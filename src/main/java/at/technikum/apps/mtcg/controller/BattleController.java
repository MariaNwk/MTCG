package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.entity.cards.CardExtended;
import at.technikum.apps.mtcg.service.BattleService;
import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BattleController extends Controller{

    private final BattleService battleService = new BattleService();
    private boolean isBattlePending = false;
    private Request firstRequest;
    private List<CardExtended> playerOneDeck;
    private  List<CardExtended> playerTwoDeck;
    private String battleLog = "";
    private String playerOne;
    private String playerTwo;

    private List<String> battleLogList = new ArrayList<>();
    public final String ERROR = "ERROR";
    ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public boolean supports(String route) {
        return route.equals("/battles");
    }

    @Override
    public Response handle(Request request) {



        if (request.getRoute().equals("/battles")) {
            switch (request.getMethod()) {
                case "POST": return battle(request);
                default:
                    return status(HttpStatus.METHOD_NOT_ALLOWED);
            }

        }

        return status(HttpStatus.BAD_REQUEST);

    }

    public Response battle(Request request) {

        if (request.getTokenNotAdmin().equals("INVALID"))
        {
            return status(HttpStatus.UNAUTHORIZED);
        }

        synchronized (this){ //wait notify

            if(!isBattlePending){
                return waitForOpponent(request);
            } else {

                if(request.getUsername().equals(firstRequest.getUsername())){
                    return status(HttpStatus.BAD_REQUEST); //FORBIDDEN
                }

                isBattlePending = false;
                return prepareBattle(request);
            }
        }

    }

    private Response waitForOpponent(Request request)  {

        isBattlePending = true;
        firstRequest = request;
        battleLog= "";

        Response response = new Response();
        response.setStatus(HttpStatus.OK);
        response.setContentType(HttpContentType.APPLICATION_JSON);
        response.setBody("Waiting for opponent");
        return response;

    }



    private Response prepareBattle(Request request) {

        playerOne = request.getUsername();
        playerTwo = firstRequest.getUsername();

        battleLog = startBattle(playerOne, playerTwo);

        this.notify();

        if (battleLog.equals(ERROR)) {
            return status(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Response response = new Response();
        response.setStatus(HttpStatus.OK);
        response.setContentType(HttpContentType.APPLICATION_JSON);
        response.setBody(battleLog);
        return response;


    }

    public String startBattle(String playerOne, String playerTwo) {

        battleLogList = new ArrayList<>();

        playerOneDeck = battleService.getDeck(playerOne);
        playerTwoDeck = battleService.getDeck(playerTwo);


        if (playerOneDeck.isEmpty() || playerTwoDeck.isEmpty()){
            return ERROR;
        }


        battleLogList.add("---------------WELCOME MTCG--------------");
        battleLogList.add("BATTLE HAS STARTED: ");
        battleLogList.add(playerOne + " AGAINST " + playerTwo);
        battleLogList.add("-----------------------------------------");

        int rounds = 0;
        int cardOneIndex;
        int cardTwoIndex;

        while(rounds < 100){

            cardOneIndex = new Random().nextInt(playerOneDeck.size());
            cardTwoIndex = new Random().nextInt(playerTwoDeck.size());

            fight(cardOneIndex,cardTwoIndex);

            if (playerOneDeck.isEmpty() || playerTwoDeck.isEmpty()) {
                break;
            }

            rounds++;

        }

        battleLogList.add("---------------BATTLE OVER--------------------");


        if (playerTwoDeck.isEmpty()) {
            battleLogList.add(playerOne + " wins the battle");
            setStats(playerOne, playerTwo);
        } else if (playerOneDeck.isEmpty()) {
            battleLogList.add(playerTwo+ " wins the battle");
            setStats(playerTwo, playerOne);
        } else {
            battleLogList.add(" No one wins the battle");
        }

        String battleLogJson;


        try
        {
            battleLogJson = objectMapper.writeValueAsString(battleLogList);
        }

        catch (JsonProcessingException e)
        {
            return ERROR;
        }

        return battleLogJson;

    }

    private void fight(int cardOneIndex, int cardTwoIndex) {

        float playerOneDamage = playerOneDeck.get(cardOneIndex).getDamage();
        float playerTwoDamage = playerTwoDeck.get(cardTwoIndex).getDamage();

        playerOneDamage = categorizeFight(playerOneDeck.get(cardOneIndex), playerTwoDeck.get(cardTwoIndex), playerOneDamage);
        playerTwoDamage = categorizeFight(playerTwoDeck.get(cardTwoIndex), playerOneDeck.get(cardOneIndex), playerTwoDamage);

        checkDamage(playerOneDamage,playerTwoDamage, cardOneIndex, cardTwoIndex);

    }

    private void checkDamage(float playerOneDamage, float playerTwoDamage, int cardOne, int cardTwo ){

        if (playerOneDamage > playerTwoDamage) { //PlayerOne wins
            battleLogList.add( playerOneDeck.get(cardOne).card_name + " card of " + playerOne +  "won");
            playerOneDeck.add(playerTwoDeck.get(cardTwo));
            playerTwoDeck.remove(playerTwoDeck.get(cardTwo));

        } else if (playerOneDamage < playerTwoDamage) { //PlayerTwo wins
            battleLogList.add(playerTwoDeck.get(cardTwo).card_name +  " card of " + playerTwo +"won");
            playerTwoDeck.add(playerOneDeck.get(cardOne));
            playerOneDeck.remove(playerOneDeck.get(cardOne));

        } else {
            battleLogList.add("DRAW");
        }

    }



    private float categorizeFight(CardExtended attack, CardExtended defend, float damage) {

        if (!attack.isMonsterCard() && !defend.isMonsterCard()){
            return spellFight(attack, defend, damage);
        } else if(!attack.isMonsterCard() && defend.isMonsterCard()){
            return mixedFight(attack, defend, damage);
        } else if (!defend.isMonsterCard() && attack.isMonsterCard()){
            return mixedFight(attack, defend, damage);
        }
        return monsterFight(attack, defend, damage);
    }




    private float spellFight(CardExtended attack, CardExtended defend, float damage) {
        String attackElement = attack.element;
        String defendElement = defend.element;

        switch (attackElement + "->" + defendElement) {
            case "water->fire":
                battleLogList.add(" water is effective against fire. ");
                return damage * 2;
            case "fire->water":
                return damage / 2;
            case "fire->regular":
                battleLogList.add(" fire is effective against regular. ");
                return damage * 2;
            case "regular->fire":
                return damage / 2;
            case "regular->water":
                battleLogList.add(" regular is effective against Water. ");
                return damage * 2;
            case "water->regular":
                return damage / 2;
            default:
                return damage;
        }
    }


    private float monsterFight(CardExtended attack, CardExtended defend, float damage) {

        //Goblin -> Dragon
        if(attack.card_name.contains("Goblin") && defend.card_name.equals("Dragon"))
        {
            battleLogList.add(" Goblins are too afraid of Dragons to attack.");
            return 0;
        }

        //Dragon -> Goblin
        if(attack.card_name.contains("Dragon") && defend.card_name.equals("Goblin"))
        {
            battleLogList.add(" Dragon damages Goblin. ");
            return damage * 2;
        }

        // Wizzard -> Or

        if(attack.card_name.contains("Wizzard") && defend.card_name.equals("Ork")){
            battleLogList.add(" Wizzard poisons Ork so that Ork dies instantly.");
            return 5000;
        }

        // Ork -> Wizzard

        if(attack.card_name.contains("Ork") && defend.card_name.equals("Wizzard")){
            battleLogList.add(" Wizzard can control Ork so they are not able to damage them.");
            return 0;
        }

        //Dragon -> Fireelf
        if(attack.card_name.contains("Dragon") && defend.card_name.equals("FireElf")){
            battleLogList.add(" The FireElves know Dragons since they were little and can evade their attacks.");
            return 0;
        }

        //Fireelf -> Dragon
        if(attack.card_name.contains("FireElf") && defend.card_name.equals("Dragon")){
            battleLogList.add(" The FireElve enchants Dragon.");
            return damage * 2;
        }

        return damage;

    }



    private float mixedFight(CardExtended attack, CardExtended defend, float damage) {

        //WaterSpell -> Knight
        if (attack.card_name.equals("WaterSpell") && defend.card_name.equals("Knight"))
        {
            battleLogList.add(" The armor of Knights is so heavy that WaterSpells make them drown them instantly. ");
            return 5000;
        }

        //spell -> kraken
        if ((attack.element.equals("water") || attack.element.equals("fire") || attack.element.equals("regular")) && defend.card_name.equals("Kraken"))
        {
            battleLogList.add(" The Kraken is immune to Spells!");
            return 0;
        }


        //water -> fire

        if(attack.element.equals("water") && defend.element.equals("fire")){
            battleLogList.add("  water is effective against fire. ");
            return damage * 2;
        }

        //fire -> water

        if(attack.element.equals("fire") && defend.element.equals("water")){
            return damage / 2;
        }

        //fire -> regular

        if(attack.element.equals("fire") && defend.element.equals("regular")){
            battleLogList.add(" fire is effective against regular. ");
            return damage * 2;
        }

        //regular -> fire

        if(attack.element.equals("regular") && defend.element.equals("fire")){
            return damage / 2;
        }

        //normal -> water

        if (attack.element.equals("regular") && defend.element.equals("water"))
        {
            battleLogList.add(" regular is effective against Water. ");
            return damage * 2;
        }
        // Water -> Normal

        if (attack.element.equals("water") && defend.element.equals("regular"))
        {
            return damage / 2;
        }

        return damage;

    }

    private void setStats(String winner, String loser) {
        battleService.setStats(winner, loser);
    }

}
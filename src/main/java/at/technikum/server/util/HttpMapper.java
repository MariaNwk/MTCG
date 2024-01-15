package at.technikum.server.util;

import at.technikum.server.http.HttpMethod;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// THOUGHT: Maybe divide the HttpMatter into two classes (single responsibility)
// THOUGHT: Dont use static methods (non-static is better for testing)
public class HttpMapper {

    public static Request toRequestObject(String httpRequest) throws IOException {
        Request request = new Request();

        request.setMethod(getHttpMethod(httpRequest));
        request.setRoute(getRoute(httpRequest));
        request.setHost(getHttpHeader("Host", httpRequest));

        String token = getToken(httpRequest);
        request.setToken(token);

        String tokenNotAdmin = getTokenNotAdmin(httpRequest);
        request.setTokenNotAdmin(tokenNotAdmin);

        String username = getUsername(httpRequest);
        request.setUsername(username);


        // THOUGHT: don't do the content parsing in this method
        String contentLengthHeader = getHttpHeader("Content-Length", httpRequest);
        if (null == contentLengthHeader) {
            return request;
        }

        int contentLength = Integer.parseInt(contentLengthHeader);
        request.setContentLength(contentLength);

        if (0 == contentLength) {
            return request;
        }

        request.setBody(httpRequest.substring(httpRequest.length() - contentLength));

        return request;
    }








    private static String getToken(String httpRequest) throws IOException {

        String token = "empty";
        try (BufferedReader br = new BufferedReader(new StringReader(httpRequest))) {
            String line;

            while ((line = br.readLine()) != null && !line.isEmpty()) {
                if (line.startsWith("Authorization: Bearer ")) {
                     token = line.substring("Authorization: Bearer ".length());
                    if(!token.contains("-mtcgToken")){
                        return "INVALID";
                    }
                     return token;
                }
            }
        } catch (IOException e) {
            // Hier entsprechende Fehlerbehandlung einfügen, falls benötigt
            e.printStackTrace();
        }

        return "INVALID";

    }


    public static String getTokenNotAdmin(String httpRequest) throws IOException {
        String token = "empty";
        try (BufferedReader br = new BufferedReader(new StringReader(httpRequest))) {
            String line;
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                if (line.startsWith("Authorization: Bearer ") && !line.contains("admin")) {
                    token = line.substring("Authorization: Bearer ".length());

                    if(!token.contains("-mtcgToken")){
                        return "INVALID";
                    }
                    return token;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "INVALID";
    }


    public static String getUsername(String httpRequest) throws IOException {

        try (BufferedReader br = new BufferedReader(new StringReader(httpRequest))) {
            String line;
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                if (line.startsWith("Authorization: Bearer ")) {
                    String token = line.substring("Authorization: Bearer ".length());

                    int dashIndex = token.indexOf('-');
                    if (dashIndex != -1) {
                        return token.substring(0, dashIndex);
                    } else {
                        return token;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "INVALID";
    }

    public static String toResponseString(Response response) {


        if (response == null) {
            return "HTTP/1.1 500 Internal Server Error\r\n\r\n";
        }

        return "HTTP/1.1 " + response.getStatusCode() + " " + response.getStatusMessage() + "\r\n" +
                "Content-Type: " + response.getContentType() + "\r\n" +
                "Content-Length: " + response.getBody().length() + "\r\n" +
                "\r\n" +
                response.getBody();
    }

    // THOUGHT: Maybe some better place for this logic?
    private static HttpMethod getHttpMethod(String httpRequest) {
        String httpMethod = httpRequest.split(" ")[0];

        // THOUGHT: Use constants instead of hardcoded strings
        return switch (httpMethod) {
            case "GET" -> HttpMethod.GET;
            case "POST" -> HttpMethod.POST;
            case "PUT" -> HttpMethod.PUT;
            case "DELETE" -> HttpMethod.DELETE;
            //case "OPTIONS" -> HttpMethod.OPTIONS;
            default -> throw new RuntimeException("No HTTP Method");
        };
    }

    private static String getRoute(String httpRequest) {
        return httpRequest.split(" ")[1];
    }

    private static String getHttpHeader(String header, String httpRequest) {
        Pattern regex = Pattern.compile("^" + header + ":\\s(.+)", Pattern.MULTILINE);
        Matcher matcher = regex.matcher(httpRequest);

        if (!matcher.find()) {
            return null;
        }

        return matcher.group(1);
    }


}

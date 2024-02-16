package com.project.DigitalWallet.ApiInteraction;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ApiInteraction {
    public static void main(String[] args) {

        String apiUrl = "http://localhost:8080/digitalWalletSystem/v1/admin/getAllUsers";
        String username = "Admin123";
        String password = "Admin@123";
        try {
            String response = sendGetRequest(apiUrl, username, password);
            System.out.println(response);
        } catch (IOException | InterruptedException e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
    private static String sendGetRequest(String apiUrl, String username, String password) throws IOException, InterruptedException {
                HttpClient httpClient = HttpClient.newBuilder().build();

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(apiUrl))
                        .header("Authorization", "Basic " + getBasicAuth(username, password))
                        .GET()
                        .build();

                HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                return httpResponse.body();
    }

    private static String getBasicAuth(String username, String password) {
        String credentials = username + ":" + password;
        byte[] base64Credentials = Base64.getEncoder().encode(credentials.getBytes());
        return new String(base64Credentials, StandardCharsets.UTF_8);
    }

}

package ApiInteraction;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.HttpClientBuilder;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ApiInteraction {
    public static void main(String[] args) {
        try {

            String apiUrl = "http://localhost:8080/digitalWalletSystem/v1/user/fetchBalance?userId=1";
            String username = "Venu7377";
            String password = "Venu@7377";


            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

            HttpClient httpClient = HttpClientBuilder.create()
                    .setDefaultCredentialsProvider(credentialsProvider)
                    .build();

            HttpGet request = new HttpGet(apiUrl);

            HttpResponse response = httpClient.execute(request);

            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line;
                StringBuilder responseContent = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }

                reader.close();

                System.out.println("API Response: " + responseContent.toString());
            } else {
                System.out.println("API request failed. Response Code: " + statusCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.project.DigitalWallet.ApiInteraction;
import com.project.DigitalWallet.PropertyReader;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class ExternalInteraction {

    @Autowired
    PropertyReader propertyReader;
    public String makeApiCall() throws IOException {

        String username = propertyReader.getProperty("external.username");
        String password = propertyReader.getProperty("external.password");
        String apiUrl = propertyReader.getProperty("apiUrl");
            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

            HttpClient httpClient = HttpClientBuilder.create()
                    .setDefaultCredentialsProvider(credentialsProvider)
                    .build();
            HttpGet httpGet = new HttpGet(apiUrl);


            HttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line;
                StringBuilder responseContent = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();

                return "API Response: " + responseContent.toString();
            } else {
                return "API request failed. Response Code: " + statusCode;
            }
    }

}


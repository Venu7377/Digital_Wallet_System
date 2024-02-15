package com.project.DigitalWallet.ApiInteraction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.DigitalWallet.ApiResponse;
import com.project.DigitalWallet.DTO.ExternalApiResponseDTO;
import com.project.DigitalWallet.PropertyReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class ExternalInteraction {

    @Autowired
    private PropertyReader propertyReader;

    public ResponseEntity< ApiResponse<List<Map<String,Object>>>> makeApiCall() throws JsonProcessingException {
        String username = propertyReader.getProperty("external.username");
        String password = propertyReader.getProperty("external.password");
        String apiUrl = propertyReader.getProperty("apiUrl");

        WebClient webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("Authorization", "Basic " + getBasicAuth(username, password))
                .build();


        String responseBody = webClient.get()
                .retrieve()
                .bodyToMono(String.class)
                .block();
        ObjectMapper objectMapper = new ObjectMapper();
        List<ExternalApiResponseDTO> externalApiResponseDTOList = objectMapper.readValue(responseBody, new TypeReference<>() {
        });
        List<Map<String, Object>> result = new ArrayList<>();
        for (ExternalApiResponseDTO responseDTO : externalApiResponseDTOList) {
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("walletId", responseDTO.getWalletId());
            userMap.put("username", responseDTO.getUsername());
            result.add(userMap);
        }


        ApiResponse<List<Map<String,Object>>> apiResponse = new ApiResponse<>("01", "Success", result);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

    }

    private String getBasicAuth(String username, String password) {
        String credentials = username + ":" + password;
        byte[] base64Credentials = Base64.getEncoder().encode(credentials.getBytes());
        return new String(base64Credentials, StandardCharsets.UTF_8);
    }
}











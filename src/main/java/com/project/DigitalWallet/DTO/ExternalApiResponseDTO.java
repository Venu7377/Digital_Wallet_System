package com.project.DigitalWallet.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class ExternalApiResponseDTO {
        @JsonProperty("walletId")
        private Long walletId;
        @JsonProperty("username")
        private String username;

    }



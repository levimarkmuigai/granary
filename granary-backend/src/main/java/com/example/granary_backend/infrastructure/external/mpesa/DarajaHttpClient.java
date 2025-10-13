package com.example.granary_backend.infrastructure.external.mpesa;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Collections;

import org.springframework.web.client.RestTemplate;

import com.example.granary_backend.infrastructure.external.mpesa.configuration.MpesaConfig;
import com.example.granary_backend.infrastructure.external.mpesa.dto.AccessTokenResponseDTO;
import com.example.granary_backend.infrastructure.external.mpesa.dto.StkPushRequestDTO;
import com.example.granary_backend.infrastructure.external.mpesa.dto.StkPushResponseDTO;

@Component
public class DarajaHttpClient {

    private final MpesaConfig mpesaConfig;
    private final RestTemplate restTemplate;

    private String cachedToken;
    private LocalDateTime tokenExpiryTime;

    public DarajaHttpClient(MpesaConfig mpesaConfig, RestTemplate restTemplate) {
        this.mpesaConfig = mpesaConfig;
        this.restTemplate = restTemplate;
    }

    public String getAccessToken() {

        if (cachedToken == null || LocalDateTime.now().isAfter(tokenExpiryTime.minusSeconds(60))) {
            return fetchNewAccessToken();
        }
        return cachedToken;
    }

    synchronized private String fetchNewAccessToken() {
        String credentials = mpesaConfig.getCredentials().getConsumerKey() + ":"
                + mpesaConfig.getCredentials().getConsumerSecret();

        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encodedCredentials);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<AccessTokenResponseDTO> response = restTemplate.exchange(
                mpesaConfig.getApi().getAuthUrl(),
                HttpMethod.GET,
                entity,
                AccessTokenResponseDTO.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            String token = response.getBody().accessToken();
            String expiresInSeconds = response.getBody().expiresIn();

            this.cachedToken = token;
            try {
                long expiresIn = Long.parseLong(expiresInSeconds);
                this.tokenExpiryTime = LocalDateTime.now().plusSeconds(expiresIn);
            } catch (NumberFormatException e) {
                System.err.println("M-Pesa API returned non-numeric expiry time: " + expiresInSeconds);

                this.tokenExpiryTime = LocalDateTime.now().plusMinutes(5);
            }

            return token;
        }
        throw new RuntimeException("Failed to retrieve M-Pesa access token.");
    }

    public StkPushResponseDTO sendStkPush(StkPushRequestDTO requestBody) {
        String accessToken = getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<StkPushRequestDTO> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<StkPushResponseDTO> response = restTemplate.exchange(
                mpesaConfig.getApi().getStkPushUrl(),
                HttpMethod.POST,
                entity,
                StkPushResponseDTO.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        }

        throw new RuntimeException("STK Push failed with status: " + response.getStatusCode());
    }
}
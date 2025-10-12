package com.example.granary_backend.infrastructure.external.mpesa.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "mpesa")
public class MpesaConfig {

    private final Api api;
    private final Credentials credentials;
    private final Security security;
    private final boolean isProduction;

    @ConstructorBinding
    public MpesaConfig(Api api, Credentials credentials, Security security, boolean isProduction) {
        this.api = api;
        this.credentials = credentials;
        this.security = security;
        this.isProduction = isProduction;
    }

    public static class Api {
        private final String authUrl;
        private final String b2cUrl;
        private final String queueTimeoutUrl;
        private final String resultUrl;
        private final String stkPushUrl;

        @ConstructorBinding
        public Api(String authUrl, String b2cUrl, String queueTimeoutUrl, String resultUrl, String stkPushUrl) {
            this.authUrl = authUrl;
            this.b2cUrl = b2cUrl;
            this.queueTimeoutUrl = queueTimeoutUrl;
            this.resultUrl = resultUrl;
            this.stkPushUrl = stkPushUrl;
        }

        public String getAuthUrl() {
            return authUrl;
        }

        public String getB2cUrl() {
            return b2cUrl;
        }

        public String getQueueTimeoutUrl() {
            return queueTimeoutUrl;
        }

        public String getResultUrl() {
            return resultUrl;
        }

        public String getStkPushUrl() {
            return stkPushUrl;
        }

    }

    public static class Credentials {
        private final String consumerKey;
        private final String consumerSecret;
        private final String shortCode;
        private final String initiatorName;
        private final String initiatorPassword;

        public Credentials(String consumerKey, String consumerSecret, String shortCode, String initiatorName,
                String initiatorPassword) {
            this.consumerKey = consumerKey;
            this.consumerSecret = consumerSecret;
            this.shortCode = shortCode;
            this.initiatorName = initiatorName;
            this.initiatorPassword = initiatorPassword;
        }

        public String getConsumerKey() {
            return consumerKey;
        }

        public String getConsumerSecret() {
            return consumerSecret;
        }

        public String getShortCode() {
            return shortCode;
        }

        public String getInitiatorName() {
            return initiatorName;
        }

        public String getInitiatorPassword() {
            return initiatorPassword;
        }

    }

    public static class Security {
        private final String certPath;

        public Security(String certPath) {
            this.certPath = certPath;
        }

        public String getCertPath() {
            return certPath;
        }
    }

    public Api getApi() {
        return this.api;
    }

    public Credentials getCredentials() {
        return this.credentials;
    }

    public Security getSecurity() {
        return this.security;
    }

    public boolean isProduction() {
        return this.isProduction;
    }
}

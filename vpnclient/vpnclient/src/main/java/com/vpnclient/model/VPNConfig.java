package com.vpnclient.model;

import jakarta.persistence.Entity;

@Entity
public class VPNConfig {
    private String clientAddress;
    private String serverAddress;
    private String protocolType;
    private String encryptionAlgorithm;

    public VPNConfig() {
    }

    public String getServerAddress() {
        return "10.0.2.25";
    }

    public String getClientAddress() {
        return "10.0.2.28";
    }

    public String getProtocolType() {
        return "TCP";
    }

    public String getEncryptionAlgorithm() {
        return "AES";
    }
}
package com.github.mzkb.auth.oauth2.line.login;

import java.io.Serializable;

public class JwtModel implements Serializable {

    private final String header;
    private final String payload;

    public JwtModel(String header, String payload) {
        this.header = header;
        this.payload = payload;
    }

    public String getHeader() {
        return header;
    }

    public String getPayload() {
        return payload;
    }
}

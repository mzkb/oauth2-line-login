package com.github.mzkb.auth.oauth2.line.login;

import java.io.Serializable;
import java.util.Map;

import static com.github.mzkb.auth.oauth2.line.login.JsonHelper.toJsonMap;
import static com.github.mzkb.auth.oauth2.line.login.JwtHelper.decodeJwt;

public class LineToken implements Serializable {

    private final String accessToken;
    private final String idToken;
    private final String email;
    private final String name;

    public static LineToken fromTokenResponseData(Map<String, Object> tokenResponse) {
        String accessToken = (String) tokenResponse.get("access_token");

        String idToken = (String) tokenResponse.get("id_token");
        JwtModel jwtModel = decodeJwt(idToken);
        String jwtPayload = jwtModel.getPayload();

        Map<String, Object> jwtJson = toJsonMap(jwtPayload);
        String email = (String) jwtJson.get("email");
        String name = (String) jwtJson.get("name");

        return new LineToken(accessToken, idToken, email, name);
    }

    private LineToken(String accessToken, String idToken, String email, String name) {
        this.accessToken = accessToken;
        this.idToken = idToken;
        this.email = email;
        this.name = name;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getIdToken() {
        return idToken;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}

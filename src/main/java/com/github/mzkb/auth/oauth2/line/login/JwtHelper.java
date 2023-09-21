package com.github.mzkb.auth.oauth2.line.login;

import java.util.Base64;

public final class JwtHelper {

    public static JwtModel decodeJwt(String token) {
        String[] chunks = token.split("\\.");

        Base64.Decoder decoder = Base64.getUrlDecoder();

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));

        return new JwtModel(header, payload);
    }
}

package com.github.mzkb.auth.oauth2.line.login;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static com.github.mzkb.auth.oauth2.line.login.JsonHelper.toJsonMap;

public final class LineHelper {
    private static final int STATUS_OK = 200;
    private static final String tokenUri = "https://api.line.me/oauth2/v2.1/token";
    private static final String authorizationUri = "https://access.line.me/oauth2/v2.1/authorize";
    private static final String jwkSetUri = "https://api.line.me/oauth2/v2.1/verify";
    private static final String userNameAttribute = "userId";
    private static final String authGrantCode = "code";
    private static final List<String> scopes = Arrays.asList("openid", "profile", "email", "real_name", "phone");
    private static final String userInfoUri = "https://api.line.me/v2/profile";

    public static String buildRedirectUri(String clientId, String redirectUri) {
        StringBuilder sb = new StringBuilder(authorizationUri);
        sb.append("?response_type=").append(authGrantCode);
        sb.append("&client_id=").append(clientId);
        sb.append("&redirect_uri=").append(redirectUri);
        sb.append("&scope=").append(String.join(" ", scopes));
        // State should be generated and stored, so it can be checked in the callback
        sb.append("&state=").append(UUID.randomUUID());
        return sb.toString();
    }

    public static Map<String, Object> getProfileData(String accessToken) {
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        try (CloseableHttpClient client = httpClientBuilder.build()) {

            HttpGet httpGet = new HttpGet(userInfoUri);
            httpGet.setHeader("Authorization", "Bearer " + accessToken);

            try (CloseableHttpResponse response = client.execute(httpGet);
                 InputStream stream = response.getEntity().getContent()) {

                int statusCode = response.getCode();
                if (statusCode == STATUS_OK) {
                    return toJsonMap(stream);
                }

                System.out.println("Status Code Not OK: " + statusCode);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("error with profile");
    }

    public static Map<String, Object> getToken(String code, String clientId, String clientSecret, String redirectUri) {
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        try (CloseableHttpClient client = httpClientBuilder.build()) {

            HttpPost httpPost = new HttpPost(tokenUri);
            httpPost.setEntity(new UrlEncodedFormEntity(getTokenBody(code, clientId, clientSecret, redirectUri)));
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

            try (CloseableHttpResponse response = client.execute(httpPost);
                 InputStream stream = response.getEntity().getContent()) {

                int statusCode = response.getCode();
                if (statusCode == STATUS_OK) {
                    return toJsonMap(stream);
                }

                System.out.println("Status Code Not OK: " + statusCode);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("error with token");
    }

    private static List<NameValuePair> getTokenBody(String code, String clientId, String clientSecret, String redirectUri) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("client_id", clientId));
        params.add(new BasicNameValuePair("client_secret", clientSecret));
        params.add(new BasicNameValuePair("code", code));
        params.add(new BasicNameValuePair("grant_type", "authorization_code"));
        params.add(new BasicNameValuePair("redirect_uri", redirectUri));
        return params;
    }
}

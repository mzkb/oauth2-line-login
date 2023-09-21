package com.github.mzkb.auth.oauth2.line.login;

import java.io.Serializable;
import java.util.Map;

public class LineProfile implements Serializable {
    private final String email;
    private final String name;
    private final String userId;
    private final String displayName;
    private final String pictureUrl;

    public static LineProfile fromLineProfileData(String email, String name, Map<String, Object> profileData) {
        String userId = (String) profileData.get("userId");
        String pictureUrl = (String) profileData.get("pictureUrl");
        String displayName = (String) profileData.get("displayName");
        return new LineProfile(email, name, userId, displayName, pictureUrl);
    }

    private LineProfile(String email, String name, String userId, String displayName, String pictureUrl) {
        this.email = email;
        this.name = name;
        this.userId = userId;
        this.displayName = displayName;
        this.pictureUrl = pictureUrl;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }
}

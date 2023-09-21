package com.github.mzkb.auth.oauth2.line.login;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import static com.github.mzkb.auth.oauth2.line.login.LineHelper.*;

@Controller
public class LineLoginController {

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;

    public LineLoginController(
            @Value("${line.clientId}") String clientId,
            @Value("${line.clientSecret}") String clientSecret,
            @Value("${line.redirectUri}") String redirectUri) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;

        if (this.clientId == null || this.clientSecret == null || this.redirectUri == null) {
            throw new RuntimeException("error with configuration");
        }
    }

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @GetMapping("/login-line")
    public ModelAndView loginLine() {
        String lineRedirectUri = buildRedirectUri(clientId, redirectUri);
        System.out.println("Auth Redirect URI: " + lineRedirectUri);
        return new ModelAndView("redirect:" + lineRedirectUri);
    }

    @GetMapping("/oauth-callback/line")
    public ModelAndView loginLineCallback(@RequestParam(value = "code", required = false) String code,
                                          @RequestParam(value = "state", required = false) String state,
                                          @RequestParam(value = "error", required = false) String error,
                                          @RequestParam(value = "error_description", required = false) String errorDescription) {

        System.out.println("Auth Callback:");
        System.out.println(" - Code: " + code);
        System.out.println(" - State: " + state);
        System.out.println(" - Error: " + error);
        System.out.println(" - Error Description: " + errorDescription);

        if (error != null) {
            String errorMessage = (errorDescription != null) ? errorDescription.replace('+', ' ') : "An unknown error occurred.";
            return new ModelAndView("error")
                    .addObject("errorTitle", "Access Denied")
                    .addObject("errorMessage", errorMessage);
        }

        Map<String, Object> tokenDataResponse = getToken(code, clientId, clientSecret, redirectUri);
        LineToken lineToken = LineToken.fromTokenResponseData(tokenDataResponse);

        LineProfile lineProfile = LineProfile.fromLineProfileData(
                lineToken.getEmail(), lineToken.getName(), getProfileData(lineToken.getAccessToken()));

        return new ModelAndView("line").addObject("line", lineProfile);
    }
}
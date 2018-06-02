package com.tpo.fitness.service.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * Created by Tiberiu on 28/11/15.
 */
@Getter
public class AppProperties {

    private static final String STRAVA_OAUTH_URL_PROPERTY = "strava.oauth.url";
    private static final String STRAVA_OAUTH_CLIENT_SECRET_PROPERTY = "strava.oauth.client.secret";
    private static final String STRAVA_OAUTH_CLIENT_ID_PROPERTY = "strava.oauth.client.id";
    private static final String STRAVA_OAUTH_TOKEN_PROPERTY = "strava.oauth.token";
    private static final String STRAVA_OAUTH_CALLBACK_URL = "strava.oauth.url.callback";

    private final String oauthUrl;
    private final String oatuhClientSecret;
    private final String oatuhClientId;
    private final String oauthToken;
    private final String callbackUrl;

    @Autowired
    public AppProperties(Environment environment) {
        this.oauthUrl = environment.getRequiredProperty(STRAVA_OAUTH_URL_PROPERTY);
        this.oatuhClientSecret = environment.getRequiredProperty(STRAVA_OAUTH_CLIENT_SECRET_PROPERTY);
        this.oatuhClientId = environment.getRequiredProperty(STRAVA_OAUTH_CLIENT_ID_PROPERTY);
        this.oauthToken = environment.getRequiredProperty(STRAVA_OAUTH_TOKEN_PROPERTY);
        this.callbackUrl = environment.getRequiredProperty(STRAVA_OAUTH_CALLBACK_URL);
    }
}

package com.tpo.fitme.strava.client.oauth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * Created by Tiberiu on 28/11/15.
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "strava.oauth")
@Validated
public class OAuthProperties {
    private String url;
    private String clientId;
    private String secret;
    private String callbackUrl;
}

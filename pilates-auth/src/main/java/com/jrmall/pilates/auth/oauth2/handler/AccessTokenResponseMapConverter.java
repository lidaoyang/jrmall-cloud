package com.jrmall.pilates.auth.oauth2.handler;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * AccessTokenResponse转换对象
 *
 * @author: Dao-yang.
 * @date: Created in 2025/6/13 11:24
 */
public final class AccessTokenResponseMapConverter implements Converter<OAuth2AccessTokenResponse, Map<String, Object>> {
    public Map<String, Object> convert(OAuth2AccessTokenResponse tokenResponse) {
        Map<String, Object> parameters = new HashMap();
        parameters.put("accessToken", tokenResponse.getAccessToken().getTokenValue());
        parameters.put("tokenType", tokenResponse.getAccessToken().getTokenType().getValue());
        parameters.put("expiresIn", getExpiresIn(tokenResponse));
        if (!CollectionUtils.isEmpty(tokenResponse.getAccessToken().getScopes())) {
            parameters.put("scope", StringUtils.collectionToDelimitedString(tokenResponse.getAccessToken().getScopes(), " "));
        }

        if (tokenResponse.getRefreshToken() != null) {
            parameters.put("refreshToken", tokenResponse.getRefreshToken().getTokenValue());
        }

        if (!CollectionUtils.isEmpty(tokenResponse.getAdditionalParameters())) {
            for (Map.Entry<String, Object> entry : tokenResponse.getAdditionalParameters().entrySet()) {
                parameters.put((String) entry.getKey(), entry.getValue());
            }
        }

        return parameters;
    }

    private static long getExpiresIn(OAuth2AccessTokenResponse tokenResponse) {
        return tokenResponse.getAccessToken().getExpiresAt() != null ? ChronoUnit.SECONDS.between(Instant.now(), tokenResponse.getAccessToken().getExpiresAt()) : -1L;
    }
}

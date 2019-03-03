package com.aconference.config.security.oauth2;

import com.aconference.config.AppProperties;
import org.springframework.security.crypto.keygen.Base64StringKeyGenerator;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomOAuthAuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {

    private final StringKeyGenerator stateGenerator = new Base64StringKeyGenerator(Base64.getUrlEncoder());
    private final AntPathRequestMatcher authorizationRequestMatcher;
    private final GoogleProperties googleProperties;
    private final AppProperties appProperties;

    public CustomOAuthAuthorizationRequestResolver(AppProperties appProperties, GoogleProperties googleProperties) {
        this.appProperties = appProperties;
        this.googleProperties = googleProperties;
        this.authorizationRequestMatcher = new AntPathRequestMatcher(this.appProperties.getoAuth2().getAuthorizationRequestUritemplate());
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
        String registrationId = this.resolveRegistrationId(request);
        String redirectUriAction = this.getAction(request, "login");
        return this.resolve(request, registrationId, redirectUriAction);
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String registrationId) {
        if (registrationId == null) {
            return null;
        } else {
            String redirectUriAction = this.getAction(request, "authorize");
            return this.resolve(request, registrationId, redirectUriAction);
        }
    }

    private OAuth2AuthorizationRequest resolve(HttpServletRequest request, String registrationId, String redirectUriAction) {
        if (registrationId == null) {
            return null;
        }
        OAuth2AuthorizationRequest.Builder builder = OAuth2AuthorizationRequest.authorizationCode();
        String redirectUriStr = this.expandRedirectUri(request, registrationId, redirectUriAction);
        Map<String, Object> additionalParameters = buildAdditionalParams();
        additionalParameters.put("registration_id", registrationId);
        return builder.clientId(googleProperties.getClientId())
                .authorizationUri(googleProperties.getAuthorizationUri())
                .redirectUri(redirectUriStr)
                .scopes(googleProperties.getScope())
                .state(this.stateGenerator.generateKey())
                .additionalParameters(additionalParameters)
                .build();
    }

    private Map<String, Object > buildAdditionalParams() {
        Map<String, Object> result = new HashMap<>();
        result.put("access_type", "offline");
        return result;
    }

    private String getAction(HttpServletRequest request, String defaultAction) {
        String action = request.getParameter("action");
        return action == null ? defaultAction : action;
    }

    private String resolveRegistrationId(HttpServletRequest request) {
        return this.authorizationRequestMatcher.matches(request) ? (String)this.authorizationRequestMatcher.extractUriTemplateVariables(request).get("registrationId") : null;
    }

    private String expandRedirectUri(HttpServletRequest request, String registrationId, String action) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("registrationId", registrationId);
        String baseUrl = UriComponentsBuilder.fromHttpUrl(UrlUtils.buildFullRequestUrl(request)).replaceQuery(null).replacePath(request.getContextPath()).build().toUriString();
        uriVariables.put("baseUrl", baseUrl);
        if (action != null) {
            uriVariables.put("action", action);
        }
        return UriComponentsBuilder.fromUriString(googleProperties.getRedirectUriTemplate()).buildAndExpand(uriVariables).toUriString();
    }

}

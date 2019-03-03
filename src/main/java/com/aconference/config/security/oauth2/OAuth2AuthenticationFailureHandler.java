package com.aconference.config.security.oauth2;

import com.aconference.util.CookieUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.aconference.config.security.oauth2.HttpCookieOAuth2AuthorithationRequestRepository.REDIRECT_URI_PARAM_COOCKIE_NAME;

@Component
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private HttpCookieOAuth2AuthorithationRequestRepository httpCookieOAuth2AuthorithationRequestRepository;

    public OAuth2AuthenticationFailureHandler(HttpCookieOAuth2AuthorithationRequestRepository httpCookieOAuth2AuthorithationRequestRepository) {
        this.httpCookieOAuth2AuthorithationRequestRepository = httpCookieOAuth2AuthorithationRequestRepository;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String targetUrl = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOCKIE_NAME)
                .map(Cookie::getValue)
                .orElse("/");

        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("error", exception.getLocalizedMessage())
                .build()
                .toUriString();

        httpCookieOAuth2AuthorithationRequestRepository.removeAuthorizationRequestCookies(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}

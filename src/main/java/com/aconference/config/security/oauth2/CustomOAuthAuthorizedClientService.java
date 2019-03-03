package com.aconference.config.security.oauth2;

import com.aconference.domain.Token;
import com.aconference.domain.User;
import com.aconference.repository.TokenRepository;
import com.aconference.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CustomOAuthAuthorizedClientService implements OAuth2AuthorizedClientService {

    private UserRepository userRepository;
    private TokenRepository tokenRepository;

    public CustomOAuthAuthorizedClientService(UserRepository userRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String s, String s1) {
        return null;
    }

    @Override
    @Transactional
    public void saveAuthorizedClient(OAuth2AuthorizedClient oAuth2AuthorizedClient, Authentication authentication) {
        Optional<User> optionalUser = userRepository.findByEmail(oAuth2AuthorizedClient.getPrincipalName());
        if (!optionalUser.isPresent()) {
            return;
        }
        User user = optionalUser.get();
        updateToken(user, oAuth2AuthorizedClient);
    }

    private void updateToken(User user, OAuth2AuthorizedClient oAuth2AuthorizedClient) {
        Token token = user.getToken();
        if (token == null) {
            token = new Token();
            user.setToken(token);
        }
        token.setOauthAccessToken(oAuth2AuthorizedClient.getAccessToken().getTokenValue());
        token.setOauthExpiresAt(oAuth2AuthorizedClient.getAccessToken().getExpiresAt());
        if (oAuth2AuthorizedClient.getRefreshToken() != null) {
            token.setOauthRefreshToken(oAuth2AuthorizedClient.getRefreshToken().getTokenValue());
        }
        tokenRepository.save(token);
    }

    @Override
    public void removeAuthorizedClient(String s, String s1) {

    }
}

package com.aconference.service;

import com.aconference.config.security.oauth2.user.UserPrincipal;
import com.aconference.domain.Configuration;
import com.aconference.domain.User;
import com.aconference.exception.ResourceNotFoundException;
import com.aconference.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfigurationService configurationService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        setConfiguration(user);
        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        setConfiguration(user);
        return UserPrincipal.create(user);
    }

    private void setConfiguration(User user) {
        Configuration configuration = user.getConfiguration();
        if (configuration == null) {
            configuration = new Configuration();
            user.setConfiguration(configuration);
            configurationService.saveConfiguration(configuration);
        }
    }

}

package com.aconference.service;

import com.aconference.domain.Configuration;
import com.aconference.dto.ConfigurationDto;
import com.aconference.repository.ConfigurationRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationService {

    private ConfigurationRepository configurationRepository;

    public ConfigurationService(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    public Configuration saveConfiguration(Configuration configuration) {
        return configurationRepository.save(configuration);
    }

    public Configuration updateConfiguration(ConfigurationDto dto, Configuration configuration) {
        if (configuration.getId() == null) {
            throw new IllegalStateException("Configuration.id field should not be null");
        }
        configuration.setInvitationFilesPath(dto.getInvitationFilesPath());
        return configurationRepository.save(configuration);
    }
}

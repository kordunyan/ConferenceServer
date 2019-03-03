package com.aconference.repository;

import com.aconference.domain.Configuration;
import org.springframework.data.repository.CrudRepository;

public interface ConfigurationRepository extends CrudRepository<Configuration, Long> {
}

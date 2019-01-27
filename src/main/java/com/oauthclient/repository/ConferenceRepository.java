package com.oauthclient.repository;

import com.oauthclient.domain.Conference;
import org.springframework.data.repository.CrudRepository;

public interface ConferenceRepository extends CrudRepository<Conference, Long> {
}

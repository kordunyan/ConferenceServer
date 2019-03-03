package com.aconference.repository;

import com.aconference.domain.Conference;
import com.aconference.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ConferenceRepository extends CrudRepository<Conference, Long> {

    boolean existsBySubjectAndUser(String subject, User user);

    List<Conference> findAllByUserOrderByIdDesc(User user);

    Optional<Conference> findByIdAndUser(Long id, User user);
}

package com.oauthclient.repository;

import com.oauthclient.domain.Conference;
import com.oauthclient.domain.User;
import org.springframework.data.repository.CrudRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ConferenceRepository extends CrudRepository<Conference, Long> {

    boolean existsBySubjectAndUser(String subject, User user);

    List<Conference> findAllByUserOrderByIdDesc(User user);

    Optional<Conference> findByIdAndUser(Long id, User user);
}

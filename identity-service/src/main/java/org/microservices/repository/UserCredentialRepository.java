package org.microservices.repository;

import org.microservices.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, Integer> {

    public Optional<UserCredential> findByName(String name);

}

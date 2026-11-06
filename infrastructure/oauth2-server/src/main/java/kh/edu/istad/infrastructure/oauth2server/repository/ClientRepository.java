package kh.edu.istad.infrastructure.oauth2server.repository;


import kh.edu.istad.infrastructure.oauth2server.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, String>{
    Optional<Client> findByClientId(String clientId);
}

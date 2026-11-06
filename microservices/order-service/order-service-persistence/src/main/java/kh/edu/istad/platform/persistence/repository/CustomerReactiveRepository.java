package kh.edu.istad.platform.persistence.repository;

import kh.edu.istad.platform.persistence.entity.CustomerEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface CustomerReactiveRepository extends R2dbcRepository<CustomerEntity, UUID> {

}

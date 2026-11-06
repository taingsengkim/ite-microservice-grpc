package kh.edu.istad.platform.persistence.repository;

import kh.edu.istad.platform.persistence.entity.OrderEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface OrderReactiveRepository extends R2dbcRepository<OrderEntity, String> {

}

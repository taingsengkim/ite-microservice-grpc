package kh.edu.istad.platform.persistence.repository;

import kh.edu.istad.platform.persistence.entity.BusinessEntity;
import kh.edu.istad.platform.persistence.entity.BusinessEntityId;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BusinessReactiveRepository extends R2dbcRepository<BusinessEntity, BusinessEntityId>{

}

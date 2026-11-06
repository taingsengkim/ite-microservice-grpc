package kh.edu.istad.platform.iam.repository;

import kh.edu.istad.platform.iam.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepostitory extends JpaRepository<Permission, Long> {

}

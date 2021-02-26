package ch.supsi.webapp.web.repository;

import ch.supsi.webapp.web.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}

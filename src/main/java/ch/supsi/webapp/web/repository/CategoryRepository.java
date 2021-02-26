package ch.supsi.webapp.web.repository;

import ch.supsi.webapp.web.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}

package ch.supsi.webapp.web.repository;

import ch.supsi.webapp.web.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, String> {
}

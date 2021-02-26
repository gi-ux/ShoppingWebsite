package ch.supsi.webapp.web;

import ch.supsi.webapp.web.model.Author;
import ch.supsi.webapp.web.model.Category;
import ch.supsi.webapp.web.repository.AuthorRepository;
import ch.supsi.webapp.web.repository.CategoryRepository;
import ch.supsi.webapp.web.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebApplication {

	@Autowired
	AuthorRepository authorRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	PasswordService passwordService;

	@Bean
	public CommandLineRunner initUser()
	{
		return (args) -> {
			if(authorRepository.count() == 0) {
				Author user = new Author("admin", "ROLE_ADMIN",passwordService.encrypt("admin"));
				authorRepository.save(user);
			}
		};
	}

	@Bean
	public CommandLineRunner initCategory()
	{
		return (args) -> {
			if(categoryRepository.count() == 0) {
				Category category = new Category("Veicolo");
				categoryRepository.save(category);
				Category category2 = new Category("Immobile");
				categoryRepository.save(category2);
				Category category3 = new Category("Lavoro");
				categoryRepository.save(category3);
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

}

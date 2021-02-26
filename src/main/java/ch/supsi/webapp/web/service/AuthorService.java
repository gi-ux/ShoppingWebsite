package ch.supsi.webapp.web.service;

import ch.supsi.webapp.web.model.Author;
import ch.supsi.webapp.web.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public Author post(@RequestBody Author author){
        return authorRepository.save(author);
    }

    public Author findUserByUsername(String username)
    {
        return authorRepository.findById(username).orElse(null);
    }
}

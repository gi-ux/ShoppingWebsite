package ch.supsi.webapp.web.service;

import ch.supsi.webapp.web.model.*;
import ch.supsi.webapp.web.repository.AuthorRepository;
import ch.supsi.webapp.web.repository.CategoryRepository;
import ch.supsi.webapp.web.repository.ItemRepository;
import ch.supsi.webapp.web.repository.RoleRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private RoleRepository roleRepository;

    public Author findUserByUsername(String username)
    {
        return authorRepository.findById(username).orElse(null);
    }

    private ObjectMapper objectMapper = new ObjectMapper();

    public List<Item> getAllIds() {
         return itemRepository.findAll();
    }

    public List<Item> get() {
        return itemRepository.findAll();
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll()
                .stream()
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Item> getByCategory(String category)
    {
        List<Item> toReturn = new ArrayList<>();
        List<Item> itemList = get();
        for (Item i : itemList)
        {
            if(i.getCategory().getCategory().equals(category))
                toReturn.add(i);
        }
        return toReturn;
    }

    public List<Item> getByString(String q)
    {
        List<Item> toReturn = new ArrayList<>();
        List<Item> itemList = get();
        for (Item i : itemList)
        {
            if(i.getDescription().contains(q) || i.getTitle().contains(q) || i.getCategory().getCategory().contains(q))
                toReturn.add(i);
        }
        return toReturn;
    }

    public List<Author> getAllAuthors(){
        return authorRepository.findAll()
                .stream()
                .distinct()
                .collect(Collectors.toList());
    }

    public Item getItem(int id) {
        return itemRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Item not found"
        ));
    }

    public Item post(@RequestBody Item item){
        return itemRepository.save(item);
    }

    public Item put(int id, Item updated) {
        Item toUpdate = getItem(id);

        toUpdate.setAuthor(updated.getAuthor());
        toUpdate.setDescription(updated.getDescription());
        toUpdate.setTitle(updated.getTitle());
        toUpdate.setCategory(updated.getCategory());
        toUpdate.setAnnuncio(updated.getAnnuncio());
        toUpdate.setDate(new Date());
        toUpdate.setCosto(updated.getCosto());
        if(updated.getImage() != null)
            toUpdate.setImage(updated.getImage());

        return post(toUpdate);
    }

    public ResponseEntity delete(@PathVariable int id) throws JsonProcessingException {
        Success success = new Success();
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
            success.setStatus(true);
            return new ResponseEntity<Success>(success, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Item not found"
            );
        }
    }
}

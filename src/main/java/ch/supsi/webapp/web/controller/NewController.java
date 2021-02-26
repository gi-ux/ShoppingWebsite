package ch.supsi.webapp.web.controller;

import ch.supsi.webapp.web.model.Author;
import ch.supsi.webapp.web.model.Item;
import ch.supsi.webapp.web.model.Role;
import ch.supsi.webapp.web.service.AuthorService;
import ch.supsi.webapp.web.service.ItemService;
import ch.supsi.webapp.web.service.PasswordService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
public class NewController {

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private AuthorService authorService;

    @GetMapping("/")
    public String getAllItems(Model model) {
        List<Item> items = itemService.get();
        model.addAttribute("getCategory", itemService.getAllCategories());
        model.addAttribute("getItems",items);
        return "articles";
    }

    @GetMapping("/item/{id}")
    public String getItemFromId(@PathVariable Integer id, Model model) {
        model.addAttribute("getItem", itemService.getItem(id));
        return "detail";
    }

    @GetMapping("/user/{user}")
    public  String getUserFromId(@PathVariable String user, Model model){
        Author author = authorService.findUserByUsername(user);
        model.addAttribute("getUser",author);
        model.addAttribute("getFav", author.getListaFav());
        return "profile";
    }

    @GetMapping("/item/{id}/pref")
    public  String addFavouriteToUser(@PathVariable Integer id, Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Author author = authorService.findUserByUsername(user.getUsername());
        Item i = itemService.getItem(id);
        if(author.isPresent(i))
        {

        }else {
            author.addFav(i);
            authorService.post(author);
        }
        return "redirect:/";
    }



    @PostMapping("/register")
    public String createNewItem(@ModelAttribute Author author) {
        author.setRole(new Role("ROLE_USER"));
        author.setPassword(passwordService.encrypt(author.getPassword()));
        authorService.post(author);
        return "redirect:/";
    }


    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute("author", new Author());
        return "registerForm";
    }


    @GetMapping("/login")
    public String getLogin() {
        return "loginForm";
    }

    @GetMapping("/logout")
    public String getLogout() {
        return "articles";
    }


    @GetMapping("/item/new")
    public String getNewItem(Model model) {
        String page = "new";
        model.addAttribute("resources", page);
        model.addAttribute("authors", itemService.getAllAuthors());
        model.addAttribute("categories", itemService.getAllCategories());
        model.addAttribute("item", new Item());
        return "createForm";
    }

    @PostMapping("/item/new")
    public String createNewItem(@ModelAttribute Item item, @RequestParam("file") MultipartFile file) throws IOException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        item.setAuthor(authorService.findUserByUsername(user.getUsername()));
        item.setDate(new Date());
        if(!file.isEmpty())
            item.setImage(file.getBytes());
        itemService.post(item);
        return "redirect:/";
    }

    @GetMapping(value = "/item/{id}/delete")
    public String deleteItem(@PathVariable Integer id, Model model) throws JsonProcessingException {
        model.addAttribute(itemService.delete(id));
        return "redirect:/";
    }

    @GetMapping(value = "/item/{id}/compra")
    public String compraItem(@PathVariable Integer id, Model model) throws JsonProcessingException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Author author = authorService.findUserByUsername(user.getUsername());
        Item item = itemService.getItem(id);
        double credito = author.getCredito();
        double costo = item.getCosto();
        if(costo <= credito)
        {
            author.setCredito(author.getCredito()-item.getCosto());
            model.addAttribute(itemService.delete(id));
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/item/{id}/edit", method = RequestMethod.POST)
    public String putItem(@PathVariable Integer id, Item item, @RequestParam("file") MultipartFile file) throws IOException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        item.setAuthor(authorService.findUserByUsername(user.getUsername()));
        item.setDate(new Date());
        if(!file.isEmpty())
            item.setImage(file.getBytes());
        itemService.put(id, item);
        return "redirect:/";
    }

    @RequestMapping(value = "/item/{id}/edit", method = RequestMethod.GET)
    public String getModItem(@PathVariable Integer id, Model model) {
        model.addAttribute("item", itemService.getItem(id));
        model.addAttribute("authors", itemService.getItem(id).getAuthor());
        model.addAttribute("categories", itemService.getItem(id).getCategory());
        model.addAttribute("title", itemService.getItem(id).getTitle());
        model.addAttribute("description", itemService.getItem(id).getDescription());
        model.addAttribute("image", itemService.getItem(id).getImage());
        model.addAttribute("image", itemService.getItem(id).getCosto());
        return "editForm";
    }

    @RequestMapping(value="/item/search", method=RequestMethod.GET)
    @ResponseBody
    public List<Item> getSearch(@RequestParam (value = "q", required = false) String q) {
        List<Item> found = itemService.getByString(q);
        return found;
    }

    @RequestMapping(value="/item/search/category", method=RequestMethod.GET)
    @ResponseBody
    public List<Item> getSearchByCategory(@RequestParam (value = "cat", required = false) String cat) {
        List<Item> found = itemService.getByCategory(cat);
        return found;
    }
}

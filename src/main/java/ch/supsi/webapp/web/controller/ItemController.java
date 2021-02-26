package ch.supsi.webapp.web.controller;
import ch.supsi.webapp.web.model.Success;
import ch.supsi.webapp.web.service.ItemService;
import ch.supsi.webapp.web.model.Item;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class ItemController {
    @Autowired
    private ItemService itemService;



    @RequestMapping(value="/items", method=RequestMethod.GET)
    public List<Item> get() {
        return itemService.get();
    }

    @RequestMapping(value="/items/{id}", method=RequestMethod.GET)
    public ResponseEntity<Item> getItem(@PathVariable int id) {
        return new ResponseEntity<Item>(itemService.getItem(id), HttpStatus.OK);
    }

    @RequestMapping(value="/items", method = RequestMethod.POST)
    public Item post(@RequestBody Item item){
        item.setDate(new Date());
        return itemService.post(item);
    }

    @RequestMapping(value="/items/{id}", method= RequestMethod.PUT)
    public Item put(@PathVariable int id, @RequestBody Item it) {
        return itemService.put(id, it);
    }

    @RequestMapping(value="/items/{id}", method= RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable int id) throws JsonProcessingException { return itemService.delete(id); }

    @GetMapping(value = "/item/{id}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] image(@PathVariable int id) {
        Item item = itemService.getItem(id);
        return item.getImage();
    }
}

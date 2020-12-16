package controller;


import com.example.ex08.Item;
import com.example.ex08.ItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class ItemController {

    private ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("hello")
    public String hello() {
        return "Tere!";
    }

    @GetMapping("api/items")
    public Iterable<Item> getItems() {
        return itemRepository.findAll();
    }

    @GetMapping("api/items/{id}")
    public Item getItemById(@PathVariable Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("api/items/{id}/mark-as-done")
    public void markAsDone(@PathVariable Long id) {
        itemRepository.markAsDone(id);
    }

    @PostMapping("api/items")
    public void insertItem(@RequestBody @Valid Item item) {
        itemRepository.save(item);
    }

    @ExceptionHandler
    public ResponseEntity<Void> handle(ResponseStatusException e) {
        return new ResponseEntity<>(null, e.getStatus());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleValidationExceptions(MethodArgumentNotValidException ex) {
    }


}

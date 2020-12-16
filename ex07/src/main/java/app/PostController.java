package app;

import model.Post;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PostController {

    private PostMemoryDao dao;

    public PostController(PostMemoryDao dao) {
        this.dao = dao;
    }

    @GetMapping("posts")
    public List<Post> getPosts() {
        return dao.findAll();
    }

    @PostMapping("posts")
    @ResponseStatus(HttpStatus.CREATED)
    public void savePost(@RequestBody @Valid Post post) {
        dao.save(post);
    }

    @DeleteMapping("posts/{id}")
    public void deletePost(@PathVariable Long id) {
        dao.delete(id);
    }

}
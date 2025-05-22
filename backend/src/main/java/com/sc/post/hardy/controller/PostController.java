package com.sc.post.hardy.controller;

import com.sc.post.hardy.model.dto.post.PostModel;
import com.sc.post.hardy.model.dto.post.PostQueryModel;
import com.sc.post.hardy.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/save")
    public ResponseEntity<PostModel> save(@RequestBody PostModel postModel) {
        return ResponseEntity.ok(postService.save(postModel));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<PostModel> getById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(postService.getById(id));
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable(name = "id")  Long id) {
        postService.delete(id);
    }

    @PutMapping("/like/{id}")
    public ResponseEntity<PostModel> likePost(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(postService.likePost(id));
    }

    @PutMapping("/unLike/{id}")
    public ResponseEntity<PostModel> unLikePost(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(postService.unLikePost(id));
    }

    @PostMapping("/queryPage")
    public ResponseEntity<Page<PostModel>> findPostWithPagination(Pageable pageable, @RequestBody PostQueryModel queryModel) {
        return ResponseEntity.ok(postService.findPostWithPagination(pageable, queryModel));
    }
}

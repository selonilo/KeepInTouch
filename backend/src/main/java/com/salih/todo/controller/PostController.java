package com.salih.todo.controller;

import com.salih.todo.model.dto.PostDto;
import com.salih.todo.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController extends BaseController{

    private final PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @GetMapping(PUBLIC_FIND_ALL_POST)
    public List<PostDto> getAll(){
        return postService.getAll();
    }

    @GetMapping(PUBLIC_GET_BY_USER_ID + "/{id}")
    public List<PostDto> getByUserId(@PathVariable Long id){
        List<PostDto> postDtos = postService.getByUserId(id);
        return postDtos;
    }

    @PostMapping(PUBLIC_SAVE_POST)
    public ResponseEntity<PostDto> save(@RequestBody PostDto postDto){
        return ResponseEntity.ok(postService.save(postDto));
    }

    @DeleteMapping(PUBLIC_DELETE_POST+"/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable(value = "id",required = true)Long id){
        return ResponseEntity.ok(postService.delete(id));
    }

    @PostMapping(PUBLIC_UPDATE_POST)
    public ResponseEntity<PostDto> update(@RequestBody PostDto postDto){
        return ResponseEntity.ok(postService.update(postDto));
    }


}

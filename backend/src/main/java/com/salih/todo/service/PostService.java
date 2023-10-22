package com.salih.todo.service;

import com.salih.todo.exception.KullaniciBulunamadiException;
import com.salih.todo.model.dto.PostDto;
import com.salih.todo.model.entity.Post;
import com.salih.todo.model.entity.User;
import com.salih.todo.model.mapper.PostMapper;
import com.salih.todo.repository.PostRepository;
import com.salih.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    private final UserRepository userRepository;

    public List<PostDto> getAll(){
        return PostMapper.mapToList(postRepository.findAll());
    }

    public List<PostDto> getByUserId(Long id){
        return PostMapper.mapToList(postRepository.getByUserId(id));
    }

    public PostDto save(PostDto postDto) {
        if (postDto.getDescription() == null){
            throw new IllegalArgumentException("description is null");
        }
        Post post = PostMapper.mapTo(postDto);

        Optional<User> user = userRepository.findById(postDto.getUserId());
        if (user.isPresent()){
            post.setUser(user.get());
        }
        else {
            throw new KullaniciBulunamadiException(postDto.getUserId().toString());
        }
        postRepository.save(post);
        return postDto;
    }

    public Boolean delete(Long id){
        postRepository.deleteById(id);
        return true;
    }

    public PostDto update(PostDto postDto){
        Optional<Post> todoOptional = postRepository.findById(postDto.getId());
        if(todoOptional.isPresent()){
            Post post = todoOptional.get();
            postRepository.save(post);
        }
        else {
            throw new IllegalArgumentException("not found id");
        }
        return postDto;
    }



}

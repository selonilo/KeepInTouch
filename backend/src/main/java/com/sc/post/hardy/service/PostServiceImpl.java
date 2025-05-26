package com.sc.post.hardy.service;

import com.sc.post.hardy.model.dto.post.PostModel;
import com.sc.post.hardy.model.dto.post.PostQueryModel;
import com.sc.post.hardy.model.entity.PostEntity;
import com.sc.post.hardy.model.mapper.PostMapper;
import com.sc.post.hardy.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    public PostModel save(PostModel postModel) {
        return PostMapper.mapTo(postRepository.saveAndFlush(PostMapper.mapTo(postModel)));
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    public PostModel getById(Long id) {
        var optPost = postRepository.findById(id);
        if (optPost.isPresent()) {
            var post = optPost.get();
            post.setViewNumber(post.getViewNumber() + 1);
            postRepository.saveAndFlush(post);
            return PostMapper.mapTo(post);
        } else {
            throw new RuntimeException();
        }
    }

    public PostModel likePost(Long id) {
        var optPost = postRepository.findById(id);
        if (optPost.isPresent()) {
            var post = optPost.get();
            post.setLikeNumber(post.getLikeNumber() + 1);
            return PostMapper.mapTo(postRepository.saveAndFlush(post));
        } else {
            throw new RuntimeException();
        }
    }

    public PostModel unLikePost(Long id) {
        var optPost = postRepository.findById(id);
        if (optPost.isPresent()) {
            var post = optPost.get();
            if (post.getLikeNumber() == 0) {
                throw new RuntimeException();
            }
            post.setLikeNumber(post.getLikeNumber() - 1);
            return PostMapper.mapTo(postRepository.saveAndFlush(post));
        } else {
            throw new RuntimeException();
        }
    }

    public List<PostModel> getList() {
        List<PostEntity> postList = postRepository.findAll();
        return PostMapper.mapToList(postList);
    }

    public List<PostModel> getListByUserId(Long id) {
        List<PostEntity> postList = postRepository.findAllByUserId(id);
        return PostMapper.mapToList(postList);
    }

    public Page<PostModel> findPostWithPagination(Pageable pageable, PostQueryModel queryModel) {
        Page<PostEntity> post = postRepository.findAllPost(queryModel.getTitle(), queryModel.getPostType(), pageable);
        return post.map(PostMapper::mapTo);
    }
}

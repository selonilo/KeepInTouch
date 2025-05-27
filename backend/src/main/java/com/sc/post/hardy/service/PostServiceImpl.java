package com.sc.post.hardy.service;

import com.sc.post.hardy.exception.NotFoundException;
import com.sc.post.hardy.model.dto.post.PostModel;
import com.sc.post.hardy.model.dto.post.PostQueryModel;
import com.sc.post.hardy.model.entity.LikeEntity;
import com.sc.post.hardy.model.entity.PostEntity;
import com.sc.post.hardy.model.entity.ViewEntity;
import com.sc.post.hardy.model.mapper.PostMapper;
import com.sc.post.hardy.repository.LikeRepository;
import com.sc.post.hardy.repository.PostRepository;
import com.sc.post.hardy.repository.UserRepository;
import com.sc.post.hardy.repository.ViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ViewRepository viewRepository;

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
            return PostMapper.mapTo(post);
        } else {
            throw new NotFoundException(id.toString());
        }
    }

    public void likePost(Long postId, Long userId) {
        var optPost = postRepository.findById(postId);
        var optUser = userRepository.findById(userId);
        if (optPost.isPresent() && optUser.isPresent()) {
            LikeEntity likeEntity = new LikeEntity();
            likeEntity.setPostId(postId);
            likeEntity.setUserId(userId);
            likeRepository.saveAndFlush(likeEntity);
        } else {
            throw new NotFoundException(postId.toString().concat(userId.toString()));
        }
    }

    public void unLikePost(Long postId, Long userId) {
        var optPost = postRepository.findById(postId);
        var optUser = userRepository.findById(userId);
        if (optPost.isPresent() && optUser.isPresent()) {
            var optLike = likeRepository.findByPostIdAndUserId(postId, userId);
            optLike.ifPresent(likeEntity -> likeRepository.delete(likeEntity));
        } else {
            throw new NotFoundException(postId.toString().concat(userId.toString()));
        }
    }

    public List<PostModel> getList(Long userId) {
        List<PostEntity> postList = postRepository.findAll();
        List<PostModel> postModelList = PostMapper.mapToList(postList);
        for (var post : postModelList) {
            var optView = viewRepository.findByPostIdAndUserId(post.getId(), userId);
            if (optView.isEmpty()) {
                ViewEntity viewEntity = new ViewEntity();
                viewEntity.setPostId(post.getId());
                viewEntity.setUserId(userId);
                viewRepository.saveAndFlush(viewEntity);
            }
            post.setIsLiked(likeRepository.findByPostIdAndUserId(post.getId(), userId).isPresent());
            post.setLikeNumber(likeRepository.countByPostId(post.getId()));
            post.setViewNumber(viewRepository.countByPostId(post.getId()));
            var optUser = userRepository.findById(post.getUserId());
            optUser.ifPresent(user -> {
                post.setUserImageUrl(user.getImageUrl());
                post.setName(user.getName());
            });
        }
        return postModelList;
    }

    public List<PostModel> getListByUserId(Long userId) {
        List<PostEntity> postList = postRepository.findAllByUserId(userId);
        List<PostModel> postModelList = PostMapper.mapToList(postList);
        for (var post : postModelList) {
            var optView = viewRepository.findByPostIdAndUserId(post.getId(), userId);
            if (optView.isEmpty()) {
                ViewEntity viewEntity = new ViewEntity();
                viewEntity.setPostId(post.getId());
                viewEntity.setUserId(userId);
                viewRepository.saveAndFlush(viewEntity);
            }
            post.setIsLiked(likeRepository.findByPostIdAndUserId(post.getId(), userId).isPresent());
            post.setLikeNumber(likeRepository.countByPostId(post.getId()));
            post.setViewNumber(viewRepository.countByPostId(post.getId()));
            var optUser = userRepository.findById(post.getUserId());
            optUser.ifPresent(user -> {
                post.setUserImageUrl(user.getImageUrl());
                post.setName(user.getName());
            });
        }
        return postModelList;
    }

    public Page<PostModel> findPostWithPagination(Pageable pageable, PostQueryModel queryModel) {
        Page<PostEntity> post = postRepository.findAllPost(queryModel.getTitle(), queryModel.getPostType(), pageable);
        return post.map(PostMapper::mapTo);
    }

    public Boolean isLiked(Long postId, Long userId) {
        return likeRepository.findByPostIdAndUserId(postId, userId).isPresent();
    }
}

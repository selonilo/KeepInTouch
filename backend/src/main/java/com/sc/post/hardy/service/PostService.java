package com.sc.post.hardy.service;

import com.sc.post.hardy.model.dto.post.PostModel;
import com.sc.post.hardy.model.dto.post.PostQueryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    PostModel save(PostModel postModel);
    void delete(Long id);
    PostModel getById(Long id);
    PostModel likePost(Long id);
    PostModel unLikePost(Long id);
    List<PostModel> getList();
    List<PostModel> getListByUserId(Long id);
    Page<PostModel> findPostWithPagination(Pageable pageable, PostQueryModel queryModel);
}

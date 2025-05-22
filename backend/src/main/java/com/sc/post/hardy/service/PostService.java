package com.sc.post.hardy.service;

import com.sc.post.hardy.model.dto.post.PostModel;
import com.sc.post.hardy.model.dto.post.PostQueryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    PostModel save(PostModel postModel);
    void delete(Long id);
    PostModel getById(Long id);
    PostModel likePost(Long id);
    PostModel unLikePost(Long id);
    Page<PostModel> findPostWithPagination(Pageable pageable, PostQueryModel queryModel);
}

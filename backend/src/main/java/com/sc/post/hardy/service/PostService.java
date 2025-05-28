package com.sc.post.hardy.service;

import com.sc.post.hardy.model.dto.TotalStatsModel;
import com.sc.post.hardy.model.dto.post.CommentModel;
import com.sc.post.hardy.model.dto.post.PostModel;
import com.sc.post.hardy.model.dto.post.PostQueryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    PostModel save(PostModel postModel);
    void delete(Long id);
    PostModel getById(Long id);
    void likePost(Long postId, Long userId);
    void unLikePost(Long postId, Long userId);
    List<PostModel> getList(Long userId);
    List<PostModel> getListByUserId(Long id);
    List<PostModel> getListByUserIdAndLoginUserId(Long userId, Long loginUserId);
    Page<PostModel> findPostWithPagination(Pageable pageable, PostQueryModel queryModel);
    Boolean isLiked(Long postId, Long userId);
    String uploadImage(Long postId, MultipartFile file);
    void deleteImage(Long postId);
    TotalStatsModel getTotalStats();
    void commentPost(CommentModel commentModel);
    void deleteComment(Long commentId);
}

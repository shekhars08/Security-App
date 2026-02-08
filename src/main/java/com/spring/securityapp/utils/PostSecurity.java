package com.spring.securityapp.utils;

import com.spring.securityapp.dto.PostDTO;
import com.spring.securityapp.entities.UserEntity;
import com.spring.securityapp.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSecurity {

    private final PostService postService;

    public boolean isAuthorOfPost(Long postId){
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostDTO post = postService.getPostById(postId);
        assert user != null;
        return post.getAuthor().getId().equals(user.getId());
    }
}

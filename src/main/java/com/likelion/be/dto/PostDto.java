package com.likelion.be.dto;

import com.likelion.be.domain.Member;
import com.likelion.be.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDto {
    private Long id;
    private Member member;
    private String title;
    private String content;
    private LocalDateTime postDate;

    public static PostDto from(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setMember(post.getMember());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setPostDate(post.getPostDate());
        return postDto;
    }
}

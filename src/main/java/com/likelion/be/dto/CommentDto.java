package com.likelion.be.dto;

import com.likelion.be.domain.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CommentDto {
    private Long id;
    private Long postId;
    private Long memberId;
    private String content;
    private LocalDate commentDate;

    //Comment를 CommentDto로 변환
    public static CommentDto from(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setPostId(comment.getPost().getId());
        dto.setMemberId(comment.getMember().getId());
        dto.setContent(comment.getContent());
        dto.setCommentDate(comment.getCommentDate());
        return dto;
    }
}

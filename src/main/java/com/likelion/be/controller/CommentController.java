package com.likelion.be.controller;

import com.likelion.be.domain.Comment;
import com.likelion.be.dto.CommentDto;
import com.likelion.be.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 작성 API
    @Operation(summary = "createComment", description = "댓글 작성", tags = {"Comment"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping // POST 요청을 처리
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent()); // 댓글 내용 설정
        comment.setCommentDate(commentDto.getCommentDate()); // 댓글 작성 날짜 설정

        Comment createdComment = commentService.registerComment(comment, commentDto.getPostId(), commentDto.getMemberId());
        return ResponseEntity.ok(CommentDto.from(createdComment)); // 생성된 댓글 반환
    }

    // 모든 댓글 조회 API
    @Operation(summary = "getAllComments", description = "모든 댓글 조회", tags = {"Comment"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        List<CommentDto> commentDtos = comments.stream()
                .map(CommentDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(commentDtos);
    }

    // ID로 댓글 조회 API
    @Operation(summary = "getCommentById", description = "ID로 댓글 조회", tags = {"Comment"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long id) {
        Optional<Comment> commentOptional = commentService.getCommentById(id);
        if (commentOptional.isPresent()) {
            return ResponseEntity.ok(CommentDto.from(commentOptional.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
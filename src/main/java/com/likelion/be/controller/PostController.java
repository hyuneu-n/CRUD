package com.likelion.be.controller;

import com.likelion.be.domain.Post;
import com.likelion.be.dto.MemberDto;
import com.likelion.be.dto.PostDto;
import com.likelion.be.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시물 작성 API
    @Operation(summary = "registerPost", description = "게시글 작성", tags = {"Post"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = MemberDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    // 게시물 작성
    @PostMapping
    public ResponseEntity<PostDto> registerPost(@RequestBody PostDto postDto) {
        Post post = new Post();
        post.setMember(postDto.getMember());
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        Post registeredPost = postService.registerPost(post);
        return ResponseEntity.ok(PostDto.from(registeredPost));
    }
    // 모든 게시물 조회 API
    @Operation(summary = "getPost", description = "모든 게시물 조회", tags = {"Post"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = MemberDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    // 모든 게시물 조회
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        List<PostDto> postDtos = new ArrayList<>();
        for (Post post : posts) {
            PostDto dto = PostDto.from(post).from(post);
            postDtos.add(dto);
        }
        return ResponseEntity.ok(postDtos);
    }
    // ID로 게시물 조회 API
    @Operation(summary = "getPostById", description = "Id로 게시물 조회", tags = {"Post"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = MemberDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    // ID로 게시물 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name="id") Long id) {
        Optional<Post> postOptional = postService.getPostById(id);
        if (postOptional.isPresent()) {
            PostDto dto = PostDto.from(postOptional.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // 게시물 삭제 API
    @Operation(summary = "deletePost", description = "게시물 삭제", tags = {"Post"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = MemberDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable(name="id") Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    // 게시글 수정 API
    @Operation(summary = "updatePost", description = "게시글 수정", tags = {"Post"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = MemberDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    // 게시글 수정
    @PatchMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable(name="id") Long id, @RequestBody PostDto postDto) {
        try {
            Post updatedPost = postService.updatePost(id, postDto);
            return ResponseEntity.ok(PostDto.from(updatedPost));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

package com.likelion.be.service;

import com.likelion.be.domain.Member;
import com.likelion.be.domain.Post;
import com.likelion.be.dto.MemberDto;
import com.likelion.be.dto.PostDto;
import com.likelion.be.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    @Transactional
    public Post registerPost(Post post) {
        return postRepository.save(post);
    }
    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        postRepository.findAll().forEach(posts::add);
        return posts;
    }
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    @Transactional
    public Post updatePost(Long id, PostDto postDto) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            if (postDto.getTitle() != null) {
                post.setTitle(postDto.getTitle());
            }
            if (postDto.getContent() != null) {
                post.setContent(postDto.getContent());
            }
            return postRepository.save(post);
        } else {
            throw new RuntimeException("Post not found with id " + id);
        }
    }
}
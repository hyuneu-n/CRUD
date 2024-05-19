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
    private final PostRepository postRepository; // PostRepository 인스턴스 변수

    @Autowired // 의존성 주입 어노테이션
    public PostService(PostRepository postRepository) { // 생성자
        this.postRepository = postRepository;
    }

    @Transactional // 쓰기 트랜잭션 설정
    public Post registerPost(Post post) { // 게시물 등록 메서드
        return postRepository.save(post);
    }

    @Transactional // 쓰기 트랜잭션 설정
    public void deletePost(Long id) { // 게시물 삭제 메서드
        postRepository.deleteById(id);
    }

    public List<Post> getAllPosts() { // 모든 게시물 조회 메서드
        List<Post> posts = new ArrayList<>();
        postRepository.findAll().forEach(posts::add);
        return posts;
    }

    public Optional<Post> getPostById(Long id) { // ID로 게시물 조회 메서드
        return postRepository.findById(id);
    }

    @Transactional // 쓰기 트랜잭션 설정
    public Post updatePost(Long id, PostDto postDto) { // 게시물 수정 메서드
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isPresent()) { // 게시물 존재 여부 확인
            Post post = postOptional.get();
            if (postDto.getTitle() != null) { // 제목이 null이 아닌 경우
                post.setTitle(postDto.getTitle());
            }
            if (postDto.getContent() != null) { // 내용이 null이 아닌 경우
                post.setContent(postDto.getContent());
            }
            return postRepository.save(post); // 게시물 저장
        } else {
            throw new RuntimeException("Post not found with id " + id); // 게시물 미존재 예외 발생
        }
    }
}
package com.likelion.be.service;

import com.likelion.be.domain.Comment;
import com.likelion.be.domain.Member;
import com.likelion.be.domain.Post;
import com.likelion.be.dto.CommentDto;
import com.likelion.be.repository.CommentRepository;
import com.likelion.be.repository.MemberRepository;
import com.likelion.be.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, MemberRepository memberRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    // 댓글 등록
    @Transactional
    public Comment registerComment(Comment comment, Long postId, Long memberId) {
        Optional<Post> postOptional = postRepository.findById(postId); // postId로 게시글 조회
        Optional<Member> memberOptional = memberRepository.findById(memberId); // memberId로 회원 조회

        // 게시글과 회원이 존재하는지 확인
        if (postOptional.isPresent() && memberOptional.isPresent()) {
            comment.setPost(postOptional.get()); // 댓글에 게시글 설정
            comment.setMember(memberOptional.get()); // 댓글에 회원 설정
            return commentRepository.save(comment); // 댓글 저장 및 반환
        } else {
            // 게시글 또는 회원이 존재하지 않을 경우 예외 발생
            throw new RuntimeException("Not Found");
        }
    }

    // 모든 댓글 조회
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    // ID로 댓글 조회
    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }
}

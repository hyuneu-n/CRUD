package com.likelion.be.service;

import com.likelion.be.domain.Member;
import com.likelion.be.dto.MemberDto;
import com.likelion.be.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true) // 기본적으로 읽기 전용 트랜잭션 설정
public class MemberService {
    private final MemberRepository memberRepository; // MemberRepository 인스턴스 변수

    @Autowired // 의존성 주입 어노테이션
    public MemberService(MemberRepository memberRepository) { // 생성자
        this.memberRepository = memberRepository;
    }

    @Transactional // 쓰기 트랜잭션 설정
    public Member registerMember(Member member) { // 회원 등록 메서드
        return memberRepository.save(member);
    }

    @Transactional // 쓰기 트랜잭션 설정
    public void deleteMember(Long id) { // 회원 삭제 메서드
        memberRepository.deleteById(id);
    }

    public List<Member> getAllMembers() { // 모든 회원 조회 메서드
        return memberRepository.findAll();
    }

    public Optional<Member> getMemberById(Long id) { // ID로 회원 조회 메서드
        return memberRepository.findById(id);
    }

    @Transactional // 쓰기 트랜잭션 설정
    public Member updateMember(Long id, MemberDto memberDto) { // 회원 정보 수정 메서드
        Optional<Member> memberOptional = memberRepository.findById(id);
        if (memberOptional.isPresent()) { // 회원 존재 여부 확인
            Member member = memberOptional.get();
            if (memberDto.getName() != null) { // 이름이 null이 아닌 경우
                member.setName(memberDto.getName());
            }
            if (memberDto.getEmail() != null) { // 이메일이 null이 아닌 경우
                member.setEmail(memberDto.getEmail());
            }
            return memberRepository.save(member); // 회원 정보 저장
        } else {
            throw new RuntimeException("Not Found"); // 회원 미존재 예외 발생
        }
    }
}
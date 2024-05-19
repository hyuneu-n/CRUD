package com.likelion.be.dto;

import com.likelion.be.domain.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MemberDto {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime registerDate;

    // Member 엔티티를 MemberDto로 변환하는 메서드
    public static MemberDto from(Member member) {
        MemberDto memberDto = new MemberDto();
        memberDto.setId(member.getId());
        memberDto.setName(member.getName());
        memberDto.setEmail(member.getEmail());
        memberDto.setRegisterDate(member.getRegisterDate());
        return memberDto;
    }
}
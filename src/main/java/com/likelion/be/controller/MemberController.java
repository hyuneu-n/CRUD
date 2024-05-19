package com.likelion.be.controller;

import com.likelion.be.domain.Member;
import com.likelion.be.dto.MemberDto;
import com.likelion.be.service.MemberService;
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
@RequestMapping("/api/members") // API 경로 설정
public class MemberController { // MemberController 클래스 선언

    private final MemberService memberService; // MemberService 인스턴스 변수

    @Autowired // 의존성 주입 어노테이션
    public MemberController(MemberService memberService) { // 생성자
        this.memberService = memberService;
    }

    // 회원가입 API
    @Operation(summary = "createMember", description = "회원가입", tags = {"Member"}) // Swagger 설명 어노테이션
    @ApiResponses({ // Swagger 응답 어노테이션
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = MemberDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping // POST 요청 매핑
    public ResponseEntity<MemberDto> registerMember(@RequestBody MemberDto memberDto) { // 회원가입 메서드
        Member member = new Member();
        member.setName(memberDto.getName());
        member.setEmail(memberDto.getEmail());

        Member registeredMember = memberService.registerMember(member);
        return ResponseEntity.ok(MemberDto.from(registeredMember));
    }

    // 모든 회원 조회 API
    @Operation(summary = "getMember", description = "회원 조회", tags = {"Member"}) // Swagger 설명 어노테이션
    @ApiResponses({ // Swagger 응답 어노테이션
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = MemberDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping // GET 요청 매핑
    public ResponseEntity<List<MemberDto>> getAllMembers() { // 모든 회원 조회 메서드
        List<Member> members = memberService.getAllMembers();
        List<MemberDto> memberDtos = new ArrayList<>();
        for (Member member : members) {
            MemberDto dto = MemberDto.from(member);
            memberDtos.add(dto);
        }
        return ResponseEntity.ok(memberDtos);
    }

    // ID로 회원 조회 API
    @Operation(summary = "getMemberById", description = "개별 회원 조회", tags = {"Member"}) // Swagger 설명 어노테이션
    @ApiResponses({ // Swagger 응답 어노테이션
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = MemberDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/{id}") // GET 요청 매핑
    public ResponseEntity<MemberDto> getMemberById(@PathVariable(name = "id") Long id) { // ID로 회원 조회 메서드
        Optional<Member> memberOptional = memberService.getMemberById(id);
        if (memberOptional.isPresent()) {
            MemberDto dto = MemberDto.from(memberOptional.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 회원 탈퇴 API
    @Operation(summary = "deleteMember", description = "회원 탈퇴", tags = {"Member"}) // Swagger 설명 어노테이션
    @ApiResponses({ // Swagger 응답 어노테이션
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = MemberDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @DeleteMapping("/{id}") // DELETE 요청 매핑
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) { // 회원 탈퇴 메서드
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    // 회원 정보 수정 API
    @Operation(summary = "updateMember", description = "회원 정보 수정", tags = {"Member"}) // Swagger 설명 어노테이션
    @ApiResponses({ // Swagger 응답 어노테이션
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = MemberDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PatchMapping("/{id}") // PATCH 요청 매핑
    public ResponseEntity<MemberDto> UpdateMember(@PathVariable Long id, @RequestBody MemberDto memberDto) { // 회원 정보 수정 메서드
        try {
            Member updatedMember = memberService.updateMember(id, memberDto);
            return ResponseEntity.ok(MemberDto.from(updatedMember));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
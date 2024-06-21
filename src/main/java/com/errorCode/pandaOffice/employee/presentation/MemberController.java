package com.errorCode.pandaOffice.employee.presentation;


import com.errorCode.pandaOffice.employee.dto.request.MemberSignupRequest;
import com.errorCode.pandaOffice.employee.dto.response.ProfileResponse;
import com.errorCode.pandaOffice.employee.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /* 회원 가입 */
//    @PostMapping("/signup")
//    public ResponseEntity<Void> signup(@RequestBody @Valid MemberSignupRequest memberRequest) {
//
//        memberService.signup(memberRequest);
//
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }

    /* 인증 테스트를 위한 메소드 */
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("test 응답 완료");
    }

    /* 본인 프로필 조회 */
    @GetMapping("/{memberId}")
    @PreAuthorize("#memberId == authentication.principal.username")
//    @PostAuthorize("returnObject.writer == authentication.principal.username")
    public ResponseEntity<ProfileResponse> getProfile(@PathVariable int memberId) {

        ProfileResponse profileResponse = memberService.getProfile(Integer.parseInt(String.valueOf(memberId)));

        return ResponseEntity.ok(profileResponse);
    }

    /* 로그아웃 시 DB 토큰 무효화 */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal UserDetails userDetails) {

        memberService.updateRefreshToken(Integer.parseInt(userDetails.getUsername()), null);

        return ResponseEntity.ok().build();
    }
    /* 아이디 찾기 */
    @PostMapping("/find-id")
    public ResponseEntity<String> findId(@RequestParam String name,
                                         @RequestParam String email,
                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate) {
        System.out.println("잘 돌아가고 있니");
        int memberId = memberService.findId(name, email, birthDate);
        
        if (memberId != 0) { // 수정: memberId가 0이 아니라면 찾은 경우입니다.
            return ResponseEntity.ok("찾은 아이디: " + memberId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    }
















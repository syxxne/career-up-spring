package com.careerup.careerupspring.controller;

import com.careerup.careerupspring.dto.UserDTO;
import com.careerup.careerupspring.service.MypageService;
import com.careerup.careerupspring.service.S3UploadService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
public class MypageController {
    @Autowired
    MypageService mypageService;
    @Autowired
    S3UploadService s3UploadService;

    @Value("${jwt.token.key}")
    private String tokenKey;

    // 마이페이지 요청
    @GetMapping("/mypage")
    public void getPage(){}

    // 구직자 수정
    @PatchMapping("/mypage")
    @ResponseBody
    public void patchPage(@ModelAttribute UserDTO userDTO, @RequestParam(name="profile", required = false) MultipartFile file, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) throws IOException {

        System.out.println(authorizationHeader);
        String token = extractToken(authorizationHeader);

        if (isValidToken(token)) {
            try {
                Claims claims = Jwts.parser().setSigningKey(tokenKey).parseClaimsJws(token).getBody();

                // 클레임에서 이메일 정보 추출
                String userEmail = claims.getSubject();
                System.out.println("userEmail" + userEmail);

                // 획득한 이메일 정보로 업데이트를 수행할 DTO 생성
                UserDTO userToUpdate = new UserDTO();
                userToUpdate.setEmail(userEmail);
                userToUpdate.setProfile(userDTO.getProfile());

                // 파일이 전송되었다면 프로필 업데이트
                if (file != null && !file.isEmpty()) {
                    String imgPath = s3UploadService.upload(file);
                    userToUpdate.setProfile(imgPath);
                }

                // 업데이트 서비스 호출
                mypageService.updateMypage(userEmail, userToUpdate);
            } catch (Exception e) {
                // 토큰 파싱이나 업데이트 중에 문제가 발생한 경우 처리
                e.printStackTrace();
            }
        }
    }

    // 헤더에서 토큰을 추출하고 반환
    private String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    // 토큰 유효성 검증
    private boolean isValidToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(tokenKey).parseClaimsJws(token).getBody();
            return true;
        } catch (Exception e) {
            // 토큰이 유효하지 않은 경우 처리
            return false;
        }
    }


    // 재직자 작성 및 수정
    @PutMapping("/mypage")
    @ResponseBody
    public void putPage(@RequestBody UserDTO userDTO){

    }
}

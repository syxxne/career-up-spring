package com.careerup.careerupspring.controller;

import com.careerup.careerupspring.dto.UserDTO;
import com.careerup.careerupspring.service.MypageService;
import com.careerup.careerupspring.service.S3UploadService;
import com.careerup.careerupspring.service.SeekerPatchService;
import com.careerup.careerupspring.service.WorkerPutService;
import com.careerup.careerupspring.util.JwtTokenUtil;
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
    @Autowired
    SeekerPatchService seekerPatchService;
    @Autowired
    WorkerPutService workerPutService;

    // 마이페이지 요청
    @GetMapping("/mypage")
    @ResponseBody
    public UserDTO getPage(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader){
        return mypageService.getMyPage(authorizationHeader);
    }

    // 구직자 수정
    @PatchMapping("/mypage")
    @ResponseBody
    public boolean patchPage(@RequestPart(name = "user") UserDTO userDTO,
                          @RequestPart(name="profile", required = false) MultipartFile file,
                          @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader){
        return seekerPatchService.updatePatchMypage(authorizationHeader, file, userDTO);

    }

    // 재직자 작성 및 수정
    @PutMapping("/mypage")
    @ResponseBody
    public boolean putPage(@RequestPart(name = "user") UserDTO userDTO,
                        @RequestPart(name="profile",required = false) MultipartFile file,
                        @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        return workerPutService.updatePutMypage(authorizationHeader, file, userDTO);
    }

}
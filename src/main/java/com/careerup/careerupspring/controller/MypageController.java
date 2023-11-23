package com.careerup.careerupspring.controller;

import com.careerup.careerupspring.dto.UserDTO;
import com.careerup.careerupspring.service.GalleryService;
import com.careerup.careerupspring.service.S3UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class MypageController {
    @Autowired
    GalleryService galleryService;
    @Autowired
    S3UploadService s3UploadService;

    // 마이페이지 요청
    @GetMapping("/mypage")
    public void getPage(){}

    // 구직자 수정
    @PatchMapping("/mypage")
    @ResponseBody
    public void patchPage(@RequestBody UserDTO userDTO, @RequestParam(required = false)MultipartFile file) throws IOException {
        galleryService.updateMypage(userDTO);

        if(file !=null && !file.isEmpty()){
            String imgPath = s3UploadService.upload(file);
            userDTO.setProfile(imgPath);
            galleryService.updateMypage(userDTO);
        }
    }

    // 재직자 작성 및 수정
    @PutMapping("/mypage")
    @ResponseBody
    public void putPage(@RequestBody UserDTO userDTO){

    }
}

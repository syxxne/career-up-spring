package com.careerup.careerupspring.controller;

import com.careerup.careerupspring.dto.UserDTO;
import com.careerup.careerupspring.service.MypageService;
import com.careerup.careerupspring.service.S3UploadService;
import org.springframework.beans.factory.annotation.Autowired;
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

    // 마이페이지 요청
    @GetMapping("/mypage")
    public void getPage(){}

    // 구직자 수정
    @PatchMapping("/mypage")
    @ResponseBody
    public void patchPage(@ModelAttribute UserDTO userDTO, @RequestParam(name="profile", required = false) MultipartFile file) throws IOException {

        System.out.println("file"+ file);
        if(file !=null && !file.isEmpty()){

            String imgPath = s3UploadService.upload(file);
            System.out.println("image"+ imgPath);
            userDTO.setProfile(imgPath);
            mypageService.updateMypage(userDTO);
        } else {
            mypageService.updateMypage(userDTO);
        }
    }

    // 재직자 작성 및 수정
    @PutMapping("/mypage")
    @ResponseBody
    public void putPage(@RequestBody UserDTO userDTO){

    }
}

package com.todo.springboot.controller;

import com.todo.springboot.dto.ResponseDTO;
import com.todo.springboot.dto.UserDTO;
import com.todo.springboot.model.UserEntity;
import com.todo.springboot.security.TokenProvider;
import com.todo.springboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){
        try {
            // 요청을 이용해 저장할 사용자 만들기
            UserEntity userEntity = UserEntity.builder()
                    .email(userDTO.getEmail())
                    .username(userDTO.getUsername())
                    .password(userDTO.getPassword())
                    .build();
            // 서비스를 이용해 리포지토리에 사용자 저장
            UserEntity registeredUser = userService.create(userEntity);
            UserDTO responseUserDTO = UserDTO.builder()
                    .email(registeredUser.getEmail())
                    .username(registeredUser.getUsername())
                    .password(registeredUser.getPassword())
                    .build();

            return ResponseEntity.ok().body(responseUserDTO);
        }catch (Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO){
        UserEntity user = userService.getByCredentials(
                userDTO.getEmail(),
                userDTO.getPassword());

        if(user != null) {
            // 토큰 생성
            final String token = tokenProvider.create(user);
            final UserDTO responseUserDTO = UserDTO.builder()
                    .email(user.getEmail())
                    .id(user.getId())
                    .token(token)
                    .build();
            return ResponseEntity.ok().body(responseUserDTO);
        } else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("Login Failed.")
                    .build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}

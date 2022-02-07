package com.todo.springboot.controller;

import com.todo.springboot.dto.TodoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("todo")
public class TodoController {

    @GetMapping("/testTodo")
    public ResponseEntity<?> testTodo(){
        TodoDTO response = TodoDTO.builder().id("k").title("s").done(true).build();
        return ResponseEntity.ok().body(response);
    }
}

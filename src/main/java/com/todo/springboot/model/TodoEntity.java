package com.todo.springboot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoEntity {
    private String id; // 오브젝트 아이디
    private String userId; // 오브젝트 생성한 사용자의 아이디
    private String title; // ex) 운동하기
    private boolean done; // 완료 여부
}

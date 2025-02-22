package com.hwk9407.bookmanagementassignment.api.author.service;

import com.hwk9407.bookmanagementassignment.api.author.dto.request.AddAuthorRequest;
import com.hwk9407.bookmanagementassignment.api.author.dto.response.AddAuthorResponse;
import com.hwk9407.bookmanagementassignment.domain.author.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AddAuthorResponse addAuthor(AddAuthorRequest req) {
        // DB에 이메일이 중복됐는지 확인
        // 없으면 DB 저장
        // id 값 Response Dto에 저장 후 반환
        return null;
    }
}

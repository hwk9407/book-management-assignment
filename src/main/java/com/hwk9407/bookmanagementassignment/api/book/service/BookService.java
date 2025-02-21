package com.hwk9407.bookmanagementassignment.api.book.service;

import com.hwk9407.bookmanagementassignment.api.book.dto.request.AddBookRequest;
import com.hwk9407.bookmanagementassignment.api.book.dto.response.AddBookResponse;
import com.hwk9407.bookmanagementassignment.domain.book.BookRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public AddBookResponse addBook(@Valid AddBookRequest req) {
        // isbn 유효성 검사 (숫자 여부, 길이 체크, 마지막 자리 0 체크)
        // isbn 이 DB에 이미 있는지 중복 검사
        // 저자가 DB에 존재하는지 검사
        // 새로운 책 엔티티 생성 후 저장
        // 저장된 엔티티를 DTO 에 담아 반환

        return null;
    }
}

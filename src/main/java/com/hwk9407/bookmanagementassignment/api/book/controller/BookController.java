package com.hwk9407.bookmanagementassignment.api.book.controller;

import com.hwk9407.bookmanagementassignment.api.book.dto.request.AddBookRequest;
import com.hwk9407.bookmanagementassignment.api.book.dto.request.RetrieveAllBooksRequest;
import com.hwk9407.bookmanagementassignment.api.book.dto.request.UpdateBookRequest;
import com.hwk9407.bookmanagementassignment.api.book.dto.response.AddBookResponse;
import com.hwk9407.bookmanagementassignment.api.book.dto.response.RetrieveAllBooksResponse;
import com.hwk9407.bookmanagementassignment.api.book.dto.response.RetrieveBookResponse;
import com.hwk9407.bookmanagementassignment.api.book.service.BookService;
import com.hwk9407.bookmanagementassignment.util.DtoValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;

@Tag(name = "도서 API", description = "도서 관련 CRUD API")
@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final DtoValidator dtoValidator;

    @Operation(summary = "도서 생성", description = "새로운 도서를 추가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "도서 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 또는 ISBN 유효성 검증 실패"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 데이터 접근")
    })
    @PostMapping("/books")
    public ResponseEntity<Void> addBook(@Valid @RequestBody AddBookRequest req) {
        AddBookResponse res = bookService.addBook(req);
        return ResponseEntity
                .created(URI.create(String.valueOf(res.id())))
                .build();
    }

    @Operation(summary = "도서 목록 조회", description = "등록된 모든 도서를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @GetMapping("/books")
    public ResponseEntity<RetrieveAllBooksResponse> retrieveAllBooks(
            @Parameter(description = "페이지 번호 (기본값: 1)", example = "1")
            @RequestParam(defaultValue = "1") int page,

            @Parameter(description = "페이지 크기 (기본값: 10)", example = "10")
            @RequestParam(defaultValue = "10") int size,

            @Parameter(description = "정렬 방향 (ASC/DESC, 기본값: DESC)", example = "DESC")
            @RequestParam(defaultValue = "DESC") String orderBy,

            @Parameter(description = "정렬 기준 필드 (기본값: publicationDate)", example = "publicationDate")
            @RequestParam(defaultValue = "publicationDate") String sortBy,

            @Parameter(description = "저자 ID (선택사항)", example = "1")
            @RequestParam(required = false) Long authorId,

            @Parameter(description = "시작일 (선택사항)", example = "2025-02-25")
            @RequestParam(required = false) LocalDate startPubDate,

            @Parameter(description = "종료일 (선택사항)", example = "2025-02-25")
            @RequestParam(required = false) LocalDate endPubDate
    ) {
        RetrieveAllBooksRequest req = new RetrieveAllBooksRequest(page, size, orderBy, sortBy, authorId, startPubDate, endPubDate);
        dtoValidator.validate(req);
        if (startPubDate != null && endPubDate != null) {
            if (startPubDate.isAfter(endPubDate)) {
                throw new IllegalArgumentException("종료일을 시작일보다 이전으로 조회할 수 없습니다.");
            }
        }
        RetrieveAllBooksResponse res = bookService.retrieveAllBooks(req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "도서 상세 조회", description = "특정 도서의 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "도서를 찾을 수 없음")
    })
    @GetMapping("/books/{id}")
    public ResponseEntity<RetrieveBookResponse> retrieveBook(
            @Parameter(description = "조회할 도서의 ID", example = "1")
            @PathVariable Long id
    ) {
        RetrieveBookResponse res = bookService.retrieveBook(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "도서 수정", description = "도서 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "404", description = "도서 혹은 저자를 찾을 수 없음")
    })
    @PatchMapping("/books/{id}")
    public ResponseEntity<Void> updateBook(
            @Parameter(description = "수정할 도서의 ID", example = "1")
            @PathVariable Long id,

            @Valid @RequestBody UpdateBookRequest req
    ) {
        bookService.updateBook(id, req);
        return ResponseEntity
                .ok()
                .build();
    }

    @Operation(summary = "도서 삭제", description = "특정 도서를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "도서를 찾을 수 없음")
    })
    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "삭제할 도서의 ID", example = "1")
            @PathVariable Long id
    ) {
        bookService.deleteBook(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}

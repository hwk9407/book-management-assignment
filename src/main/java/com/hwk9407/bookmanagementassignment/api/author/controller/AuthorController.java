package com.hwk9407.bookmanagementassignment.api.author.controller;

import com.hwk9407.bookmanagementassignment.api.author.dto.request.AddAuthorRequest;
import com.hwk9407.bookmanagementassignment.api.author.dto.request.RetrieveAllAuthorsRequest;
import com.hwk9407.bookmanagementassignment.api.author.dto.request.UpdateAuthorRequest;
import com.hwk9407.bookmanagementassignment.api.author.dto.response.AddAuthorResponse;
import com.hwk9407.bookmanagementassignment.api.author.dto.response.RetrieveAllAuthorsResponse;
import com.hwk9407.bookmanagementassignment.api.author.dto.response.RetrieveAuthorResponse;
import com.hwk9407.bookmanagementassignment.api.author.service.AuthorService;
import com.hwk9407.bookmanagementassignment.api.book.dto.request.RetrieveAllBooksRequest;
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

@Tag(name = "저자 API", description = "저자 관련 CRUD API")
@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;
    private final DtoValidator dtoValidator;

    @Operation(summary = "저자 생성", description = "새로운 저자를 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "저자 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "409", description = "중복된 이메일로 인한 충돌")
    })
    @PostMapping("/authors")
    public ResponseEntity<Void> addAuthor(@Valid @RequestBody AddAuthorRequest req) {
        AddAuthorResponse res = authorService.addAuthor(req);
        return ResponseEntity
                .created(URI.create(String.valueOf(res.id())))
                .build();
    }

    @Operation(summary = "저자 목록 조회", description = "등록된 모든 저자를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터")
    })
    @GetMapping("/authors")
    public ResponseEntity<RetrieveAllAuthorsResponse> retrieveAllAuthors(
            @Parameter(description = "페이지 번호 (기본값: 1)", example = "1")
            @RequestParam(defaultValue = "1") int page,

            @Parameter(description = "페이지 크기 (기본값: 10)", example = "10")
            @RequestParam(defaultValue = "10") int size
    ) {
        RetrieveAllAuthorsRequest req = new RetrieveAllAuthorsRequest(page, size);
        dtoValidator.validate(req);
        RetrieveAllAuthorsResponse res = authorService.retrieveAllAuthors(req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "저자 상세 조회", description = "특정 저자의 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 저자")
    })
    @GetMapping("/authors/{id}")
    public ResponseEntity<RetrieveAuthorResponse> retrieveAuthor (
            @Parameter(description = "조회할 저자의 ID", example = "1")
            @PathVariable Long id
    ) {
        RetrieveAuthorResponse res = authorService.retrieveAuthor(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "저자 정보 수정", description = "저자의 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 저자")
    })
    @PatchMapping("/authors/{id}")
    public ResponseEntity<Void> updateAuthor (
            @Parameter(description = "수정할 저자의 ID", example = "1")
            @PathVariable Long id,

            @Valid @RequestBody UpdateAuthorRequest req
    ) {
        authorService.updateAuthor(id, req);
        return ResponseEntity
                .ok()
                .build();
    }

    @Operation(summary = "저자 삭제", description = "특정 저자를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "400", description = "삭제할 수 없는 저자 (연관된 도서 존재)"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 저자")
    })
    @DeleteMapping("/authors/{id}")
    public ResponseEntity<Void> deleteAuthor (
            @Parameter(description = "삭제할 저자의 ID", example = "1")
            @PathVariable Long id
    ) {
        authorService.deleteAuthor(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}

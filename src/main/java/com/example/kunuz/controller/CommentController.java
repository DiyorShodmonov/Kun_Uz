package com.example.kunuz.controller;

import com.example.kunuz.dto.comment.CommentCreateDTO;
import com.example.kunuz.dto.comment.CommentResponseDTO;
import com.example.kunuz.dto.comment.CommentUpdateDTO;
import com.example.kunuz.dto.profile.ProfileResponseDTO;
import com.example.kunuz.enums.Language;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.service.CommentService;
import com.example.kunuz.util.HttpRequestUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/comment")
@Tag(name = "Comment controller", description = "This controller worked with comments")
public class CommentController {
    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }


    @Operation(summary = "Method for create comment", description = "This method used to create comment")
    @PostMapping("/sec/create")
    public ResponseEntity<?> create(@RequestBody CommentCreateDTO dto, HttpServletRequest request) {

        Integer profileId = HttpRequestUtil.getProfileId(request);
        log.info("Create comment profileId = " + profileId + " " + dto);

        CommentResponseDTO result = service.create(dto, profileId);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Method for update comment", description = "This method used to update comment")
    @PutMapping("/sec/update")
    public ResponseEntity<?> update(@RequestBody CommentUpdateDTO dto, HttpServletRequest request,
                                    @RequestHeader(value = "Accept-Language", defaultValue = "RU") Language language) {
        Integer profileId = HttpRequestUtil.getProfileId(request);

        Boolean result = service.update(dto, profileId, language);

        return ResponseEntity.ok(result);
    }


    @Operation(summary = "Method for delete comment", description = "This method used to delete comment")
    @DeleteMapping("/sec/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id, HttpServletRequest request,
                                    @RequestHeader(value = "Accept-Language", defaultValue = "RU") Language language) {
        ProfileResponseDTO profile = HttpRequestUtil.getProfile(request);
        Boolean result = service.delete(id, profile, language);

        return ResponseEntity.ok(result);

    }

    @Operation(summary = "Method for get article's comment list ", description = "This method used to getting  article's comment list")
    @GetMapping("/get/{article_id}")
    public ResponseEntity<?> getListByArticleId(@PathVariable("article_id") String articleId) {
        List<CommentResponseDTO> result = service.getListByArticleId(articleId);
        return ResponseEntity.ok(result);
    }


    @Operation(summary = "Method for getting all comment list", description = "This method used gettin all comment list with pagination")
    @GetMapping("/sec/get")
    public ResponseEntity<?> getList(@RequestParam("page") Integer page, @RequestParam("size") Integer size,
                                     HttpServletRequest request) {

        HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);

        Page<CommentResponseDTO> result = service.getPageList(page, size);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Method for getting comment reply comment list", description = "This method used gettin comment  reply comment list ")
    @GetMapping("/get_reply/{id}")
    public ResponseEntity<?> getReplyList(@PathVariable("id") Integer id) {
        List<CommentResponseDTO> result = service.getReplyList(id);
        return ResponseEntity.ok(result);
    }


}

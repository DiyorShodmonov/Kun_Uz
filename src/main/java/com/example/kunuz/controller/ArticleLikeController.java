package com.example.kunuz.controller;

import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.service.ArticleLikeService;
import com.example.kunuz.util.HttpRequestUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/article_like")
@Tag(name = "ArticleLike  Controller")
public class ArticleLikeController {
    private final ArticleLikeService service;


    public ArticleLikeController(ArticleLikeService service) {
        this.service = service;
    }


    @Operation(summary = "ArticleLike  like method")
    @PostMapping("/like/{article_id}")
    public ResponseEntity<?> like(@PathVariable("article_id") String articleId, HttpServletRequest request) {

        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.USER);

        log.info("ArticleLike  like : article id = {}, profile id = {}", articleId, profileId);

        Boolean result = service.like(articleId, profileId);
        return ResponseEntity.ok(result);

    }

    @Operation(summary = "ArticleLike  dislike method")
    @PostMapping("/dislike/{article_id}")
    public ResponseEntity<?> dislike(@PathVariable("article_id") String articleId, HttpServletRequest request) {

        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.USER);

        log.info("ArticleLike  dislike : article id = {}, profile id = {}", articleId, profileId);

        Boolean result = service.dislike(articleId, profileId);
        return ResponseEntity.ok(result);

    }


    @Operation(summary = "ArticleLike  remove method")
    @DeleteMapping("/remove/{article_id}")
    public ResponseEntity<?> remove(@PathVariable("article_id") String articleId, HttpServletRequest request) {

        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.USER);

        log.info("ArticleLike  remove : article id = {}, profile id = {}", articleId, profileId);

        Boolean result = service.remove(articleId, profileId);
        return ResponseEntity.ok(result);

    }
}

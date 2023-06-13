package com.example.kunuz.controller;

import com.example.kunuz.dto.article.ArticleTypeShortDTO;
import com.example.kunuz.dto.article.ArticleTypeDTO;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.service.ArticleTypeService;
import com.example.kunuz.util.HttpRequestUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/article_type")
public class ArticleTypeController {

    private final ArticleTypeService service;

    public ArticleTypeController(ArticleTypeService service) {
        this.service = service;
    }

    @Operation(summary = "ArticleType create method", description = "This method used to create article type (ADMIN)")
    @PostMapping
    private ResponseEntity<?> create(@RequestBody ArticleTypeDTO dto, HttpServletRequest request) {
        log.info("ArticleType create : article type = {} ", dto);

        HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);

        ArticleTypeDTO result = service.create(dto);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "ArticleType update method")
    @PutMapping("/{id}")
    private ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody ArticleTypeDTO dto,
                                     HttpServletRequest request) {

        log.info("ArticleType create : id = {} , article type = {} ", id, dto);

        HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        ArticleTypeDTO result = service.update(id, dto);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "ArticleType delete method")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id, HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        log.info("ArticleType delete : id {}", id);

        Boolean result = service.deleteById(id);
        return ResponseEntity.ok(result);
    }


    @Operation(summary = "ArticleType get list method (Page)")
    @GetMapping("/list")
    public ResponseEntity<?> getList(@RequestParam("page") Integer page,
                                     @RequestParam("size") Integer size,
                                     HttpServletRequest request) {
        log.info("ArticleType get list (page) : page = {}, size = {} ", page, size);

        HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        Page<ArticleTypeDTO> result = service.getList(page, size);
        return ResponseEntity.ok(result);
    }


    @Operation(summary = "ArticleType get by lang mehtod")
    @GetMapping("/byLang/{lang}")
    public ResponseEntity<?> getByLang(@PathVariable("lang") String lang,
                                       HttpServletRequest request) {

        log.info("ArticleType get by lang : lang = {}", lang);
        HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        List<ArticleTypeShortDTO> result = service.getByLang(lang);
        return ResponseEntity.ok(result);
    }


}

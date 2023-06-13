package com.example.kunuz.controller;

import com.example.kunuz.dto.article.ArticleCreateDTO;
import com.example.kunuz.dto.article.ArticleResponseDTO;
import com.example.kunuz.dto.article.ArticleShortInfoDTO;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.service.ArticleService;
import com.example.kunuz.util.HttpRequestUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/article")

@Tag(name = "Article controller")
public class ArticleController {

    private final ArticleService service;

    public ArticleController(ArticleService service) {
        this.service = service;
    }


    @Operation(summary = "Article create method", description = "This method used to create article from moderator")
    @PostMapping("/admin/create")
    public ResponseEntity<?> create(@Valid @RequestBody ArticleCreateDTO dto, HttpServletRequest request) {
        log.info("Article create : title= {} , description = {} , imageId = {}", dto.getTitle(), dto.getDescription(), dto.getImageId());

        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.MODERATOR);
        ArticleResponseDTO result = service.create(dto, profileId);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Article update method", description = "This method used to update available article all fields")
    @PutMapping("/admin/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, ArticleCreateDTO dto, HttpServletRequest request) {
        log.info("Article update : id = {} , title= {} , description = {} , imageId = {}", id, dto.getTitle(), dto.getDescription(), dto.getImageId());


        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.MODERATOR);
        boolean result = service.update(id, dto, profileId);
        return ResponseEntity.ok(result);
    }


    @Operation(summary = "Article delete method", description = "This method used to delete article. Only moderators can delete")
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id, HttpServletRequest request) {
        log.info("Article delete : id = {} ", id);

        HttpRequestUtil.getProfileId(request, ProfileRole.MODERATOR);
        boolean result = service.delete(id);
        return ResponseEntity.ok(result);
    }


    @Operation(summary = "Article change status", description = "This method used to change status from NOT_PUBLISHED to PUBlISHED. Only Publisher can it")
    @PutMapping("/admin/change/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") String id, HttpServletRequest request) {
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.PUBLISHER);

        log.info("Article change status (publish) : id = {}, publisherId = {}", id, profileId);

        boolean result = service.changeStatus(id, profileId);
        return ResponseEntity.ok(result);
    }


    @Operation(summary = "Article get last 5 by type id", description = "This method used to getting 5 articles last published by article type id")
    @GetMapping("/user/get_last5/{type_id}")
    public ResponseEntity<?> getLast5(@PathVariable("type_id") Integer id) {

        log.info("Article get last 5 by article type : typeId = {}", id);

        List<ArticleShortInfoDTO> result = service.getLast5(id);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Article get last 3 by type id", description = "This method used to getting 3 articles last published by article type id")
    @GetMapping("/user/get_last3/{type_id}")
    public ResponseEntity<?> getLast3(@PathVariable("type_id") Integer id) {
        log.info("Article get last 3 by article type : typeId = {}", id);

        List<ArticleShortInfoDTO> result = service.getLast3(id);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Article get last 8 without given list", description = "This method used to getting 8 articles last published without given article id list")
    @GetMapping("/user/get_last8")
    public ResponseEntity<?> getLast8(@RequestBody() List<String> list) {
        log.info("Article get last 8 without given list :  list {}", list);

        List<ArticleShortInfoDTO> result = service.getLast8(list);
        return ResponseEntity.ok(result);
    }


    @Operation(summary = "Article get by id and lang", description = "This method used to get article by id and lang")
    @GetMapping("/user/get_by_id/{id}")
    public ResponseEntity<?> getByIdAndLang(@PathVariable("id") String id) {
        log.info("Article get by art and lang : id = {}" + id);

        ArticleResponseDTO result = service.getByIdAndLang(id);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Article get last 4 without given id", description = "This method used to get last 4 article by type id and without given id")
    @GetMapping("user/get_last4_by_type1/{type_id}")
    public ResponseEntity<?> getLast4ByType(@Param("id") String id, @PathVariable("type_id") Integer typeId) {
        log.info("Article get last 4 by type and without given id : id = {} , typeID = {}", id, typeId);

        List<ArticleShortInfoDTO> result = service.getLast4ByType1(id, typeId);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Article get 4 most read articles", description = "This method used to get 4 most read articles")
    @GetMapping("user/gettop4")
    public ResponseEntity<?> getTop4() {
        log.info("Article get 4 most read articles");
        List<ArticleShortInfoDTO> result = service.getTop4();
        return ResponseEntity.ok(result);
    }


    @Operation(summary = "Article get last 4 by type")
    @GetMapping("user/get_last4_by_type2/{type_id}")
    public ResponseEntity<?> getLast4ByType(@PathVariable("type_id") Integer typeId) {
        log.info("Article get last 4 by type : typeId = {}", typeId);
        List<ArticleShortInfoDTO> result = service.getLast4ByType2(typeId);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Article get last 5 by type id and region key")
    @GetMapping("user/get_last5_by_type_and_region")
    public ResponseEntity<?> getLast5ByTypeAdnRegion(@RequestParam("type_id") Integer typeId, @RequestParam("region") String regionKey) {
        log.info("Article get last 5 by type and region : typeId = {} , region key = {} ", typeId, regionKey);
        List<ArticleShortInfoDTO> result = service.getLast5ByTypeAndRegion(typeId, regionKey);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Article get list by region key (Page)")
    @GetMapping("user/get_list_by_type/{region_key}")
    public ResponseEntity<?> getListByType(@PathVariable("region_key") String regionKey, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        log.info("Article get list by region (Page) : region key {} , page = {} , size = {}", regionKey, page, size);
        Page<ArticleShortInfoDTO> result = service.getListByType(regionKey, page, size);
        return ResponseEntity.ok(result);
    }


    @Operation(summary = "Article increase view count")
    @PutMapping("user/inc_view/{id}")
    public ResponseEntity<?> increaseViewCount(@PathVariable("id") String id) {
        log.info("Article increase view count : article id = {}", id);
        Boolean result = service.increaseViewCount(id);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Article increase share count")
    @PutMapping("/user/inc_share/{id}")
    public ResponseEntity<?> increaseShareCount(@PathVariable("id") String id) {
        log.info("Article increase share count : article id = {}", id);
        Boolean result = service.increaseShareCount(id);
        return ResponseEntity.ok(result);
    }


}

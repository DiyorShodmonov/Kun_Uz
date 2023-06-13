package com.example.kunuz.controller;

import com.example.kunuz.dto.CategoryShortDTO;
import com.example.kunuz.dto.JwtDTO;
import com.example.kunuz.dto.CategoryDTO;
import com.example.kunuz.service.CategoryService;
import com.example.kunuz.util.JwtUtil;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService service;


    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CategoryDTO dto,
                                    @RequestHeader("Authorization") String token) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTOFromAuthToken(token);
        CategoryDTO result = service.create(dto, jwtDTO);
        return ResponseEntity.ok(result);
    }


    @PutMapping("/{id}")
    private ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody CategoryDTO dto,
                                     @RequestHeader("Authorization") String token) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTOFromAuthToken(token);
        CategoryDTO result = service.update(id, dto, jwtDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id,
                                        @RequestHeader("Authorization") String token) {

        JwtDTO jwtDTO = JwtUtil.getJwtDTOFromAuthToken(token);
        Boolean result = service.deleteById(id, jwtDTO);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/list")
    public ResponseEntity<?> getList(@RequestParam("page") Integer page,
                                     @RequestParam("size") Integer size,
                                     @RequestHeader("Authorization") String token) {

        JwtDTO jwtDTO = JwtUtil.getJwtDTOFromAuthToken(token);
        Page<CategoryDTO> result = service.getList(page, size, jwtDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/byLang/{lang}")
    public ResponseEntity<?> getByLang(@PathVariable("lang") String lang,
                                       @RequestHeader("Authorization") String token) {

        JwtDTO jwtDTO = JwtUtil.getJwtDTOFromAuthToken(token);
        List<CategoryShortDTO> result = service.getByLang(lang, jwtDTO);
        return ResponseEntity.ok(result);
    }

}

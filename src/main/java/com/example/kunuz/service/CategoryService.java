package com.example.kunuz.service;

import com.example.kunuz.dto.JwtDTO;
import com.example.kunuz.dto.CategoryDTO;
import com.example.kunuz.dto.CategoryShortDTO;
import com.example.kunuz.entity.CategoryEntity;
import com.example.kunuz.exp.ArticleTypeNotFoundException;
import com.example.kunuz.exp.RegionNotFoundException;
import com.example.kunuz.mapper.ICategoryMapper;
import com.example.kunuz.repository.CategoryRepository;
import com.example.kunuz.util.LangUtil;
import com.example.kunuz.util.RoleUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public CategoryDTO create(CategoryDTO dto, JwtDTO jwtDTO) {
        RoleUtil.checkRoleToAdmin(jwtDTO);


        CategoryEntity entity = getEntity(dto);
        entity.setCreatedDate(LocalDateTime.now());


        repository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public CategoryEntity getEntity(CategoryDTO dto) {
        CategoryEntity entity = new CategoryEntity();
        entity.setKey(dto.getKey());
        entity.setName_uz(dto.getName_uz());
        entity.setName_ru(dto.getName_ru());
        entity.setName_en(dto.getName_en());
        return entity;
    }

    public CategoryDTO update(Integer id, CategoryDTO dto, JwtDTO jwtDTO) {
        RoleUtil.checkRoleToAdmin(jwtDTO);
        Optional<CategoryEntity> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new ArticleTypeNotFoundException("article type not found");
        }


        CategoryEntity entity = getEntity(dto);
        entity.setId(optional.get().getId());
        entity.setCreatedDate(optional.get().getCreatedDate());


        repository.save(entity);
        dto.setId(entity.getId());

        return dto;
    }

    public Boolean deleteById(Integer id, JwtDTO jwtDTO) {
        RoleUtil.checkRoleToAdmin(jwtDTO);
        Optional<CategoryEntity> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new RegionNotFoundException("article type not found");
        }

        repository.deleteById(id);
        return true;

    }

    public Page<CategoryDTO> getList(Integer page, Integer size, JwtDTO jwtDTO) {
        RoleUtil.checkRoleToAdmin(jwtDTO);

        Pageable pageable = PageRequest.of(page, size);

        Page<CategoryEntity> pageObj = repository.findAll(pageable);

        List<CategoryEntity> content = pageObj.getContent();
        List<CategoryDTO> dtoList = new ArrayList<>();
        content.forEach(entity -> dtoList.add(getDTO(entity)));

        return new PageImpl<>(dtoList, pageable, pageObj.getTotalElements());
    }


    public CategoryDTO getDTO(CategoryEntity entity) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setKey(entity.getKey());
        dto.setName_uz(entity.getName_uz());
        dto.setName_ru(entity.getName_ru());
        dto.setName_en(entity.getName_en());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public List<CategoryShortDTO> getByLang(String lang, JwtDTO jwtDTO) {

        RoleUtil.checkRoleToAdmin(jwtDTO);
        List<ICategoryMapper> mapperList = new ArrayList<>();

        LangUtil.checkLang(lang);

        switch (lang) {
            case "en" -> mapperList = repository.getByLangEn();
            case "uz" -> mapperList = repository.getByLangUz();
            case "ru" -> mapperList = repository.getByLangRu();
        }

        return getDTOList(mapperList);
    }


    private List<CategoryShortDTO> getDTOList(List<ICategoryMapper> mapperList) {

        List<CategoryShortDTO> dtoList = new ArrayList<>();

        for (ICategoryMapper mapper : mapperList) {
            CategoryShortDTO dto = new CategoryShortDTO();
            dto.setId(mapper.getId());
            dto.setName(mapper.getName());
            dto.setKey(mapper.getAKey());
            dtoList.add(dto);

        }
        return dtoList;
    }


}

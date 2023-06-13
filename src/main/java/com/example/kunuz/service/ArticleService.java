package com.example.kunuz.service;

import com.example.kunuz.dto.article.ArticleCreateDTO;
import com.example.kunuz.dto.article.ArticleResponseDTO;
import com.example.kunuz.dto.article.ArticleShortInfoDTO;
import com.example.kunuz.entity.RegionEntity;
import com.example.kunuz.entity.article.ArticleEntity;
import com.example.kunuz.entity.article.ArticleTypeEntity;
import com.example.kunuz.enums.ArticleStatus;
import com.example.kunuz.enums.LikeStatus;
import com.example.kunuz.exp.ArticleNotFoundException;
import com.example.kunuz.exp.ArticleNotPublishedException;
import com.example.kunuz.mapper.ArticleShortInfoMapper;
import com.example.kunuz.mapper.IArticleShortInfoMapper;
import com.example.kunuz.repository.ArticleRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    private final ArticleRepository repository;

    private final ArticleTypeService articleTypeService;

    private final ProfileService profileService;

    private final RegionService regionService;
    private final AttachService attachService;

    public ArticleService(ArticleRepository repository, ArticleTypeService articleTypeService, ProfileService profileService, RegionService regionService, AttachService attachService) {
        this.repository = repository;
        this.articleTypeService = articleTypeService;
        this.profileService = profileService;
        this.regionService = regionService;
        this.attachService = attachService;
    }

    public ArticleResponseDTO create(ArticleCreateDTO dto, Integer profileId) {
        ArticleEntity entity = new ArticleEntity();


        entity.setModeratorId(profileId);
        entity.setRegionId(dto.getRegionId());
        entity.setArticleTypeId(dto.getArticleTypeId());
        entity.setImageId(dto.getImageId());

        entity.setDescription(dto.getDescription());
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setStatus(ArticleStatus.NOT_PUBLISHED);


        repository.save(entity);

        ArticleResponseDTO responseDTO = new ArticleResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setDescription(entity.getDescription());
        responseDTO.setContent(entity.getContent());
        responseDTO.setTitle(entity.getTitle());
        responseDTO.setImageId(entity.getImageId());
        return responseDTO;
    }


    public Boolean update(String id, ArticleCreateDTO dto, Integer profileId) {

        ArticleEntity entity = getById(id);


        entity.setArticleTypeId(dto.getArticleTypeId());
        entity.setRegionId(dto.getRegionId());
        entity.setModeratorId(profileId);


        entity.setDescription(dto.getDescription());
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setSharedCount(0);
        entity.setViewCount(0);
        entity.setVisible(true);

        entity.setStatus(ArticleStatus.NOT_PUBLISHED);

        repository.save(entity);

        return true;
    }

    public ArticleEntity getById(String id) {
        Optional<ArticleEntity> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new ArticleNotFoundException("Article Not Found");
        }
        return optional.get();
    }

    public boolean delete(String id) {
        getById(id);
        repository.deleteById(id);

        return true;
    }

    public boolean changeStatus(String id, Integer profileId) {
        ArticleEntity entity = getById(id);
        entity.setStatus(ArticleStatus.PUBLISHED);
        entity.setPublisherId(profileId);
        entity.setPublishedDate(LocalDateTime.now());

        repository.save(entity);

        return true;
    }

    public List<ArticleShortInfoDTO> getLast5(Integer id) {

        ArticleTypeEntity articleType = articleTypeService.getById(id);
        List<IArticleShortInfoMapper> iMapperList = repository.findTop5(articleType, ArticleStatus.PUBLISHED);

        List<ArticleShortInfoDTO> dtoList = new ArrayList<>();

        for (IArticleShortInfoMapper iMapper : iMapperList) {
            dtoList.add(getShortDTO(iMapper));
        }
        return dtoList;
    }

    private ArticleShortInfoDTO getShortDTO(IArticleShortInfoMapper mapper) {
        ArticleShortInfoDTO dto = new ArticleShortInfoDTO();
        dto.setTitle(mapper.getTitle());
        dto.setDescription(mapper.getDescription());
        dto.setPublishedDate(mapper.getPublishedDate());
        dto.setPublishedDate(mapper.getPublishedDate());
        dto.setImage(attachService.getById(mapper.getImageId()));
        return dto;
    }

    public List<ArticleShortInfoDTO> getLast3(Integer id) {

        ArticleTypeEntity articleType = articleTypeService.getById(id);

        List<IArticleShortInfoMapper> iMapperList = repository.findTop3(articleType, ArticleStatus.PUBLISHED);

        List<ArticleShortInfoDTO> dtoList = new ArrayList<>();

        for (IArticleShortInfoMapper iMapper : iMapperList) {
            dtoList.add(getShortDTO(iMapper));
        }

        return dtoList;
    }


    public List<ArticleShortInfoDTO> getLast8(List<String> idList) {

        List<IArticleShortInfoMapper> iMapperList = repository.getLast8Native(ArticleStatus.PUBLISHED, idList);

        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        iMapperList.forEach(iMapper -> dtoList.add(getShortDTO(iMapper)));

        return dtoList;
    }

    public ArticleResponseDTO getByIdAndLang(String id) {
        ArticleEntity entity = repository.findByIdAndStatus(id, ArticleStatus.PUBLISHED);
        if (entity == null) {
            throw new ArticleNotPublishedException("Article not published");
        }

        ArticleResponseDTO dto = new ArticleResponseDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());

        dto.setPublishedDate(entity.getPublishedDate());
        dto.setCreatedDate(entity.getCreatedDate());

        dto.setImageId(entity.getImageId());

        dto.setSharedCount(entity.getSharedCount());
        dto.setViewCount(entity.getViewCount());

        dto.setModeratorDTO(profileService.getFullDTO(entity.getModerator()));
        dto.setPublisherDTO(profileService.getFullDTO(entity.getPublisher()));
        dto.setArticleTypeDTO(articleTypeService.getFullDTO(entity.getArticleType()));
        dto.setRegionDTO(regionService.getFullDTO(entity.getRegion()));


        return dto;

    }

    public List<ArticleShortInfoDTO> getLast4ByType1(String id, Integer typeId) {
        List<IArticleShortInfoMapper> iMapperList = repository.getLast4ByType1(typeId, id, ArticleStatus.PUBLISHED);
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        iMapperList.forEach(iMapper -> dtoList.add(getShortDTO(iMapper)));
        return dtoList;
    }

    public List<ArticleShortInfoDTO> getTop4() {
        List<IArticleShortInfoMapper> iMapperList = repository.getTop4();
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        iMapperList.forEach(iMapper -> dtoList.add(getShortDTO(iMapper)));
        return dtoList;
    }

    public List<ArticleShortInfoDTO> getLast4ByType2(Integer typeId) {

        List<IArticleShortInfoMapper> iMapperList = repository.getLast4ByType2(typeId);
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        iMapperList.forEach(iMapper -> dtoList.add(getShortDTO(iMapper)));
        return dtoList;
    }


    public List<ArticleShortInfoDTO> getLast5ByTypeAndRegion(Integer typeId, String regionKey) {

        RegionEntity region = regionService.getByKey(regionKey);

        List<IArticleShortInfoMapper> iMapperList = repository.getLast5ByTypeAndRegionKey(typeId, region.getId());
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        iMapperList.forEach(iMapper -> dtoList.add(getShortDTO(iMapper)));
        return dtoList;
    }

    public Page<ArticleShortInfoDTO> getListByType(String regionKey, Integer page, Integer size) {
        RegionEntity region = regionService.getByKey(regionKey);

        Sort sort = Sort.by(Sort.Direction.DESC, "publishedDate");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ArticleShortInfoMapper> mapperList = repository.getListByTypeWithPage(region.getId(), pageable);

        List<ArticleShortInfoDTO> dtoList = new ArrayList<>();
        for (ArticleShortInfoMapper mapper : mapperList) {
            ArticleShortInfoDTO dto = new ArticleShortInfoDTO();
            dto.setTitle(mapper.getTitle());
            dto.setDescription(mapper.getDescription());
            dto.setImage(mapper.getImageId());
            dto.setPublishedDate(mapper.getPublishedDate());
            dtoList.add(dto);
        }
        return new PageImpl<>(dtoList, pageable, mapperList.getTotalElements());
    }


    public void like(String id) {
        ArticleEntity article = getById(id);
        article.setLikeCount(article.getLikeCount() + 1);
        repository.save(article);
    }

    public void dislike(String id) {
        ArticleEntity article = getById(id);
        article.setDislikeCount(article.getDislikeCount() + 1);
        repository.save(article);
    }

    public void remove(String id, LikeStatus status) {
        ArticleEntity entity = getById(id);

        if (status.equals(LikeStatus.LIKE)) {
            entity.setLikeCount(entity.getLikeCount() - 1);
        } else {
            entity.setDislikeCount(entity.getDislikeCount() - 1);
        }

        repository.save(entity);

    }

    public Boolean increaseViewCount(String id) {
        ArticleEntity entity = getById(id);
        entity.setViewCount(entity.getViewCount() + 1);

        repository.save(entity);
        return true;

    }

    public Boolean increaseShareCount(String id) {
        ArticleEntity entity = getById(id);
        entity.setSharedCount(entity.getSharedCount() + 1);
        repository.save(entity);
        return true;
    }

}

package com.example.kunuz.repository;

import com.example.kunuz.entity.CategoryEntity;
import com.example.kunuz.mapper.ICategoryMapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer>,
        PagingAndSortingRepository<CategoryEntity, Integer> {

    @Query(value = "SELECT a.id as id, a.name_uz as name, a.key as aKey from region a", nativeQuery = true)
    List<ICategoryMapper> getByLangUz();

    @Query(value = "SELECT a.id as id, a.name_ru as name, a.key as key from region a", nativeQuery = true)
    List<ICategoryMapper> getByLangRu();

    @Query(value = "SELECT a.id as id, a.name_en as name, a.key as key from region a", nativeQuery = true)
    List<ICategoryMapper> getByLangEn();
}

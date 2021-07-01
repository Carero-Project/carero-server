package com.carero.repository;

import com.carero.domain.cat.SubCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SubCatRepository {


    private final EntityManager em;

    public SubCategory findById(Long id){
        return em.find(SubCategory.class, id);
    }

    public SubCategory findByName(String name){
        // name을 "기타"로 두었을 경우 오류 발생 ( getSingleResult 로 두었으므로 )
        Object subCat = em.createQuery("select s from SubCategory s where s.subCategoryName = :name")
                .setParameter("name", name)
                .getSingleResult();

        return (SubCategory)subCat;
    }

}

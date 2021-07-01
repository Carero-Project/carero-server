package com.carero.service;

import com.carero.domain.cat.SubCategory;
import com.carero.repository.SubCatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubCatService {

    private final SubCatRepository subCatRepository;

    public SubCategory findOne(Long subCatId){
        return subCatRepository.findById(subCatId);
    }

}

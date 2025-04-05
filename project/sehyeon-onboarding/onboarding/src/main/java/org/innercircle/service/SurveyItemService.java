package org.innercircle.service;

import org.innercircle.entity.ItemOption;
import org.innercircle.entity.SurveyItem;
import org.innercircle.repository.SurveyItemJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SurveyItemService {

    @Autowired
    SurveyItemJpaRepository surveyItemRepository;

    public Long saveSurveyItem(SurveyItem surveyItem) {
        surveyItemRepository.save(surveyItem);
        return surveyItem.getSeq();
    }


    public SurveyItem findServeyItem(Long seq) {
        SurveyItem surveyItem = surveyItemRepository.findOne(seq);
        return surveyItem;
    }

}

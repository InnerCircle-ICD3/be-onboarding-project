package org.innercircle.service;


import org.innercircle.entity.AnswerItem;
import org.innercircle.repository.AnswerItemSpringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnswerItemService {

    @Autowired
    AnswerItemSpringRepository answerItemRepository;

    public Long saveAnswerItem(AnswerItem answerItem) {
        answerItemRepository.save(answerItem);
        return answerItem.getSeq();
    }

    public AnswerItem findAnswerItem(Long seq) {
        Optional<AnswerItem> optional = answerItemRepository.findById(seq);
        return optional.get();
    }
}

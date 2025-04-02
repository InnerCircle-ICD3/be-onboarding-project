package org.innercircle.service;

import org.innercircle.entity.ItemOption;
import org.innercircle.repository.ItemOptionJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemOptionService {

    @Autowired
    ItemOptionJpaRepository itemOptionRepository;

    public Long saveOption(ItemOption itemOption) {
        itemOptionRepository.save(itemOption);
        return itemOption.getSeq();
    }

    public ItemOption findItemOption(Long seq) {
        ItemOption itemOption = itemOptionRepository.findOne(seq);
        return itemOption;
    }

}

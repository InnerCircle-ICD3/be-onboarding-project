package org.innercircle.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.innercircle.entity.ItemOption;
import org.springframework.stereotype.Repository;

@Repository
public class ItemOptionJpaRepository {

    @PersistenceContext
    EntityManager entityManager;

    public void save(ItemOption itemOption) {
        entityManager.persist(itemOption);
    }


    public ItemOption findOne(Long seq) {
        ItemOption itemOption = entityManager.find(ItemOption.class, seq);
        return itemOption;
    }

}

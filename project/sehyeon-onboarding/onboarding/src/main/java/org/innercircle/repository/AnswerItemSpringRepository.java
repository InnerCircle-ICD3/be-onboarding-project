package org.innercircle.repository;

import org.innercircle.entity.AnswerItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerItemSpringRepository extends JpaRepository<AnswerItem, Long> {
}

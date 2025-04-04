package com.chosseang.seonghunonboarding.repository

import com.chosseang.seonghunonboarding.entity.Answer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AnswerRepository : JpaRepository<Answer, Long> {
}

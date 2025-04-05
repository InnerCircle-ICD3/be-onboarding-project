package com.example.repository

import com.example.entity.SurveyItemBase
import org.springframework.data.jpa.repository.JpaRepository

interface SurveyItemRepository : JpaRepository<SurveyItemBase, Long>

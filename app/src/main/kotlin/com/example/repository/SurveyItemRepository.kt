package com.example.repository

import com.example.entity.*
import org.springframework.data.jpa.repository.JpaRepository

interface SurveyItemRepository : JpaRepository<SurveyItem, Long>

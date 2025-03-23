package com.example.repository

import com.example.entity.*
import com.springframework.data.jpa.repository.JpaRepository

interface SurveyRepository : JpaRepository<Survey, Long>
interface SurveyItemRepository : JpaRepository<SurveyItem, Long>
interface SelectionOptionRepository : JpaRepository<SelectionOption, Long>

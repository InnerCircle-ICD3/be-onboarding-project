package com.example.repository

import com.example.entity.SelectionOption
import org.springframework.data.jpa.repository.JpaRepository

interface SelectionOptionRepository : JpaRepository<SelectionOption, Long>

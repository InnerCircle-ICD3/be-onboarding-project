package com.example.repository

import com.example.entity.*
import org.springframework.data.jpa.repository.JpaRepository

interface SelectionOptionRepository : JpaRepository<SelectionOption, Long>

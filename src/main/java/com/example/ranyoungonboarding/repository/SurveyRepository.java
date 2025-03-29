package com.example.ranyoungonboarding.repository;


import com.example.ranyoungonboarding.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {

    //기본 CRUD는 JpaRepository에서 제공
}

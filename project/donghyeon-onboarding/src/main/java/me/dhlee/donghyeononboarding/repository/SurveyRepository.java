package me.dhlee.donghyeononboarding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import me.dhlee.donghyeononboarding.domain.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

}

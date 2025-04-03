package com.survey.application.repository;

import com.survey.domain.survey.ChoiceInputForm;
import com.survey.domain.survey.InputForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaChoiceInputFormRepository extends JpaRepository<ChoiceInputForm, Long> {

    @Query("SELECT c FROM ChoiceInputForm c " +
            "LEFT JOIN FETCH c.inputOptions " +
            "WHERE c.inputForm IN :inputForms")
    List<ChoiceInputForm> findByInputFormsFetchJoin(@Param("inputForms") List<InputForm> inputForms);
}

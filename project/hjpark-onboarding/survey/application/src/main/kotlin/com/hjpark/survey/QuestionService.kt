//package com.hjpark.survey
//
//import com.hjpark.survey.entity.Question
//import com.hjpark.survey.entity.QuestionOption
//import com.hjpark.survey.repository.QuestionOptionRepository
//import com.hjpark.survey.repository.QuestionRepository
//import org.springframework.stereotype.Service
//import org.springframework.transaction.annotation.Transactional
//
//@Service
//@Transactional(readOnly = true)
//class QuestionService(
//    private val questionRepository: QuestionRepository,
//    private val questionOptionRepository: QuestionOptionRepository
//) {
//    fun findQuestions(surveyId: Long): List<Question> {
//        return questionRepository.findAllBySurveyIdOrderBySequence(surveyId)
//    }
//
//    fun findQuestion(id: Long): Question {
//        return questionRepository.findById(id)
//            .orElseThrow { NoSuchElementException("질문을 찾을 수 없습니다: $id") }
//    }
//
//    fun findQuestionOptions(questionId: Long): List<QuestionOption> {
//        return questionOptionRepository.findAllByQuestionIdOrderBySequence(questionId)
//    }
//
//    @Transactional
//    fun createQuestion(option: Question): Any {
//        return questionRepository.save(option)
//    }
//
//    @Transactional
//    fun createQuestionOption(option: QuestionOption): Any {
//        return questionOptionRepository.save(option)
//    }
//
//    @Transactional
//    fun deleteQuestionOption(id: Long) {
//        questionOptionRepository.deleteById(id)
//    }
//}
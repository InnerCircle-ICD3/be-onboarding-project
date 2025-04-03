package org.innercircle.controller;


import org.innercircle.entity.Survey;
import org.innercircle.entity.Answer;
import org.innercircle.service.AnswerService;
import org.innercircle.service.SurveyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SurveyController {

    @Autowired
    SurveyService surveyService;

    @Autowired
    AnswerService answerService;

    Logger logger = LoggerFactory.getLogger(SurveyController.class);

    @RequestMapping(value = "registsurvey", method = RequestMethod.POST)
    @ResponseBody
    public Long registSurvey(@RequestBody Survey survey) {
        logger.info(survey.toString());
        Long seqSurvey = surveyService.saveSurvey(survey);
        return seqSurvey;
    }

    @RequestMapping(value = "registanswer", method = RequestMethod.POST)
    @ResponseBody
    public Long registAnswer(@RequestBody Answer answer) {
        logger.info(answer.toString());
        Long seqAnswer = answerService.saveAnswer(answer);
        return seqAnswer;
    }
}

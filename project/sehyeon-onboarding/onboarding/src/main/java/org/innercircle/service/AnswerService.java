package org.innercircle.service;

import org.innercircle.entity.Answer;
import org.innercircle.repository.AnswerSpringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AnswerService {

    @Autowired
    AnswerSpringRepository answerRepository;

    public Long saveAnswer(Answer answer) {
        answerRepository.save(answer);
        return answer.getSeq();
    }

    public Answer findOne(Long seq) {
        Optional<Answer> optional = answerRepository.findById(seq);
        Answer answer = optional.get();
        return answer;
    }
}

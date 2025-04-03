package org.innercircle.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
@Entity
@TableGenerator(name = "MY_SEQ_GEN", table = "MY_SEQ_TB", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_VAL", pkColumnValue = "SURVEY_ANSWER_SEQ", allocationSize = 5)
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MY_SEQ_GEN")
    @Column(name = "seq_answer")
    Long seq;


    @ManyToOne(fetch = FetchType.LAZY)
    @Setter(AccessLevel.NONE)
    Survey survey;

    @OneToMany(mappedBy = "surveyAnswer", cascade = CascadeType.ALL)
    @Setter(AccessLevel.NONE)
    List<AnswerItem> answerItemList;


    public void setSurvey(Survey survey) {
        if(this.survey == survey) {
            return;
        }
        this.survey = survey;
    }

    public void setAnswerItemList(List<AnswerItem> answerItemList) {
        if(this.answerItemList == answerItemList) {
            return;
        }
        if(answerItemList != null && answerItemList.size() > 0) {
                for(AnswerItem answerItem : answerItemList) {
                    answerItem.setAnswer(this);
                }
                this.answerItemList = answerItemList;
        }
    }

    @Override
    public String toString() {
        return "SurveyAnswer{" +
                "seq=" + seq +
                ", survey=" + survey +
                ", answerItemList=" + answerItemList +
                '}';
    }
}

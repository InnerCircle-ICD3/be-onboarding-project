package org.innercircle.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Entity
@TableGenerator(name = "MY_SEQ_GEN", table = "MY_SEQ_TB", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_VAL", pkColumnValue = "ANSWER_ITEM_SEQ", allocationSize = 5)
public class AnswerItem {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MY_SEQ_GEN")
    @Column(name = "seq_answ_item")
    Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seq_answer")
    @Setter(AccessLevel.NONE)
    Answer answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seq_item")
    @Setter(AccessLevel.NONE)
    SurveyItem surveyItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seq_option")
    @Setter(AccessLevel.NONE)
    ItemOption itemOption;

    String answVal;

    public void setAnswer(Answer answer) {
        if(this.answer == answer) {
            return;
        }
        this.answer = answer;
    }

    public void setSurveyItem(SurveyItem surveyItem) {
        if(this.surveyItem == surveyItem) {
            return;
        }
        this.surveyItem = surveyItem;
    }

    public void setItemOption(ItemOption itemOption) {
        if(this.itemOption == itemOption) {
            return;
        }
        this.itemOption = itemOption;
    }


    @Override
    public String toString() {
        return "AnswerItem{" +
                "seq=" + seq +
                ", surveyItem=" + surveyItem +
                ", itemOption=" + itemOption +
                ", answVal='" + answVal + '\'' +
                '}';
    }
}

package org.innercircle.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Entity
@TableGenerator(name = "MY_SEQ_GEN", table = "MY_SEQ_TB", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_VAL", pkColumnValue = "OPTION_SEQ", allocationSize = 20)
public class ItemOption {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MY_SEQ_GEN")
    @Column(name = "seq_option")
    private Long seq;
    private String content;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seq_item")
    @Setter(AccessLevel.NONE)
    SurveyItem surveyItem;



    public void loadSurveyItem(SurveyItem surveyItem) {
        if(this.surveyItem == surveyItem) {
            return;
        }
        this.surveyItem = surveyItem;
    }


    @Override
    public String toString() {
        return "ItemOption{" +
                "seq=" + seq +
                ", content='" + content + '\'' +
                ", surveyItem=" + surveyItem.hashCode() +
                '}';
    }
}

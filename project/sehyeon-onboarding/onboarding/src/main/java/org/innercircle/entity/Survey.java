package org.innercircle.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@TableGenerator(name = "MY_SEQ_GEN", table = "MY_SEQ_TB", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_VAL", pkColumnValue = "SURVEY_SEQ", allocationSize = 5)
public class Survey {

// @NoArgsConstructor 와 @Builder 어노테이션은 동시에 쓸 수 없음. 서로 다른 생성자 규약을 적용해야 해서
// JPA 쓰려면 Entity 에 기본 생성자가 있어야 함. 그래서 @NoArgsConstructor를 붙이는 게 좋음. entity 생성해서 넣거나 data 불러와서 entitty 생성하거나 할 때 기본 생성자 쓰는 듯
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MY_SEQ_GEN")
    @Column(name = "seq_survey")
    private Long seq;
    private String title;
    private String desc;

    @OneToMany(mappedBy = "survey")
    List<SurveyItem> surveyItemList;


    public void loadSurveyItemList(List<SurveyItem> surveyItemList) {
        if(this.surveyItemList == surveyItemList) {
            return;
        }
        this.surveyItemList = surveyItemList;
        for(SurveyItem surveyItem : surveyItemList) {
            if(surveyItem.getSurvey() != this) {
                surveyItem.loadSurvey(this);
            }
        }
    }

    @Override
    public String toString() {
        return "Survey{" +
                "seq=" + seq +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", surveyItemList=" + surveyItemList.hashCode() +
                '}';
    }
}

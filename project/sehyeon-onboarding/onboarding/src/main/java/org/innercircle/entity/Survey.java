package org.innercircle.entity;


import jakarta.persistence.*;
import lombok.*;

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

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
    @Setter(AccessLevel.NONE)
// @Setter 어노테이션에 (AccessLevel.NONE) 속성을 넣음으로써 애플리케이션 안에서 호출 못하도록 막으려고 했음.
// 왜냐하면 loadSurveyItemList() 를 사용하도록 강제하고 싶어서.
// 근데 생각해 보니까 controller 에서 setter 써야 함. 심지어 setter 를 호출할 때 참조 entity 쪽에도 연관관계룰 맵핑해 줘야 함
// 결국은 setter 를 loadSurveyItemList 처럼 만드는 수밖에
    List<SurveyItem> surveyItemList;

    public void setSurveyItemList(List<SurveyItem> surveyItemList) {
        if(this.surveyItemList == surveyItemList) {
            return;
        }
        this.surveyItemList = surveyItemList;
        for(SurveyItem surveyItem : surveyItemList) {
            if(surveyItem.getSurvey() != this) {
                // surveyItem의 survey가 내가 아니면 그 survey 찾아가서 surveyItemList 로부터 이 surveyItem을 지워주고 나와야 하나
                surveyItem.setSurvey(this);
            }
        }
    }

//    public void loadSurveyItemList(List<SurveyItem> surveyItemList) {
//        if(this.surveyItemList == surveyItemList) {
//            return;
//        }
//        this.surveyItemList = surveyItemList;
//        for(SurveyItem surveyItem : surveyItemList) {
//            if(surveyItem.getSurvey() != this) {
//                // surveyItem의 survey가 내가 아니면 그 survey 찾아가서 surveyItemList 로부터 이 surveyItem을 지워주고 나와야 하나
//                surveyItem.loadSurvey(this);
//            }
//        }
//    }

    @Override
    public String toString() {
        return "Survey{" +
                "seq=" + seq +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", surveyItemList=" + surveyItemList +
                '}';
    }
}

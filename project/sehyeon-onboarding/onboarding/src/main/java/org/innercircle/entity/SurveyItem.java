package org.innercircle.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Data
@Entity
@TableGenerator(name = "MY_SEQ_GEN", table = "MY_SEQ_TB", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_VAL", pkColumnValue = "ITEM_SEQ", allocationSize = 10)
public class SurveyItem {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MY_SEQ_GEN")
    @Column(name="seq_item")
    private Long seq;
    private String title;
    private String desc;
    @Column(length = 1)
    private String isRequiredYN;
    @Enumerated(EnumType.STRING)
    private ItemType itemType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="seq_survey")
    @Setter(AccessLevel.NONE)
    Survey survey;
    @OneToMany(mappedBy = "surveyItem", cascade = CascadeType.ALL)
    @Setter(AccessLevel.NONE)
    List<ItemOption> itemOptionList;


    public void setSurvey(Survey survey) {
        if(this.survey == survey) {
            return;
        }
        this.survey = survey;
    }

    public void setItemOptionList(List<ItemOption> itemOptionList){
        if(this.itemOptionList == itemOptionList) {
            return;
        }
        this.itemOptionList = itemOptionList;
        for(ItemOption itemOption : itemOptionList) {
            if(itemOption.getSurveyItem() != this) {
                //
                itemOption.setSurveyItem(this);
            }
        }
    }

    @Override
    public String toString() {
        return "SurveyItem{" +
                "seq=" + seq +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", isRequiredYN='" + isRequiredYN + '\'' +
                ", itemType=" + itemType +
                ", itemOptionList=" + itemOptionList +
                '}';
    }
}

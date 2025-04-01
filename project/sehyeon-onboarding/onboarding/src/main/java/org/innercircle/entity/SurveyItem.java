package org.innercircle.entity;

import jakarta.persistence.*;

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

    @ManyToOne
    @JoinColumn(name="seq_survey")
    Survey survey;

    @ManyToOne
    @JoinColumn(name="seq_type")
    ItemType itemType;
}

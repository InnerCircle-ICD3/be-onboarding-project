package org.innercircle.entity;

import jakarta.persistence.*;

@Entity
@TableGenerator(name = "MY_SEQ_GEN", table = "MY_SEQ_TB", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_VAL", pkColumnValue = "OPTION_SEQ", allocationSize = 20)
public class ItemOption {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MY_SEQ_GEN")
    @Column(name = "seq_option")
    private Long seq;
    private String content;



    @ManyToOne
    @JoinColumn(name = "seq_item")
    SurveyItem surveyItem;

    @ManyToOne
    @JoinColumn(name = "seq_type")
    ItemType itemType;




}

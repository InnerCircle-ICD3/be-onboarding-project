package org.innercircle.entity;


import jakarta.persistence.*;

@Entity
@TableGenerator(name = "MY_SEQ_GEN", table = "MY_SEQ_TB", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_VAL", pkColumnValue = "SURVEY_SEQ", allocationSize = 5)
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MY_SEQ_GEN")
    @Column(name = "seq_survey")
    private Long seq;
    private String title;
    private String desc;

}

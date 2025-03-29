package org.innercircle.entity;

import jakarta.persistence.*;


@Entity
@TableGenerator(name = "MY_SEQ_GEN", table = "MY_SEQ_TB", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_VAL", pkColumnValue = "TYPE_SEQ", allocationSize = 5)
public class ItemType {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MY_SEQ_GEN")
    @Column(name="seq_type")
    private Long id;
    private String desc;

}

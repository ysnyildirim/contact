package com.yil.contact.model;


import com.yil.contact.base.IEntity;
import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "PHONE_TYPE",
        schema = "CNT")
public class PhoneType implements IEntity {
    @Id
    @SequenceGenerator(name = "PHONE_TYPE_SEQUENCE_GENERATOR",
            sequenceName = "SEQ_PHONE_TYPE_ID",
            schema = "CNT")
    @GeneratedValue(generator = "PHONE_TYPE_SEQUENCE_GENERATOR")
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;
}


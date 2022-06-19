package com.yil.contact.model;


import com.yil.contact.base.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode
@Entity
@Data
@Table(name = "PHONE_TYPE")
public class PhoneType extends AbstractEntity {
    @Id
    @SequenceGenerator(name = "PHONE_TYPE_SEQUENCE_GENERATOR",
            sequenceName = "SEQ_PHONE_TYPE_ID",
            allocationSize = 1)
    @GeneratedValue(generator = "PHONE_TYPE_SEQUENCE_GENERATOR")
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;
}


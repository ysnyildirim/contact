package com.yil.contact.model;

import com.yil.contact.base.IEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode
@Entity
@Data
@Table(name = "CONTACT_TYPE",
        schema = "CNT")
public class ContactType implements IEntity {
    @Id
    @SequenceGenerator(name = "CONTACT_TYPE_SEQUENCE_GENERATOR",
            sequenceName = "SEQ_CONTACT_TYPE_ID",
            schema = "CNT")
    @GeneratedValue(generator = "CONTACT_TYPE_SEQUENCE_GENERATOR")
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;
}

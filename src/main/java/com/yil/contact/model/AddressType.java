package com.yil.contact.model;

import com.yil.contact.base.IEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode
@Entity
@Data
@Table(name = "ADDRESS_TYPE",
        schema = "CNT")
public class AddressType implements IEntity {
    @Id
    @SequenceGenerator(name = "ADDRESS_TYPE_SEQUENCE_GENERATOR",
            sequenceName = "SEQ_ADDRESS_TYPE_ID",
            schema = "CNT")
    @GeneratedValue(generator = "ADDRESS_TYPE_SEQUENCE_GENERATOR")
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;
}

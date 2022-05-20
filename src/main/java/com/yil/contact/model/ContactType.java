package com.yil.contact.model;


import com.yil.contact.base.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode
@Entity
@Data
@Table(name = "ContactType")
public class ContactType extends AbstractEntity {
    @Id
    @SequenceGenerator(name = "ContactType_Sequence_Generator",
            sequenceName = "Seq_ContactType",
            allocationSize = 1)
    @GeneratedValue(generator = "ContactType_Sequence_Generator")
    @Column(name = "Id")
    private Long id;
    @Column(name = "Name", nullable = false, length = 100)
    private String name;
}


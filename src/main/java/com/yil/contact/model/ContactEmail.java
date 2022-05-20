package com.yil.contact.model;

import com.yil.contact.base.AbstractEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "ContactEmail")
public class ContactEmail extends AbstractEntity {
    @Id
    @SequenceGenerator(name = "ContactEmail_Sequence_Generator",
            sequenceName = "Seq_ContactEmail",
            allocationSize = 1)
    @GeneratedValue(generator = "ContactEmail_Sequence_Generator")
    @Column(name = "Id")
    private Long id;
    @Column(name = "ContactId", nullable = false)
    private Long contactId;
    @Column(name = "Address", nullable = false)
    private String address;
    @Column(name = "EmailTypeId", nullable = false)
    private Long emailTypeId;

}
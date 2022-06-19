package com.yil.contact.model;

import com.yil.contact.base.AbstractEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "CONTACT_EMAIL")
public class ContactEmail extends AbstractEntity {
    @Id
    @SequenceGenerator(name = "CONTACT_EMAIL_SEQUENCE_GENERATOR",
            sequenceName = "SEQ_CONTACT_EMAIL_ID",
            allocationSize = 1)
    @GeneratedValue(generator = "CONTACT_EMAIL_SEQUENCE_GENERATOR")
    @Column(name = "ID")
    private Long id;
    @Column(name = "CONTACT_ID", nullable = false)
    private Long contactId;
    @Column(name = "EMAIL", nullable = false)
    private String email;

}
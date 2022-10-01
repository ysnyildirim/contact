package com.yil.contact.model;

import com.yil.contact.base.IEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "CONTACT_EMAIL",
        schema = "CNT")
public class ContactEmail implements IEntity {
    @Id
    @SequenceGenerator(name = "CONTACT_EMAIL_SEQUENCE_GENERATOR",
            sequenceName = "SEQ_CONTACT_EMAIL_ID",
            schema = "CNT")
    @GeneratedValue(generator = "CONTACT_EMAIL_SEQUENCE_GENERATOR")
    @Column(name = "ID")
    private Long id;
    @Column(name = "CONTACT_ID", nullable = false)
    private Long contactId;
    @Column(name = "EMAIL", nullable = false)
    private String email;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_TIME")
    private Date createdTime;
    @Column(name = "CREATED_USER_ID")
    private Long createdUserId;
}
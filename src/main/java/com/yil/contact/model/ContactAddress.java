package com.yil.contact.model;

import com.yil.contact.base.IEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "CONTACT_ADDRESS",
        schema = "CNT")
public class ContactAddress implements IEntity {
    @Id
    @SequenceGenerator(name = "CONTACT_ADDRESS_SEQUENCE_GENERATOR",
            sequenceName = "SEQ_CONTACT_ADDRESS_ID",
            schema = "CNT")
    @GeneratedValue(generator = "CONTACT_ADDRESS_SEQUENCE_GENERATOR")
    @Column(name = "ID")
    private Long id;
    @Column(name = "CONTACT_ID", nullable = false)
    private Long contactId;
    @Column(name = "ADDRESS_TYPE_ID", nullable = false)
    private Long addressTypeId;
    @Column(name = "REGION_ID")
    private Long regionId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_TIME")
    private Date createdTime;
    @Column(name = "CREATED_USER_ID")
    private Long createdUserId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_MODIFY_DATE")
    private Date lastModifyDate;
    @Column(name = "LAST_MODIFY_USER_ID")
    private Long lastModifyUserId;
}

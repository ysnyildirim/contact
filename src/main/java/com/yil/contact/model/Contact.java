package com.yil.contact.model;

import com.yil.contact.base.AbstractEntity;
import com.yil.contact.base.IEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CONTACT")
public class Contact implements IEntity {
    @Id
    @SequenceGenerator(name = "CONTACT_SEQUENCE_GENERATOR",
            sequenceName = "SEQ_CONTACT_ID",
            allocationSize = 1)
    @GeneratedValue(generator = "CONTACT_SEQUENCE_GENERATOR")
    @Column(name = "ID")
    private Long id;
    @Column(name = "CONTACT_TYPE_ID", nullable = false)
    private Long contactTypeId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_TIME")
    private Date createdTime;
    @Column(name = "CREATED_USER_ID")
    private Long createdUserId;

}

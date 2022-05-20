package com.yil.contact.model;

import com.yil.contact.base.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Contact")
public class Contact extends AbstractEntity {
    @Id
    @SequenceGenerator(name = "Contact_Sequence_Generator",
            sequenceName = "Seq_Contact",
            allocationSize = 1)
    @GeneratedValue(generator = "Contact_Sequence_Generator")
    @Column(name = "Id")
    private Long id;
    @Column(name = "ContactTypeId", nullable = false)
    private Long contactTypeId;

}

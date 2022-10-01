package com.yil.contact.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactPhoneDto implements Serializable {
    private Long id;
    private Long contactId;
    private Long number;
    private Long phoneTypeId;
}

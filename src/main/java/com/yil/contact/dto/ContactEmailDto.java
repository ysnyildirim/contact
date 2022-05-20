package com.yil.contact.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactEmailDto {
    private Long id;
    private Long contactId;
    private String address;
    private Long emailTypeId;
}

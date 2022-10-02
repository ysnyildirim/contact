/*
 * Copyright (c) 2022. Tüm hakları Yasin Yıldırım'a aittir.
 */
package com.yil.contact.base;

import lombok.Getter;

@Getter
public enum ErrorCode {
    ContactNotFound(6000006, "Contact not found"),
    PhoneTypeNotFound(6000005, "Phone type not found"),
    ContactAddressNotFound(6000004, "Contact address not found"),
    AddressTypeNotFound(6000003, "Address type not found"),
    ContactPhoneNotFound(6000002, "Contact phone not found"),
    ContactEmailNotFound(6000001, "Contact email not found"),
    ContactTypeNotFound(6000000, "Contact type not found");
    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

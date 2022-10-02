/*
 * Copyright (c) 2022. Tüm hakları Yasin Yıldırım'a aittir.
 */
package com.yil.contact.exception;

import com.yil.contact.base.ApiException;
import com.yil.contact.base.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@ApiException(code = ErrorCode.ContactEmailNotFound)
public class ContactEmailNotFoundException extends Exception {
}

package com.softserve.sprint15.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Page with such address does not exist")
public class MappingNotFoundException extends RuntimeException {

    public MappingNotFoundException(Long id) {
        super("PageNotFoundException with id="+id);
    }
}

package com.softserve.sprint15.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler(value = {EntityNotFoundByIdException.class, EntityNotFoundException.class,
            org.hibernate.boot.MappingNotFoundException.class, MappingNotFoundException.class})
    public String handlePageNotFoundException() {
        logger.error("MappingNotFoundException handler executed");
        return "error-404";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleServerSideException(HttpServletRequest request) {
        logger.info("Server Side Error Occured:: URL=" + request.getRequestURL());
        return "error-500";
    }
}
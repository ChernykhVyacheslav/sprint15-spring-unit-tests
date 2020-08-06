package com.softserve.sprint15.exception;

import org.hibernate.boot.MappingNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler(value = {EntityNotFoundByIdException.class, EntityNotFoundException.class,
            MappingNotFoundException.class, CustomMappingNotFoundException.class})
    public ModelAndView handlePageNotFoundException(Exception exception) {
        logger.error("MappingNotFoundException handler executed");
        ModelAndView modelAndView = new ModelAndView("error-404", HttpStatus.NOT_FOUND);
        modelAndView.addObject("info", exception.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleServerSideException(Exception exception) {
        logger.error("RuntimeException handler executed");
        ModelAndView modelAndView = new ModelAndView("error-500", HttpStatus.INTERNAL_SERVER_ERROR);
        modelAndView.addObject("info", exception.getMessage());
        return modelAndView;
    }
}
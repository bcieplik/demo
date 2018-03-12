package com.bart.demo.config.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandler {

    private static final String NOT_FOUND_MESSAGE = "Resource not found";
    private static final String BAD_REQUEST_MESSAGE = "Bad request";

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse notFound(NotFoundException exception) {
        return new ErrorResponse(NOT_FOUND_MESSAGE, 404);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse badRequest(BadRequestException exception) {
        return new ErrorResponse(BAD_REQUEST_MESSAGE, 400);
    }
}

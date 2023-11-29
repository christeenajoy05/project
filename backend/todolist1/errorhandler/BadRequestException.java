package com.backend.todolist1.errorhandler;
public class BadRequestException extends RuntimeException {
	private static final long serialVersionUID = 1L;

    public BadRequestException(String message) {
        super(message);
    }
}

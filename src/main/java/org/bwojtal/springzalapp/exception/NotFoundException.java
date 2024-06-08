package org.bwojtal.springzalapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = -8778474686473470171L;

    public NotFoundException() {
        super("Entity not found");
    }

    public NotFoundException(String msg) {
        super(msg);
    }
}

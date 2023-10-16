package com.educandoweb.course.services.exceptions;

public class ResourceNotFountException extends RuntimeException{
    
    public ResourceNotFountException(Object id) {
        super("Resource not found. ID: "+id);
    }
}

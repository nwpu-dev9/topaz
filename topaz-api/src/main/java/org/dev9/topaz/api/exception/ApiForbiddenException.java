package org.dev9.topaz.api.exception;

public class ApiForbiddenException extends RuntimeException{
    public ApiForbiddenException(){
        super();
    }

    public ApiForbiddenException(String message){
        super(message);
    }
}

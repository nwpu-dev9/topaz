package org.dev9.topaz.api.exception;

public class ApiForbiddenException extends Exception{
    public ApiForbiddenException(){
        super();
    }

    public ApiForbiddenException(String message){
        super(message);
    }
}

package org.dev9.topaz.api.exception;

public class ApiUnauthorizedException extends RuntimeException{
    public ApiUnauthorizedException(){
        super();
    }

    public ApiUnauthorizedException(String message){
        super(message);
    }
}

package org.dev9.topaz.api.exception;

public class ApiUnauthorizedException extends Exception{
    public ApiUnauthorizedException(){
        super();
    }

    public ApiUnauthorizedException(String message){
        super(message);
    }
}

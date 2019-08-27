package org.dev9.topaz.common.exception;

public class UnauthorizedException extends Exception{
    public UnauthorizedException(){
        super();
    }

    public UnauthorizedException(String message){
        super(message);
    }
}

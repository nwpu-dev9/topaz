package org.dev9.topaz.api.exception;

public class ApiNotFoundException extends Exception{
    public ApiNotFoundException(){
        super();
    }

    public ApiNotFoundException(String message){
        super(message);
    }
}

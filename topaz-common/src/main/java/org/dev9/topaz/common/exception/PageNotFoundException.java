package org.dev9.topaz.common.exception;

import org.springframework.data.domain.PageRequest;

public class PageNotFoundException extends RuntimeException{
    public PageNotFoundException(){
        super();
    }

    public PageNotFoundException(String message){
        super(message);
    }
}

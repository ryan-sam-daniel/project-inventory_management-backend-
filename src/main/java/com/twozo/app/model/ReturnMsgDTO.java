package com.twozo.app.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class ReturnMsgDTO {
    private final Collection<String> msg;

    public ReturnMsgDTO(){
        this.msg = new ArrayList<>();
    }

    public Collection<String> getMsg() {
        return msg;
    }

    public void setMsg(final String message) {
        this.msg.add(message);
    }

    public boolean hasMsg(){
        return !this.msg.isEmpty();
    }
}

package com.kk.controller.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageAgreement {
    private Boolean flag;
    private Object data;
    private String message;

    public MessageAgreement(Boolean flag) {
        this.flag = flag;
    }

    public MessageAgreement(Boolean flag, Object data) {
        this.flag = flag;
        this.data = data;
    }

    public MessageAgreement(String message) {
        this.flag = false;
        this.message = message;
    }
}

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

    public MessageAgreement(Boolean flag) {
        this.flag = flag;
    }
}

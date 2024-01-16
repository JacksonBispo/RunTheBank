package com.runthebank.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StandardError {

    private static final long serialVersionUID = 1L;
    private Integer status;
    private String msg;
    private Long timaStamp;

}

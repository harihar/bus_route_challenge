package com.goeuro.busroutes.Exception;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Error {
    private int errorCode;
    private String message;
}

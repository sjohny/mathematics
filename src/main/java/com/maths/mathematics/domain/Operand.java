package com.maths.mathematics.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Operand {
    MUTLIPLICATION("X"), DIVISION("/"), ADDITION("+"), SUBSTRACTION("-");

    @Getter
    private String value;

}

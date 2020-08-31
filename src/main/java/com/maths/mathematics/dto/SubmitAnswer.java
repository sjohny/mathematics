package com.maths.mathematics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitAnswer {
    private long id;

    private long firstNumber;

    private long secondNumber;

    private String operand;

    private Long answer;

    private boolean correct;
}

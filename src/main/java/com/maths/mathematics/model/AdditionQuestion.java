package com.maths.mathematics.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdditionQuestion {
    private long augend;
    private long addend;
}

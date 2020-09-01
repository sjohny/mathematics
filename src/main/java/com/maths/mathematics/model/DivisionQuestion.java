package com.maths.mathematics.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DivisionQuestion {
    private long dividend;
    private long divisor;
}

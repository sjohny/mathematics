package com.maths.mathematics.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubtractionQuestion {
    private long subtrahend;
    private long minuend;
}

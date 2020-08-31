package com.maths.mathematics.model;

import com.maths.mathematics.utils.Example;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MultiplicationQuestion {
    @ApiModelProperty(example = Example.QUESTION_ID)
    private long id;
    private long multiplier;
    private long multiplicand;
}

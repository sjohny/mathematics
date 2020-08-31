package com.maths.mathematics.dto;

import com.maths.mathematics.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitAnswersDto {

    private User user;

    private List<SubmitAnswer> submitAnswers;

    private long marks;
}

package com.maths.mathematics.service;

import com.maths.mathematics.domain.Operand;
import com.maths.mathematics.entities.Question;
import com.maths.mathematics.entities.User;

import java.util.List;

public interface FailedAnswerService {
    List<Question> getPreviouslyFailedQuestions(User user, Operand operand);
}

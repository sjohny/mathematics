package com.maths.mathematics.service;

import com.maths.mathematics.entities.Question;
import com.maths.mathematics.entities.User;

import java.util.List;

public interface SubtractionService {
    List<Question> getSubtractionQuestions(User user);
}

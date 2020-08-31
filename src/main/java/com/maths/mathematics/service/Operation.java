package com.maths.mathematics.service;

import com.maths.mathematics.entities.Answer;
import com.maths.mathematics.entities.Question;
import com.maths.mathematics.entities.Result;
import com.maths.mathematics.entities.User;

import java.util.List;
import java.util.Optional;

public interface Operation {

    List<Question> getQuestions(User user);

    Optional<Question> findQuestion(Long id);

    Optional<Answer> findAnswer(Long id);

    List<Answer> getAllAnswers();

    List<Result> saveAllResults(List<Result> results);
}

package com.maths.mathematics.service;

import com.maths.mathematics.entities.Question;
import com.maths.mathematics.model.AdditionQuestion;
import com.maths.mathematics.model.DivisionQuestion;
import com.maths.mathematics.model.MultiplicationQuestion;
import com.maths.mathematics.model.SubtractionQuestion;

import java.util.List;

public interface QuestionAnswerService {

    void saveMultiplicationAnswers(List<Question> savedQuestions);

    List<Question> saveMultiplicationQuestions(List<MultiplicationQuestion> questions);

    List<Question> saveAdditionQuestions(List<AdditionQuestion> questions);

    void saveAdditionAnswers(List<Question> savedQuestions);

    List<Question> saveSubstractionQuestions(List<SubtractionQuestion> questions);

    void saveSubstractionAnswers(List<Question> savedQuestions);

    List<Question> saveDivisionQuestions(List<DivisionQuestion> questions);

    void saveDivisionAnswers(List<Question> savedQuestions);
}

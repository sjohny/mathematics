package com.maths.mathematics.service;

import com.maths.mathematics.domain.Operand;
import com.maths.mathematics.entities.Question;
import com.maths.mathematics.entities.User;
import com.maths.mathematics.model.MultiplicationQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.maths.mathematics.utils.GenerateRandom.getRandomInteger;

@Service
public class MultiplicationServiceImpl implements MultipliationService {
    private static final int MIN_NUMBER_RANGE = 1;
    private static final int MAX_NUMBER_RANGE = 1000;

    @Value("${app.multiplication.max.numbers}")
    private Integer maxNumberOfQuestions;

    @Autowired
    private QuestionAnswerService questionAnswerService;

    @Autowired
    private FailedAnswerService failedAnswerService;

    @Override
    public List<Question> getMultiplicationQuestions(User user) {
        List<Question> previouslyFailedQuestions = failedAnswerService.getPreviouslyFailedQuestions(user, Operand.MUTLIPLICATION);
        if (previouslyFailedQuestions.isEmpty()) {
            return getQuestions(maxNumberOfQuestions);
        }

        int failedAnswerCountToInclude = Math.subtractExact(maxNumberOfQuestions, previouslyFailedQuestions.size());
        if (failedAnswerCountToInclude >= 0) {
            int numberOfNewQuestionsToInclude = Math.subtractExact(maxNumberOfQuestions, previouslyFailedQuestions.size());
            List<Question> questions = previouslyFailedQuestions;
            if (numberOfNewQuestionsToInclude != 0) {
                questions.addAll(getQuestions(numberOfNewQuestionsToInclude));
            }
            return questions;
        } else {//Number of expected questions is covered by failed numbers
            return previouslyFailedQuestions.subList(0, maxNumberOfQuestions - 1);

        }
    }

    private List<Question> getQuestions(int numberOfQuestionsExpected) {
        List<MultiplicationQuestion> questions = getTableQuestions(numberOfQuestionsExpected);
        List<Question> savedQuestions = questionAnswerService.saveMultiplicationQuestions(questions);
        questionAnswerService.saveMultiplicationAnswers(savedQuestions);
        return savedQuestions;
    }

    private List<MultiplicationQuestion> getTableQuestions(int numberOfQuestionsExpected) {
        return IntStream.range(0, numberOfQuestionsExpected)
                .mapToObj(i -> getMultiplicationTableQuestion())
                .collect(Collectors.toList());
    }

    private MultiplicationQuestion getMultiplicationTableQuestion() {
        int min = MIN_NUMBER_RANGE;
        int max = MAX_NUMBER_RANGE;
        final int multiplier = getRandomInteger(min, max);
        final int multiplicand = getRandomInteger(min, max);
        return MultiplicationQuestion.builder()
                .multiplier(multiplier)
                .multiplicand(multiplicand)
                .build();
    }
}

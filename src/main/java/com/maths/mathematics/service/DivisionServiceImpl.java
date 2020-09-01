package com.maths.mathematics.service;

import com.maths.mathematics.domain.Operand;
import com.maths.mathematics.entities.Question;
import com.maths.mathematics.entities.User;
import com.maths.mathematics.model.DivisionQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.maths.mathematics.utils.GenerateRandom.getRandomInteger;

@Service
public class DivisionServiceImpl implements DivisionService {
    private static final int MIN_NUMBER_DIVISOR = 1;
    private static final int MAX_NUMBER_DIVISOR = 10;
    private static final int MIN_NUMBER_DIVIDENT = 0;
    private static final int MAX_NUMBER_DIVIDENT = 100;

    @Value("${app.division.max.numbers}")
    private Integer maxNumberOfQuestions;

    @Autowired
    private QuestionAnswerService questionAnswerService;

    @Autowired
    private FailedAnswerService failedAnswerService;

    @Override
    public List<Question> getDivisionQuestions(User user) {
        List<Question> previouslyFailedQuestions = failedAnswerService.getPreviouslyFailedQuestions(user, Operand.DIVISION);
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
        List<DivisionQuestion> questions = getDivisionQuestions(numberOfQuestionsExpected);
        List<Question> savedQuestions = questionAnswerService.saveDivisionQuestions(questions);
        questionAnswerService.saveDivisionAnswers(savedQuestions);
        return savedQuestions;
    }

    private List<DivisionQuestion> getDivisionQuestions(int numberOfQuestionsExpected) {
        return IntStream.range(0, numberOfQuestionsExpected)
                .mapToObj(i -> generateQuestions())
                .collect(Collectors.toList());
    }

    private DivisionQuestion generateQuestions() {
        long baseNumberToMultiply = getRandomInteger(MIN_NUMBER_DIVIDENT, MAX_NUMBER_DIVIDENT);
        long divisor = getRandomInteger(MIN_NUMBER_DIVISOR, MAX_NUMBER_DIVISOR);
        long dividend = divisor * baseNumberToMultiply;
        return DivisionQuestion.builder()
                .dividend(dividend)
                .divisor(divisor)
                .build();
    }
}

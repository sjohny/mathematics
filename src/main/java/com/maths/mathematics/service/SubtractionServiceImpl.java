package com.maths.mathematics.service;

import com.maths.mathematics.domain.Operand;
import com.maths.mathematics.entities.Question;
import com.maths.mathematics.entities.User;
import com.maths.mathematics.model.SubtractionQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.maths.mathematics.utils.GenerateRandom.getRandomInteger;

@Service
public class SubtractionServiceImpl implements SubtractionService {
    private static final int MIN_NUMBER_FOR_RANGE = 10000;
    private static final int MAX_NUMBER_FOR_RANGE = 100000;

    @Value("${app.subtraction.max.numbers}")
    private Integer maxNumberOfQuestions;

    @Autowired
    private QuestionAnswerService questionAnswerService;

    @Autowired
    private FailedAnswerService failedAnswerService;

    @Override
    public List<Question> getSubtractionQuestions(User user) {

        List<Question> previouslyFailedQuestions = failedAnswerService.getPreviouslyFailedQuestions(user, Operand.SUBTRACTION);
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
        List<SubtractionQuestion> questions = getSubtractionQuestions(numberOfQuestionsExpected);
        List<Question> savedQuestions = questionAnswerService.saveSubstractionQuestions(questions);
        questionAnswerService.saveSubstractionAnswers(savedQuestions);
        return savedQuestions;
    }

    private List<SubtractionQuestion> getSubtractionQuestions(int numberOfQuestionsExpected) {
        return IntStream.range(0, numberOfQuestionsExpected)
                .mapToObj(i -> generateQuestions())
                .collect(Collectors.toList());
    }

    private SubtractionQuestion generateQuestions() {
        long subtrahend = getRandomInteger(MIN_NUMBER_FOR_RANGE, MAX_NUMBER_FOR_RANGE);
        long minuend = getRandomInteger(MIN_NUMBER_FOR_RANGE, (int) subtrahend);
        return SubtractionQuestion.builder()
                .subtrahend(subtrahend)
                .minuend(minuend)
                .build();
    }
}

package com.maths.mathematics.service;

import com.maths.mathematics.entities.Question;
import com.maths.mathematics.entities.User;
import com.maths.mathematics.model.AdditionQuestion;
import com.maths.mathematics.model.DivisionQuestion;
import com.maths.mathematics.repositories.QuestionRepository;
import com.maths.mathematics.repositories.ResultRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.maths.mathematics.utils.GenerateRandom.getRandomInteger;

@AllArgsConstructor
@Service
public class DivisionServiceImpl implements DivisionService {
    private static final int EXPECTED_NUMBER_OF_ADDITION_QUESTIONS = 5;
    private static final int MIN_NUMBER_DIVISOR = 1;
    private static final int MAX_NUMBER_DIVISOR = 10;

    private static final int MIN_NUMBER_DIVIDENT = 0;
    private static final int MAX_NUMBER_DIVIDENT = 100;

    private final QuestionRepository questionRepository;
    private final ResultRepository resultRepository;
    private final QuestionAnswerService questionAnswerService;

    @Override
    public List<Question> getDivisionQuestions(User user) {

        List<DivisionQuestion> questions = getDivisionQuestions(EXPECTED_NUMBER_OF_ADDITION_QUESTIONS);
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

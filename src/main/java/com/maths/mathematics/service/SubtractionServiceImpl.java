package com.maths.mathematics.service;

import com.maths.mathematics.entities.Question;
import com.maths.mathematics.entities.User;
import com.maths.mathematics.model.SubtractionQuestion;
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
public class SubtractionServiceImpl implements SubtractionService {
    private static final int EXPECTED_NUMBER_OF_ADDITION_QUESTIONS = 5;
    private static final int MIN_NUMBER_FOR_RANGE = 10000;
    private static final int MAX_NUMBER_FOR_RANGE = 100000;

    private final QuestionRepository questionRepository;
    private final ResultRepository resultRepository;
    private final QuestionAnswerService questionAnswerService;

    @Override
    public List<Question> getSubtractionQuestions(User user) {

        List<SubtractionQuestion> questions = getSubtractionQuestions(EXPECTED_NUMBER_OF_ADDITION_QUESTIONS);
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

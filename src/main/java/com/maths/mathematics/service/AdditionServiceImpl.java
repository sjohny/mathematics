package com.maths.mathematics.service;

import com.maths.mathematics.entities.Question;
import com.maths.mathematics.entities.User;
import com.maths.mathematics.model.AdditionQuestion;
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
public class AdditionServiceImpl implements AdditionService {
    private static final int EXPECTED_NUMBER_OF_ADDITION_QUESTIONS = 5;
    private static final int MIN_NUMBER_FOR_RANGE = 10000;
    private static final int MAX_NUMBER_FOR_RANGE = 100000;

    private final QuestionRepository questionRepository;
    private final ResultRepository resultRepository;
    private final QuestionAnswerService questionAnswerService;

    @Override
    public List<Question> getAdditionQuestions(User user) {
        List<AdditionQuestion> questions = getAdditionQuestions(EXPECTED_NUMBER_OF_ADDITION_QUESTIONS);
        List<Question> savedQuestions = questionAnswerService.saveAdditionQuestions(questions);
        questionAnswerService.saveAdditionAnswers(savedQuestions);
        return savedQuestions;
    }

    private List<AdditionQuestion> getAdditionQuestions(int numberOfQuestionsExpected) {
        return IntStream.range(0, numberOfQuestionsExpected)
                .mapToObj(i -> generateQuestions())
                .collect(Collectors.toList());
    }

    private AdditionQuestion generateQuestions() {
        return AdditionQuestion.builder()
                .augend(getRandomInteger(MIN_NUMBER_FOR_RANGE, MAX_NUMBER_FOR_RANGE))
                .addend(getRandomInteger(MIN_NUMBER_FOR_RANGE, MAX_NUMBER_FOR_RANGE))
                .build();
    }
}

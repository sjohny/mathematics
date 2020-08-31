package com.maths.mathematics.service;

import com.maths.mathematics.domain.Operand;
import com.maths.mathematics.entities.Question;
import com.maths.mathematics.entities.User;
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

    private final ResultRepository resultRepository;


    @Override
    public List<Question> getAdditionQuestions(User user) {
        return IntStream.range(0, EXPECTED_NUMBER_OF_ADDITION_QUESTIONS)
                .mapToObj(i -> generateQuestions())
                .collect(Collectors.toList());
        //return questionsToInclude;
    }

    private Question generateQuestions() {
        return Question.builder()
                .firstNumber(getRandomInteger(MIN_NUMBER_FOR_RANGE, MAX_NUMBER_FOR_RANGE))
                .operand(Operand.ADDITION)
                .secondNumber(getRandomInteger(MIN_NUMBER_FOR_RANGE, MAX_NUMBER_FOR_RANGE))
                .build();
    }
}

package com.maths.mathematics.service;

import com.maths.mathematics.domain.Operand;
import com.maths.mathematics.entities.Answer;
import com.maths.mathematics.entities.Question;
import com.maths.mathematics.entities.Result;
import com.maths.mathematics.entities.User;
import com.maths.mathematics.model.MultiplicationQuestion;
import com.maths.mathematics.repositories.CorrectAnswerRepository;
import com.maths.mathematics.repositories.QuestionRepository;
import com.maths.mathematics.repositories.ResultRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.maths.mathematics.utils.GenerateRandom.getRandomInteger;

@AllArgsConstructor
@Service
public class MultiplicationServiceImpl implements MultipliationService {
    private static final int EXPECTED_NUMBER_OF_TABLE_QUESTIONS = 20;
    private static final int MIN_NUMBER_MULITPLICATION_TABLE = 1;
    private static final int MAX_NUMBER_MULITPLICATION_TABLE = 100;

    private final QuestionRepository questionRepository;
    private final CorrectAnswerRepository correctAnswerRepository;
    private final ResultRepository resultRepository;


    @Override
    public  List<Question> getMultiplicationQuestions(User user) {
        List<Question> questionsToInclude = new ArrayList<>();
        Result latestResultForUser = resultRepository.findFirstByOrderBySubmittedAtDesc();
        if (latestResultForUser != null) {
            List<Result> resultsWithSameQuestionSet = resultRepository.findAllByQuestionSet(latestResultForUser.getQuestionSet());
            List<Result> previouslyFailedResults = resultsWithSameQuestionSet.stream()
                    .filter(r -> r.getCorrectAnswer() != r.getSubmittedAnswer()).collect(Collectors.toList());
            List<Question> previouslyFailedQuestions = previouslyFailedResults.stream()
                    .map(r -> questionRepository.findById(r.getQuestionId()).get()).collect(Collectors.toList());
            int possibleFailedAnswerCountToInclude = EXPECTED_NUMBER_OF_TABLE_QUESTIONS - previouslyFailedQuestions.size();
            if (possibleFailedAnswerCountToInclude >= 0) {
                questionsToInclude = previouslyFailedQuestions;
            } else {
                questionsToInclude = previouslyFailedQuestions.subList(0, EXPECTED_NUMBER_OF_TABLE_QUESTIONS - 1);
            }
        }
        if (questionsToInclude.size() < EXPECTED_NUMBER_OF_TABLE_QUESTIONS) {
            int remainingCountOFQuestionsCanBeIncluded = EXPECTED_NUMBER_OF_TABLE_QUESTIONS - questionsToInclude.size();

            List<Question> addExtraQuestions = getQuestions(remainingCountOFQuestionsCanBeIncluded);
            questionsToInclude.addAll(addExtraQuestions);
        }
        return questionsToInclude;
    }

    private List<Question> getQuestions(int numberOfQuestionsExpected) {
        List<MultiplicationQuestion> questions = getTableQuestions(numberOfQuestionsExpected);
        questionRepository.saveAll(questions.stream().map(q -> Question.builder()
                .id(q.getId())
                .firstNumber(q.getMultiplicand())
                .secondNumber(q.getMultiplier())
                .operand(Operand.MUTLIPLICATION)
                .build()).collect(Collectors.toList()));
        List<Question> savedQuestions = questionRepository.findAllByOperandOrderByIdDesc(
                Operand.MUTLIPLICATION, PageRequest.of(0, numberOfQuestionsExpected))
                .stream()
                .limit(numberOfQuestionsExpected)
                .collect(Collectors.toList());
        correctAnswerRepository.saveAll(
                savedQuestions.stream()
                        .map(q -> Answer.builder()
                                .id(q.getId())
                                .question(q)
                                .answer(q.getFirstNumber() * q.getSecondNumber())
                                .build()).collect(Collectors.toList()));
        return savedQuestions;
    }

    private List<MultiplicationQuestion> getTableQuestions(int numberOfQuestionsExpected) {
        return IntStream.range(0, numberOfQuestionsExpected)
                .mapToObj(i -> getMultiplicationTableQuestion())
                .collect(Collectors.toList());
    }

    private MultiplicationQuestion getMultiplicationTableQuestion() {
        int min = MIN_NUMBER_MULITPLICATION_TABLE;
        int max = MAX_NUMBER_MULITPLICATION_TABLE;
        final int multiplier = getRandomInteger(min, max);
        final int multiplicand = getRandomInteger(min, max);
        return MultiplicationQuestion.builder()
                .multiplier(multiplier)
                .multiplicand(multiplicand)
                .build();
    }
}

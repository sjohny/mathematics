package com.maths.mathematics.service;

import com.maths.mathematics.entities.Question;
import com.maths.mathematics.entities.Result;
import com.maths.mathematics.entities.User;
import com.maths.mathematics.model.MultiplicationQuestion;
import com.maths.mathematics.repositories.QuestionRepository;
import com.maths.mathematics.repositories.ResultRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.maths.mathematics.utils.GenerateRandom.getRandomInteger;

@AllArgsConstructor
@Service
public class MultiplicationServiceImpl implements MultipliationService {
    private static final int EXPECTED_NUMBER_OF_TABLE_QUESTIONS = 10;
    private static final int MIN_NUMBER_MULITPLICATION_TABLE = 1;
    private static final int MAX_NUMBER_MULITPLICATION_TABLE = 100;

    private final QuestionRepository questionRepository;
    private final ResultRepository resultRepository;
    private final QuestionAnswerService questionAnswerService;


    @Override
    public List<Question> getMultiplicationQuestions(User user) {
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

package com.maths.mathematics.service;

import com.maths.mathematics.domain.Operand;
import com.maths.mathematics.entities.Question;
import com.maths.mathematics.entities.Result;
import com.maths.mathematics.entities.User;
import com.maths.mathematics.model.DivisionQuestion;
import com.maths.mathematics.repositories.QuestionRepository;
import com.maths.mathematics.repositories.ResultRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.maths.mathematics.utils.GenerateRandom.getRandomInteger;

@AllArgsConstructor
@Service
public class PreviouslyFailedServiceImpl implements FailedAnswerService{
    private final QuestionRepository questionRepository;
    private final ResultRepository resultRepository;

    @Override
    public List<Question> getPreviouslyFailedQuestions(User user, Operand operand) {

        Result latestResultForUser = resultRepository.findFirstByUserIdOrderBySubmittedAtDesc(user.getId());
        if (latestResultForUser != null) {
            List<Result> resultsWithSameQuestionSet = resultRepository.findAllByQuestionSet(latestResultForUser.getQuestionSet());
            List<Result> previouslyFailedResults = resultsWithSameQuestionSet.stream()
                    .filter(r -> r.getCorrectAnswer() != r.getSubmittedAnswer()).collect(Collectors.toList());
            List<Question> previouslyFailedQuestions =  previouslyFailedResults.stream()
                    .map(r -> questionRepository.findById(r.getQuestionId()).get()).collect(Collectors.toList());
            return previouslyFailedQuestions.stream().filter(f -> f.getOperand().equals(operand)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}

package com.maths.mathematics.service;

import com.maths.mathematics.entities.Answer;
import com.maths.mathematics.entities.Question;
import com.maths.mathematics.entities.Result;
import com.maths.mathematics.entities.User;
import com.maths.mathematics.repositories.CorrectAnswerRepository;
import com.maths.mathematics.repositories.QuestionRepository;
import com.maths.mathematics.repositories.ResultRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class OperationImpl implements Operation {

    private final QuestionRepository questionRepository;
    private final CorrectAnswerRepository correctAnswerRepository;
    private final ResultRepository resultRepository;

    private final MultipliationService multipliationService;


    @Override
    public List<Question> getQuestions(User user) {
        return multipliationService.getMultiplicationQuestions(user);
    }

    @Override
    public Optional<Question> findQuestion(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public Optional<Answer> findAnswer(Long id) {
        return correctAnswerRepository.findById(id);
    }

    @Override
    public List<Answer> getAllAnswers() {
        return correctAnswerRepository.findAll();
    }

    @Override
    public List<Result> saveAllResults(List<Result> results) {
        return resultRepository.saveAll(results);
    }


}

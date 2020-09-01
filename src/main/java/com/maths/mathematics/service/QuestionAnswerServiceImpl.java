package com.maths.mathematics.service;

import com.maths.mathematics.domain.Operand;
import com.maths.mathematics.entities.Answer;
import com.maths.mathematics.entities.Question;
import com.maths.mathematics.model.AdditionQuestion;
import com.maths.mathematics.model.DivisionQuestion;
import com.maths.mathematics.model.MultiplicationQuestion;
import com.maths.mathematics.model.SubtractionQuestion;
import com.maths.mathematics.repositories.CorrectAnswerRepository;
import com.maths.mathematics.repositories.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class QuestionAnswerServiceImpl implements QuestionAnswerService{

    private final QuestionRepository questionRepository;
    private final CorrectAnswerRepository correctAnswerRepository;

    @Override
    public List<Question> saveMultiplicationQuestions(List<MultiplicationQuestion> questions) {
        return questionRepository.saveAll(questions.stream().map(q -> Question.builder()
                .firstNumber(q.getMultiplicand())
                .secondNumber(q.getMultiplier())
                .operand(Operand.MUTLIPLICATION)
                .build()).collect(Collectors.toList()));
    }

    @Override
    public void saveMultiplicationAnswers(List<Question> savedQuestions) {
        correctAnswerRepository.saveAll(
                savedQuestions.stream()
                        .map(q -> Answer.builder()
                                .id(q.getId())
                                .question(q)
                                .answer(q.getFirstNumber() * q.getSecondNumber())
                                .build()).collect(Collectors.toList()));
    }

    @Override
    public List<Question> saveAdditionQuestions(List<AdditionQuestion> questions) {
        return questionRepository.saveAll(questions.stream().map(q -> Question.builder()
                .firstNumber(q.getAugend())
                .secondNumber(q.getAddend())
                .operand(Operand.ADDITION)
                .build()).collect(Collectors.toList()));
    }

    @Override
    public void saveAdditionAnswers(List<Question> savedQuestions) {
        correctAnswerRepository.saveAll(
                savedQuestions.stream()
                        .map(q -> Answer.builder()
                                .id(q.getId())
                                .question(q)
                                .answer(q.getFirstNumber() + q.getSecondNumber())
                                .build()).collect(Collectors.toList()));
    }

    @Override
    public List<Question> saveSubstractionQuestions(List<SubtractionQuestion> questions) {
        return questionRepository.saveAll(questions.stream().map(q -> Question.builder()
                .firstNumber(q.getSubtrahend())
                .secondNumber(q.getMinuend())
                .operand(Operand.SUBSTRACTION)
                .build()).collect(Collectors.toList()));
    }

    @Override
    public void saveSubstractionAnswers(List<Question> savedQuestions) {
        correctAnswerRepository.saveAll(
                savedQuestions.stream()
                        .map(q -> Answer.builder()
                                .id(q.getId())
                                .question(q)
                                .answer(q.getFirstNumber() - q.getSecondNumber())
                                .build()).collect(Collectors.toList()));
    }

    @Override
    public List<Question> saveDivisionQuestions(List<DivisionQuestion> questions) {
        return questionRepository.saveAll(questions.stream().map(q -> Question.builder()
                .firstNumber(q.getDividend())
                .secondNumber(q.getDivisor())
                .operand(Operand.DIVISION)
                .build()).collect(Collectors.toList()));
    }

    @Override
    public void saveDivisionAnswers(List<Question> savedQuestions) {
        correctAnswerRepository.saveAll(
                savedQuestions.stream()
                        .map(q -> Answer.builder()
                                .id(q.getId())
                                .question(q)
                                .answer(q.getFirstNumber() / q.getSecondNumber())
                                .build()).collect(Collectors.toList()));

    }
}

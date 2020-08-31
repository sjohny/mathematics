package com.maths.mathematics.application;

import com.maths.mathematics.dto.SubmitAnswer;
import com.maths.mathematics.dto.SubmitAnswersDto;
import com.maths.mathematics.entities.Answer;
import com.maths.mathematics.entities.Question;
import com.maths.mathematics.entities.Result;
import com.maths.mathematics.entities.User;
import com.maths.mathematics.repositories.UserRepository;
import com.maths.mathematics.service.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@Slf4j
public class MultiplicationController {

    private final Operation service;

    private final UserRepository userRepository;



    @RequestMapping("/questions-to-submit/{id}")
    public String submitAnswers(@PathVariable long id, Model model) {
        SubmitAnswersDto submitAnswersDto = new SubmitAnswersDto();

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        submitAnswersDto.setUser(user);

        List<SubmitAnswer> list = service.getQuestions(user).stream().map(q -> SubmitAnswer.builder()
                .id(q.getId())
                .firstNumber(q.getFirstNumber())
                .operand(q.getOperand().getValue())
                .secondNumber(q.getSecondNumber()).build()).collect(Collectors.toList());
        submitAnswersDto.setSubmitAnswers(list);
        model.addAttribute("form", submitAnswersDto);

        return "answer";

    }

    @PostMapping("/save-answer/{id}")
    public String saveAnswers(@ModelAttribute SubmitAnswersDto form, @PathVariable long id, Model model) {
        UUID questionSet = UUID.randomUUID();
        List<Result> results = form.getSubmitAnswers().stream().map(sa -> mapToResults(id, sa, questionSet)).collect(Collectors.toList());
        service.saveAllResults(results);
        List<SubmitAnswer> list = form.getSubmitAnswers().stream().map(this::mapToSubmitAnswer).collect(Collectors.toList());
        SubmitAnswersDto resultsDto = new SubmitAnswersDto();
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        resultsDto.setUser(user);
        resultsDto.setSubmitAnswers(list);
        long numberOfCorrectAnswers = list.stream().filter(SubmitAnswer::isCorrect).count();
        Double percentage = (double) numberOfCorrectAnswers / (double) list.size() * 100d;
        resultsDto.setMarks(percentage.intValue());
        model.addAttribute("results", resultsDto);
        return "results";
    }

    private SubmitAnswer mapToSubmitAnswer(SubmitAnswer sa) {
        Question question = service.findQuestion(sa.getId()).orElseThrow(IllegalArgumentException::new);
        Answer correctAnswer = service.findAnswer(sa.getId()).orElseThrow(IllegalArgumentException::new);
        return SubmitAnswer.builder()
                .id(sa.getId())
                .firstNumber(question.getFirstNumber())
                .operand(question.getOperand().getValue())
                .secondNumber(question.getSecondNumber()).answer(sa.getAnswer())
                .correct(sa.getAnswer() != null && sa.getAnswer() == correctAnswer.getAnswer())
                .build();
    }

    private Result mapToResults(Long userId, SubmitAnswer sa, UUID questionSet) {
        Answer answer = service.findAnswer(sa.getId()). orElseThrow(IllegalArgumentException::new);
        return Result.builder()
                .submittedAt(LocalDateTime.now())
                .userId(userId)
                .questionId(sa.getId())
                .correctAnswer(answer.getAnswer())
                .submittedAnswer(sa.getAnswer() != null ? sa.getAnswer() : 0L)
                .questionSet(questionSet.toString())
                .build();
    }
}

package com.maths.mathematics.application;

import com.maths.mathematics.dto.ResultDto;
import com.maths.mathematics.entities.Result;
import com.maths.mathematics.entities.User;
import com.maths.mathematics.repositories.ResultRepository;
import com.maths.mathematics.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class UserController {


    public static final String REDIRECT_TO_INDEX = "redirect:/";
    public static final String INDEX = "index";
    private final UserRepository userRepository;
    private final ResultRepository resultRepository;

    @GetMapping("/")
    public String start(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        List<ResultDto> resultDtos = mapToResultDto(users);
        model.addAttribute("results", resultDtos);

        return INDEX;
    }

    private List<ResultDto> mapToResultDto(List<User> users) {
        List<String> distinctQuestionSet = resultRepository.findByDistinctQuestionSet();
        return distinctQuestionSet.stream().map(qs -> {
            List<Result> resultsForSameSet = resultRepository.findAllByQuestionSet(qs);
            long numberOfCorrectAnswers = resultsForSameSet.stream()
                    .filter(result -> result.getCorrectAnswer() == result.getSubmittedAnswer()).count();
            Result takeOne = resultsForSameSet.get(0);
            Double percentage = (double) numberOfCorrectAnswers / (double) resultsForSameSet.size() * 100d;

            return ResultDto.builder()
                    .userName(users.stream().filter(user -> user.getId() == takeOne.getUserId()).findFirst().get().getName())
                    .submittedAt(takeOne.getSubmittedAt())
                    .marks(percentage.intValue())
                    .questionSet(takeOne.getQuestionSet()).build();
        }).collect(Collectors.toList());
    }

    @GetMapping("/signup")
    public String showSignUpForm(User user) {
        return "add-user";
    }

    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }

        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        //return REDIRECT_TO_INDEX;
        return "redirect:/questions-to-submit/" + user.getId();
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "update-user";
        }

        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return REDIRECT_TO_INDEX;
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        return INDEX;
    }
}
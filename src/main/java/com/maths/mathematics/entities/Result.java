package com.maths.mathematics.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@ToString
@Table(name = "result")
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private LocalDateTime submittedAt;

    @NotNull
    private long userId;

    @NotNull
    private long questionId;

    @NotNull
    private long submittedAnswer;

    @NotNull
    private long correctAnswer;

    @NotNull
    private String questionSet;

}

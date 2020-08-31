package com.maths.mathematics.entities;

import com.maths.mathematics.domain.Operand;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@Data
@ToString
@Table(name = "question")
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull(message = "firstNumber is mandatory")
    private long firstNumber;

    @NotNull(message = "secondNumber is mandatory")
    private long secondNumber;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Operand operand;

    @OneToOne(mappedBy = "question", cascade = CascadeType.ALL)
    private Answer answer;
}

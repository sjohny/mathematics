package com.maths.mathematics.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@Data
@ToString
@Table(name = "answer")
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    @Id
    @Column(name = "id")
    private long id;

    @NotNull(message = "answer is mandatory")
    private long answer;

    @OneToOne
    @MapsId
    private Question question;

}

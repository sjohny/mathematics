package com.maths.mathematics.dto;

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
public class ResultDto {

    @NotNull
    private LocalDateTime submittedAt;

    @NotNull
    private String userName;

    @NotNull
    private long marks;

    @NotNull
    private String questionSet;

}

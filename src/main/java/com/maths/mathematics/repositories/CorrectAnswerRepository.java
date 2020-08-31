package com.maths.mathematics.repositories;

import com.maths.mathematics.entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorrectAnswerRepository extends JpaRepository<Answer, Long> {

}

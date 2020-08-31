package com.maths.mathematics.repositories;

import com.maths.mathematics.domain.Operand;
import com.maths.mathematics.entities.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByOperandOrderByIdDesc(Operand operand, Pageable pageable);
}

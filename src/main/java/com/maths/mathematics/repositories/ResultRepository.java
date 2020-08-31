package com.maths.mathematics.repositories;

import com.maths.mathematics.entities.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
    Result findFirstByOrderBySubmittedAtDesc();

    List<Result> findAllByQuestionSet(String questionSet);

    List<Result> findAllDistinctQuestionSetByUserId(long userId);

    @Query(value = "select distinct(question_set) from result", nativeQuery = true)
    List<String> findByDistinctQuestionSet();

}


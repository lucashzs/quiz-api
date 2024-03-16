package com.api.quiz.repositories;

import com.api.quiz.entities.Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RankRepository extends JpaRepository <Rank, Long> {
    List<Rank> findByQuizId(long quizId);
}

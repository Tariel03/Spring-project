package com.example.springproject.Repository;

import com.example.springproject.models.SuggestWorker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuggestWorkerRepository extends JpaRepository<SuggestWorker, Long > {
    List<SuggestWorker> findSuggestWorkersByStatusNotLike(String status);
    List<SuggestWorker> findSuggestWorkersByStatusLike(String status);

}

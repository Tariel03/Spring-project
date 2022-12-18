package com.example.springproject.Repository;

import com.example.springproject.models.Workers_info;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface Workers_infoRepository extends JpaRepository<Workers_info, Long> {

}
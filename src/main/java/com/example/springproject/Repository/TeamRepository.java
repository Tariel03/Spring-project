//package com.example.springproject.Repository;
//
//import com.example.springproject.models.Customer;
//import com.example.springproject.models.Workers_info;
//import com.example.springproject.models.Team;
//import com.example.springproject.models.Zakaz;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.List;
//
//public interface TeamRepository extends JpaRepository<Team, Long>{
//    @Query("SELECT w FROM Workers_info w where w.customer = ?1")
//    List<Workers_info>findManagerByCustomer_Id(Workers_info workersInfo);
//
//    List<Workers_info> findManagerByAvailabilityLike(String is_available);
//}
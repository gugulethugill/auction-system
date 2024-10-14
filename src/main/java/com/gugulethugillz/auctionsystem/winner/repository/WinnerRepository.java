package com.gugulethugillz.auctionsystem.winner.repository;

import com.gugulethugillz.auctionsystem.winner.model.Winner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WinnerRepository extends JpaRepository<Winner, Long> {
    //SELECT * FROM winner w LEFT OUTER JOIN user u ON u.id = w.user_id WHERE u.username = ?
    @Query("SELECT w FROM Winner w WHERE w.user.username = :username")
    List<Winner> getByUsername(@Param("username") String username);


}

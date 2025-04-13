package com.saverfavor.microbank.repository;

import com.saverfavor.microbank.entity.Nominee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface NomineeRepository extends JpaRepository<Nominee,Integer> {
    @Query("select u FROM Nominee u where u.user.userid=:userId")
    List<Nominee> findNomineesByUserName(@Param("userId") String userId);

    @Query("select u from Nominee u where u.user.id=:userid")


    public  List<Nominee> findNomineesByUserId(@Param("userid")int userid);


    List<Nominee> findByUserId(long userId);

    boolean existsByUserId(long userId);
}
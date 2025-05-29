package com.saverfavor.capnest.repository;
import com.saverfavor.capnest.entity.HomePage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomePageRepository extends JpaRepository<HomePage,Integer>{
}

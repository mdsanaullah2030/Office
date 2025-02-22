package com.saverfavor.microbank.repository;

import com.saverfavor.microbank.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {

    Optional<Token> findByToken(String token);
    @Query("""
select t from Token t inner join UserRegistration u on t.userRegistration .id = u.id
where t.userRegistration.id = :userId and t.logout = false
""")
    List<Token> findAllTokensByUser(Integer userId);
}

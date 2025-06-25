package com.ecommerce.brandlyandco.repository;

import com.ecommerce.brandlyandco.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormRepository extends JpaRepository<Form, Integer> {
}

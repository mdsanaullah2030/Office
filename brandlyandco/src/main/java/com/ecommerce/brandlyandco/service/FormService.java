package com.ecommerce.brandlyandco.service;

import com.ecommerce.brandlyandco.entity.Form;
import com.ecommerce.brandlyandco.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormService {

    @Autowired
    private FormRepository formRepository;

    public Form saveForm(Form form) {
        return formRepository.save(form);
    }

    public List<Form> getAllForms() {
        return formRepository.findAll();
    }

    public Optional<Form> getFormById(int id) {
        return formRepository.findById(id);
    }

    public void deleteForm(int id) {
        formRepository.deleteById(id);
    }

    public Form updateForm(int id, Form updatedForm) {
        Optional<Form> existing = formRepository.findById(id);
        if (existing.isPresent()) {
            updatedForm.setId(id);
            return formRepository.save(updatedForm);
        } else {
            throw new RuntimeException("Form not found with ID " + id);
        }
    }
}

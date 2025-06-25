package com.ecommerce.brandlyandco.restController;

import com.ecommerce.brandlyandco.entity.Form;
import com.ecommerce.brandlyandco.service.FormService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor
@CrossOrigin
public class FormController {

    @Autowired
    private FormService formService;

    @PostMapping("/api/forms/save")
    public Form createForm(@RequestBody Form form) {
        return formService.saveForm(form);
    }
//
    @GetMapping("/api/forms/get")
    public List<Form> getAllForms() {
        return formService.getAllForms();
    }

    @GetMapping("/api/forms/{id}")
    public Form getFormById(@PathVariable int id) {
        return formService.getFormById(id)
                .orElseThrow(() -> new RuntimeException("Form not found with ID " + id));
    }

    @PutMapping("/api/forms/update/{id}")
    public Form updateForm(@PathVariable int id, @RequestBody Form form) {
        return formService.updateForm(id, form);
    }

    @DeleteMapping("/api/forms/delete/{id}")
    public String deleteForm(@PathVariable int id) {
        formService.deleteForm(id);
        return "Form with ID " + id + " has been deleted";
    }
}


package com.saverfavor.microbank.restController;

import com.saverfavor.microbank.entity.Nominee;
import com.saverfavor.microbank.service.NomineeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
public class NomineeController {

    @Autowired
    private NomineeService nomineeService;



    // Endpoint to fetch all nominees
    @GetMapping("/api/nominee/get")
    public ResponseEntity<List<Nominee>> getAllNominees() {
        List<Nominee> nominees = nomineeService.getAllNominee();
        return ResponseEntity.ok(nominees);
    }

    // Endpoint to fetch a nominee by ID
    @GetMapping("/api/nominee/get/{id}")
    public ResponseEntity<Nominee> getNomineeById(@PathVariable int id) {
        try {
            Nominee nominee = nomineeService.getNomineeById(id);
            return ResponseEntity.ok(nominee); // Returns 200 OK with nominee data
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Returns 404 if nominee not found
        }
    }



    // Endpoint to save a nominee
    @PostMapping("/api/nominee/save")
    public ResponseEntity<String> saveNominee(@RequestBody Nominee nominee) {
        try {
            nomineeService.saveNominee(nominee);
            return ResponseEntity.ok("Nominee saved successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }







    //Nominee Data Update //
    @PutMapping("/api/nominee/updateNominee/{id}")
    public ResponseEntity<String>updateNominee(
            @PathVariable int id,
            @RequestBody Nominee nominee
    )throws IOException{
        try {
            nomineeService.updateNominee( id,nominee);
            return ResponseEntity.ok("Nominee Updated successfully!");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nominee Not Found");
        }


    }



}

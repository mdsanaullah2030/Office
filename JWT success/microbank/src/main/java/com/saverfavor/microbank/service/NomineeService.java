package com.saverfavor.microbank.service;

import com.saverfavor.microbank.entity.Nominee;

import com.saverfavor.microbank.entity.User;
import com.saverfavor.microbank.repository.NomineeRepository;
import com.saverfavor.microbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
public class NomineeService {
    @Autowired
    private NomineeRepository nomineeRepository;

    @Autowired
    private UserRepository userRepository;

    // Fetch all nominees
    public List<Nominee> getAllNominee() {
        return nomineeRepository.findAll();
    }

    // Fetch nominee by ID
    public Nominee getNomineeById(int id) {
        return nomineeRepository.findById(id).orElse(new Nominee());
    }

    // Save a nominee
    @Transactional
    public void saveNominee(Nominee nominee) {
        // Validate and fetch the associated user
        User userRegistration = userRepository.findById(nominee.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User with this ID not found"));

        // Set the user and save the nominee
        nominee.setUser(userRegistration);
        nomineeRepository.save(nominee);
    }


    //Update Nominee Data //
    @Transactional
    public void updateNominee( int id,Nominee nominee)throws IOException {
        Nominee existingNominee=nomineeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nominee not found with this ID"));
        existingNominee.setName(nominee.getName());
        existingNominee.setEmail(nominee.getEmail());
        existingNominee.setDob(nominee.getDob());
        existingNominee.setRelationship(nominee.getRelationship());
        existingNominee.setCellNo(nominee.getCellNo());

        nomineeRepository.save(existingNominee);
    }
}
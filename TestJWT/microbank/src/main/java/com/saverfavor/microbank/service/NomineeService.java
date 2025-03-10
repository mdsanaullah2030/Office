package com.saverfavor.microbank.service;

import com.saverfavor.microbank.entity.Nominee;
import com.saverfavor.microbank.entity.User;

import com.saverfavor.microbank.repository.NomineeRepository;
import com.saverfavor.microbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        return nomineeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nominee not found with id: " + id));
    }


    // Save a nominee
    @Transactional
    public void saveNominee(Nominee nominee) {
        // Get the currently logged-in user's email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Assuming email is the username

        // Fetch the authenticated user from the database
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));

        // Assign the authenticated user to the nominee
        nominee.setUser(user);

        // Save nominee
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

package com.saverfavor.microbank.restController;

import com.saverfavor.microbank.DTO.ReferralDTO;
import com.saverfavor.microbank.entity.Nominee;
import com.saverfavor.microbank.entity.Referral;
import com.saverfavor.microbank.service.ReferralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Referral")
public class ReferralController {

    @Autowired
    private ReferralService referralService;

    // Endpoint to fetch all referrals
    @GetMapping("/get")
    public List<Referral> getAllReferrals() {
        return referralService.getAllReferrals();
    }

    // Save a referral
    @PostMapping("/save")
    public ResponseEntity<String> saveReferral(
            @RequestParam("inviterId") int inviterId,
            @RequestParam("inviteeId") int inviteeId) {
        System.out.println("Inviter ID: " + inviterId);
        System.out.println("Invitee ID: " + inviteeId);

        try {
            referralService.createReferral(inviterId, inviteeId);
            return ResponseEntity.ok("Referral saved successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }



    // Endpoint to fetch a nominee by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<Referral> getNomineeById(@PathVariable int id) {
        try {
            Referral referral = referralService.getReferralById(id);
            return ResponseEntity.ok(referral); // Returns 200 OK with nominee data
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Returns 404 if nominee not found
        }
    }



    @GetMapping("/getByUser/{userId}")
    public ResponseEntity<List<Referral>> getReferralsByUser(@PathVariable long userId) {
        List<Referral> referrals = referralService.getReferralsByUserId(userId);
        return ResponseEntity.ok(referrals);
    }
}
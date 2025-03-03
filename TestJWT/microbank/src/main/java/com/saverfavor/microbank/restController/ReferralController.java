
package com.saverfavor.microbank.restController;

import com.saverfavor.microbank.DTO.ReferralDTO;
import com.saverfavor.microbank.entity.Referral;
import com.saverfavor.microbank.service.ReferralService;
import org.springframework.beans.factory.annotation.Autowired;
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

}

package com.itshop.ecommerce.restController;

import com.itshop.ecommerce.entity.Branch;
import com.itshop.ecommerce.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@CrossOrigin
public class BranchController {

    private final BranchService branchService;

    @PostMapping("/api/branches/save")
    public Branch saveBranch(@RequestBody Branch branch) {
        return branchService.saveBranch(branch);
    }

    @GetMapping("/api/branches/get/all")
    public List<Branch> getAllBranches() {
        return branchService.getAllBranches();
    }

    @GetMapping("/api/branches/getID/{id}")
    public Branch getBranchById(@PathVariable int id) {
        return branchService.getBranchById(id);
    }

    @PutMapping("/api/branches/update/{id}")
    public Branch updateBranch(@PathVariable int id, @RequestBody Branch updatedBranch) {
        return branchService.updateBranch(id, updatedBranch);
    }

    @DeleteMapping("/api/branches/delete/{id}")
    public void deleteBranch(@PathVariable int id) {
        branchService.deleteBranch(id);
    }
}

package com.itshop.ecommerce.service;

import com.itshop.ecommerce.entity.Branch;
import com.itshop.ecommerce.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;

    public Branch saveBranch(Branch branch) {
        return branchRepository.save(branch);
    }

    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    public Branch getBranchById(int id) {
        return branchRepository.findById(id).orElse(null);
    }

    public Branch updateBranch(int id, Branch updatedBranch) {
        Branch existing = branchRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setBranchname(updatedBranch.getBranchname());
            existing.setBranchaddress(updatedBranch.getBranchaddress());
            existing.setPhonenumber(updatedBranch.getPhonenumber());
            existing.setDescription(updatedBranch.getDescription());
            return branchRepository.save(existing);
        }
        return null;
    }

    public void deleteBranch(int id) {
        branchRepository.deleteById(id);
    }
}

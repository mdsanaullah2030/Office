package com.itshop.ecommerce.service;



import com.itshop.ecommerce.entity.PcForPartAdd;
import com.itshop.ecommerce.entity.PcBuilder;
import com.itshop.ecommerce.repository.PcForPartAddRepository;
import com.itshop.ecommerce.repository.PcBuilderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PcForPartAddService {

    @Autowired
    private PcForPartAddRepository pcForPartAddRepository;

    @Autowired
    private PcBuilderRepository pcBuilderRepository;

    // Create
    public PcForPartAdd savePcForPart(PcForPartAdd pcForPartAdd) {
        int builderId = pcForPartAdd.getPcbuilder().getId();
        PcBuilder builder = pcBuilderRepository.findById(builderId)
                .orElseThrow(() -> new RuntimeException("PcBuilder with id " + builderId + " not found"));
        pcForPartAdd.setPcbuilder(builder);

        return pcForPartAddRepository.save(pcForPartAdd);
    }


    // Read all
    public List<PcForPartAdd> getAllCpu() {
        return pcForPartAddRepository.findAll();
    }

    // Read by ID
    public Optional<PcForPartAdd> getCpuById(int id) {
        return pcForPartAddRepository.findById(id);
    }

    public PcForPartAdd updatePcForPartAdd(int id, PcForPartAdd pcForPartadd) {
        PcForPartAdd existingCpu = pcForPartAddRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CPU not found"));

        existingCpu.setName(pcForPartadd.getName());
        existingCpu.setDescription(pcForPartadd.getDescription());
        existingCpu.setPerformance(pcForPartadd.getPerformance());
        existingCpu.setAbility(pcForPartadd.getAbility());
        existingCpu.setSpecialprice(pcForPartadd.getSpecialprice());
        existingCpu.setSpecialprice(pcForPartadd.getSpecialprice());

        return pcForPartAddRepository.save(existingCpu);
    }


    // Delete
    public void deletePcForPart(int id) {
        pcForPartAddRepository.deleteById(id);
    }


}


package com.itshop.ecommerce.service;

import com.itshop.ecommerce.entity.AboutUs;
import com.itshop.ecommerce.repository.AboutUsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AboutUsService {

    @Autowired
    private AboutUsRepository repository;

    public AboutUs create(AboutUs aboutUs) {
        return repository.save(aboutUs);
    }

    public List<AboutUs> getAll() {
        return repository.findAll();
    }

    public Optional<AboutUs> getById(int id) {
        return repository.findById(id);
    }

    public AboutUs update(int id, AboutUs aboutUs) {
        return repository.findById(id).map(existing -> {
            existing.setMission(aboutUs.getMission());
            existing.setVision(aboutUs.getVision());
            existing.setAchievements(aboutUs.getAchievements());
            existing.setBrandbusinesspartners(aboutUs.getBrandbusinesspartners());
            existing.setDescription(aboutUs.getDescription());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("AboutUs with ID " + id + " not found"));
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
}

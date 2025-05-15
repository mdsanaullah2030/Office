package com.itshop.ecommerce.service;

import com.itshop.ecommerce.entity.ServiceFeature;
import com.itshop.ecommerce.repository.ServiceFeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceFeatureService {

    @Autowired
    private ServiceFeatureRepository repository;

    public ServiceFeature saveFeature(ServiceFeature feature) {
        return repository.save(feature);
    }

    public List<ServiceFeature> getAllFeatures() {
        return repository.findAll();
    }



    public ServiceFeature updateFeature(int id, ServiceFeature updatedFeature) {
        return repository.findById(id)
                .map(feature -> {
                    feature.setEmi(updatedFeature.getEmi());
                    feature.setSupport(updatedFeature.getSupport());
                    feature.setPayment(updatedFeature.getPayment());
                    feature.setDelivery(updatedFeature.getDelivery());
                    return repository.save(feature);
                })
                .orElseThrow(() -> new RuntimeException("Feature not found with id " + id));
    }

    public void deleteFeature(int id) {
        repository.deleteById(id);
    }
}

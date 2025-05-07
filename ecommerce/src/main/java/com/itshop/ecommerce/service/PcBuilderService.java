package com.itshop.ecommerce.service;

import com.itshop.ecommerce.entity.Catagory;
import com.itshop.ecommerce.entity.PcBuilder;
import com.itshop.ecommerce.repository.PcBuilderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PcBuilderService {
    @Autowired
    private PcBuilderRepository  pcBuilderRepository;



public PcBuilder savePcBuilder(PcBuilder pcBuilder){
    return pcBuilderRepository.save(pcBuilder);
}

public List<PcBuilder>getAllPcBuilder(){
    return pcBuilderRepository.findAll();
}


  public Optional<PcBuilder>getPcBuilderById(int id){
    return pcBuilderRepository.findById(id);
  }





}

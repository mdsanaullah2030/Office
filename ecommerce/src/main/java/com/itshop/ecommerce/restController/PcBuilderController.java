package com.itshop.ecommerce.restController;

import com.itshop.ecommerce.entity.PcBuilder;
import com.itshop.ecommerce.service.PcForPartAddService;
import com.itshop.ecommerce.service.PcBuilderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@AllArgsConstructor

public class PcBuilderController {

    @Autowired
    private PcBuilderService pcBuilderService;
    @Autowired
    private PcForPartAddService cpuAddService;


    @PostMapping("/api/PcBuilder/save")
    public PcBuilder pcBuilder(@RequestBody PcBuilder pcBuilder) {
        return pcBuilderService.savePcBuilder(pcBuilder);
    }



}

package com.softserve.edu.controller.catalogue;

import com.softserve.edu.controller.client.application.util.CatalogueDTOTransformer;
import com.softserve.edu.dto.application.ApplicationFieldDTO;
import com.softserve.edu.service.catalogue.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RegionController {
    @Autowired
    private RegionService regionService;

    @RequestMapping(value = "application/regions", method = RequestMethod.GET)
    public List<ApplicationFieldDTO> getAll() {
        return CatalogueDTOTransformer.toDto(regionService.getAll());
    }
}

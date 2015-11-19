package com.softserve.edu.controller.catalogue;

import com.softserve.edu.controller.client.application.util.CatalogueDTOTransformer;
import com.softserve.edu.dto.application.ApplicationFieldDTO;
import com.softserve.edu.service.catalogue.LocalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LocalityController {

    @Autowired
    private LocalityService localityService;

    @RequestMapping(value = "application/localities/{districtId}", method = RequestMethod.GET)
    public List<ApplicationFieldDTO> getLocalitiesCorrespondingDistrict(@PathVariable Long districtId) {
        return CatalogueDTOTransformer.toDto(localityService.getLocalitiesCorrespondingDistrict(districtId));
    }

    @RequestMapping(value = "application/localities/{locationDesignation}/{districtId}", method = RequestMethod.GET)
    public List<String> getMailIndexForLocality(@PathVariable String locationDesignation, @PathVariable Long districtId) {
        return (localityService.getMailIndexForLocality(locationDesignation, districtId));
    }

}

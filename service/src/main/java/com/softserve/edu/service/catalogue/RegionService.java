package com.softserve.edu.service.catalogue;

import com.softserve.edu.entity.catalogue.Region;

public interface RegionService {

    Iterable<Region> getAll();

    Region getRegionByDesignation(String designation);

    /**
     * Find region by district id
     * @param districtId id of district
     * @return region
     */
    Region findByDistrictId(Long districtId);
}

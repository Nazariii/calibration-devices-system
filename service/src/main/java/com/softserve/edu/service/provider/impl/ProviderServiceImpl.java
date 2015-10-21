package com.softserve.edu.service.provider.impl;

import com.softserve.edu.entity.organization.Organization;
import com.softserve.edu.repository.OrganizationRepository;
import com.softserve.edu.service.provider.ProviderService;
import com.softserve.edu.service.utils.TypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class ProviderServiceImpl implements ProviderService {

    @Autowired
    private OrganizationRepository providerRepository;

    @Transactional(readOnly = true)
    public Set<String> getTypesById(Long id) {
        return TypeConverter.enumToString(providerRepository.findOrganizationTypesById(id));
    }

    @Transactional(readOnly = true)
    public Organization findById(Long id) {
        return providerRepository.findOne(id);
    }
}

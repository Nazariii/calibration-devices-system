package com.softserve.edu.service.calibrator.impl;

import com.softserve.edu.entity.catalogue.Team.DisassemblyTeam;
import com.softserve.edu.entity.device.Device;
import com.softserve.edu.entity.organization.Organization;
import com.softserve.edu.repository.CalibrationDisassemblyTeamRepository;
import com.softserve.edu.service.calibrator.CalibratorDisassemblyTeamService;
import com.softserve.edu.service.exceptions.DuplicateRecordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
public class CalibrationDisassemblyTeamServiceImpl implements CalibratorDisassemblyTeamService {

    @Autowired
    private CalibrationDisassemblyTeamRepository teamRepository;

    @Override
    @Transactional
    public List<DisassemblyTeam> getAll() {
        return (List<DisassemblyTeam>) teamRepository.findAll();
    }


    @Override
    @Transactional
    public List<DisassemblyTeam> getByOrganization(Organization organization) {
            return teamRepository.findByOrganization(organization);
    }

    @Override
    @Transactional
    public Page<DisassemblyTeam> getByOrganization(Organization organization, int pageNumber, int itemsPerPage) {
        PageRequest pageRequest = new PageRequest(pageNumber - 1, itemsPerPage);
        return teamRepository.findByOrganization(organization, pageRequest);
    }

    @Override
    @Transactional
    public Page<DisassemblyTeam> findByOrganizationAndSearchAndPagination(int pageNumber, int itemsPerPage,
                                                                          Organization organization, String search) {
        PageRequest pageRequest = new PageRequest(pageNumber - 1, itemsPerPage);
        return search == null ? teamRepository.findByOrganization(organization, pageRequest) :
                teamRepository.findByOrganizationAndNameLikeIgnoreCase(organization, "%" + search + "%", pageRequest);
    }

    @Override
    @Transactional
    public void add(DisassemblyTeam disassemblyTeam) throws DuplicateRecordException {
        try {
            if (!teamRepository.exists(disassemblyTeam.getId())) {
                teamRepository.save(disassemblyTeam);
            } else {
                throw new DuplicateRecordException(String.format("Team %s already exists.", disassemblyTeam.getId()));
            }
        } catch (Exception e) {
            throw new DuplicateRecordException(String.format("Team %s already exists.", disassemblyTeam.getId()));
        }
    }

    @Override
    @Transactional
    public DisassemblyTeam findById(String teamId) {
        return teamRepository.findOne(teamId);
    }

    @Override
    @Transactional
    public void edit(String id, String name, Date effectiveTo, Device.DeviceType specialization,
                     String leaderFullName, String leaderPhone, String leaderEmail) {
        DisassemblyTeam team = teamRepository.findOne(id);
        team.setName(name);
        team.setEffectiveTo(effectiveTo);
        team.setSpecialization(specialization);
        team.setLeaderFullName(leaderFullName);
        team.setLeaderPhone(leaderPhone);
        team.setLeaderEmail(leaderEmail);
        teamRepository.save(team);
    }

    @Override
    @Transactional
    public void delete(String teamId) {
        teamRepository.delete(teamRepository.findOne(teamId));
    }


    /**
     *
     * @param teamUsername
     * @return {@Literal true} if DisassemblyTeam already exist
     * else {@Literal false}
     */
    @Override
    @Transactional
    public boolean isTeamExist(String teamUsername) {
        return teamRepository.exists(teamUsername);
    }
}

package com.softserve.edu.dto.calibrator;


import com.softserve.edu.entity.catalogue.Team.DisassemblyTeam;
import com.softserve.edu.entity.device.Device;
import com.softserve.edu.entity.organization.Organization;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CalibrationDisassemblyTeamDTO {

    private String teamNumber;
    private String teamName;
    private Date effectiveTo;
    private String specialization;
    private String leaderFullName;
    private String leaderPhone;
    private String leaderEmail;

    /**
     * Create DTO object using basic entity
     * @param disassemblyTeam entity object
     */
    public CalibrationDisassemblyTeamDTO(DisassemblyTeam disassemblyTeam) {
        this.teamNumber = disassemblyTeam.getId();
        this.teamName = disassemblyTeam.getName();
        this.effectiveTo = disassemblyTeam.getEffectiveTo();
        this.specialization = disassemblyTeam.getSpecialization().toString();
        this.leaderFullName = disassemblyTeam.getLeaderFullName();
        this.leaderPhone = disassemblyTeam.getLeaderPhone();
        this.leaderEmail = disassemblyTeam.getLeaderEmail();
    }

    public DisassemblyTeam saveTeam(Organization organization) {
        return new DisassemblyTeam(teamNumber, teamName, effectiveTo, Device.DeviceType.valueOf(specialization), leaderFullName, leaderPhone,
                leaderEmail, organization);

    }
}
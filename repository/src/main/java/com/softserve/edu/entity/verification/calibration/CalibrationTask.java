package com.softserve.edu.entity.verification.calibration;

import com.softserve.edu.entity.catalogue.Team.DisassemblyTeam;
import com.softserve.edu.entity.device.CalibrationModule;
import com.softserve.edu.entity.organization.Organization;
import com.softserve.edu.entity.user.User;
import com.softserve.edu.entity.verification.Verification;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "CALIBRATION_TASK")
@Getter
@Setter
@EqualsAndHashCode(of = "Id")
@NoArgsConstructor
public class CalibrationTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moduleId")
    private CalibrationModule module;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teamId")
    private DisassemblyTeam team;

    @Temporal(TemporalType.DATE)
    private Date createTaskDate;

    @Temporal(TemporalType.DATE)
    private Date dateOfTask;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private User user;

    @OneToMany(mappedBy = "task")
    private Set<Verification> verifications;

    public CalibrationTask(CalibrationModule module, DisassemblyTeam team, Date createTaskDate, Date dateOfTask, User user, Set<Verification> verifications) {
        this.module = module;
        this.team = team;
        this.createTaskDate = createTaskDate;
        this.dateOfTask = dateOfTask;
        this.user = user;
        this.verifications = verifications;
    }
}

package org.example.sqi_images.drive.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.sqi_images.common.domain.BaseEntity;
import org.example.sqi_images.employee.domain.Employee;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "drive_employees")
public class DriveEmployee extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeId")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driveId")
    private Drive drive;

    @Enumerated(EnumType.STRING)
    private DriveAccessType role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "granter")
    private Employee granter;

    public Long getEmployeeId() {
        return employee.getId();
    }
}

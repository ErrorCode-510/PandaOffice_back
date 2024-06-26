package com.errorCode.pandaOffice.employee.domain.repository;


import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmployeeId(int employeeId);

    Optional<Employee> findByRefreshToken(String refreshToken);
}

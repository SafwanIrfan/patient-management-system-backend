package com.pm.patient_service.repository;

import com.pm.patient_service.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {

    boolean existsByEmail(String email);

    // it checks that patient email is in the database
    // but with different id then it will return true and
    // error should be thrown.
    boolean existsByEmailAndIdNot(String email, UUID id);
}

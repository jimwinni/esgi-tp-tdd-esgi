package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.ResourceNotFoundException;
import fr.esgi.cleancode.model.DrivingLicence;

import java.util.Optional;
import java.util.UUID;

public class DrivingLicencePenaltyService {
    private InMemoryDatabase database;

    public DrivingLicence reduceDrivingLicencePoints(UUID drivingLicenceId, int points) {
        Optional<DrivingLicence> optionalDrivingLicence = database.findById(drivingLicenceId);
        if (optionalDrivingLicence.isEmpty() || optionalDrivingLicence == null || optionalDrivingLicence.get().getAvailablePoints() - points < 0) {
            throw new ResourceNotFoundException("Driving Licence doesn't exist in database or Points can't be below 0");
        }
        DrivingLicence other = DrivingLicence.builder()
                .driverSocialSecurityNumber(optionalDrivingLicence.get().getDriverSocialSecurityNumber())
                .availablePoints(optionalDrivingLicence.get().getAvailablePoints() - points).build();
        return database.save(optionalDrivingLicence.get().getId(), other);
    }
}

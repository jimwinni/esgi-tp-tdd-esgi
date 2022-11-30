package fr.esgi.cleancode.Validator;


import fr.esgi.cleancode.exception.ResourceNotFoundException;
import fr.esgi.cleancode.model.DrivingLicence;

import java.util.Optional;

public interface TrafficOffenceValidator {
    static DrivingLicence checkDrivingLicenceValidity(Optional<DrivingLicence> optionalDrivingLicence, int points) {
        if (optionalDrivingLicence.isEmpty() || optionalDrivingLicence == null || optionalDrivingLicence.get().getAvailablePoints() - points < 0) {
            throw new ResourceNotFoundException("Driving Licence doesn't exist in database or Points can't be below 0");
        }
        DrivingLicence other = DrivingLicence.builder()
                .driverSocialSecurityNumber(optionalDrivingLicence.get().getDriverSocialSecurityNumber())
                .availablePoints(optionalDrivingLicence.get().getAvailablePoints() - points).build();
        return other;
    }
}

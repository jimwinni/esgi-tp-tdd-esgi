package fr.esgi.cleancode.service;

import fr.esgi.cleancode.Validator.TrafficOffenceValidator;
import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.ResourceNotFoundException;
import fr.esgi.cleancode.model.DrivingLicence;

import java.util.Optional;
import java.util.UUID;

public class DrivingLicencePenaltyService {
    private InMemoryDatabase database;

    public DrivingLicence reduceDrivingLicencePoints(UUID drivingLicenceId, int points) {
        Optional<DrivingLicence> optionalDrivingLicence = database.findById(drivingLicenceId);
        final var drivingLicence = TrafficOffenceValidator.checkDrivingLicenceValidity(optionalDrivingLicence,points);
        return database.save(optionalDrivingLicence.get().getId(), drivingLicence);
    }
}

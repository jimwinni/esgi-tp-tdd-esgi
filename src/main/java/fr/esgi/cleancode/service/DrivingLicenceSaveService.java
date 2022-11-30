package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import fr.esgi.cleancode.model.DrivingLicence;

public class DrivingLicenceSaveService {
    private InMemoryDatabase database;
    private DrivingLicenceIdGenerationService drivingLicenceIdGenerationService;

    public DrivingLicence saveNewDrivingLicence(String socialSecurityNumber) {
        if (socialSecurityNumber == null || socialSecurityNumber.isEmpty() || socialSecurityNumber.length() != 15 || !(socialSecurityNumber.chars().allMatch( Character::isDigit ))) {
            throw new InvalidDriverSocialSecurityNumberException("Social Security Number Invalid !");
        }
        final var drivingLicence = DrivingLicence.builder().driverSocialSecurityNumber(socialSecurityNumber).build();
        return database.save(drivingLicenceIdGenerationService.generateNewDrivingLicenceId(), drivingLicence);
    }
}

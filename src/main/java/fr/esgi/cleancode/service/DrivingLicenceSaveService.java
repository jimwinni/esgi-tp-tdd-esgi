package fr.esgi.cleancode.service;

import fr.esgi.cleancode.Validator.SocialSecurityNumberValidator;
import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import fr.esgi.cleancode.model.DrivingLicence;

public class DrivingLicenceSaveService {
    private InMemoryDatabase database;
    private DrivingLicenceIdGenerationService drivingLicenceIdGenerationService;

    public DrivingLicence saveNewDrivingLicence(String socialSecurityNumber) {
        final var drivingLicence = SocialSecurityNumberValidator.checkSocialSecurityNumberValidity(socialSecurityNumber);;
        return database.save(drivingLicenceIdGenerationService.generateNewDrivingLicenceId(), drivingLicence);
    }
}

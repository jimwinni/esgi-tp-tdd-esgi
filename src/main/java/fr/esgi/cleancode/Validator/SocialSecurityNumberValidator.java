package fr.esgi.cleancode.Validator;

import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import fr.esgi.cleancode.model.DrivingLicence;

public interface SocialSecurityNumberValidator {
    static DrivingLicence checkSocialSecurityNumberValidity(String socialSecurityNumber) {
        if (socialSecurityNumber == null || socialSecurityNumber.isEmpty() || socialSecurityNumber.length() != 15 || !(socialSecurityNumber.chars().allMatch( Character::isDigit ))) {
            throw new InvalidDriverSocialSecurityNumberException("Social Security Number Invalid !");
        }
        return DrivingLicence.builder().driverSocialSecurityNumber(socialSecurityNumber).build();
    }
}

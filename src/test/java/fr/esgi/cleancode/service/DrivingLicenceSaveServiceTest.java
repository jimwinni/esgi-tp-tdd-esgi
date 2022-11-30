package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import fr.esgi.cleancode.model.DrivingLicence;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class DrivingLicenceSaveServiceTest {
    @InjectMocks
    private DrivingLicenceSaveService service;
    @Mock
    private InMemoryDatabase database;
    @Mock
    private DrivingLicenceIdGenerationService drivingLicenceIdGenerationService;

    @ParameterizedTest
    @ValueSource(strings = {"199782154684654", "555482154684654"})
    void should_validate(String validSSNumber) {

        assertThat(validSSNumber)
                .isNotNull()
                .containsOnlyDigits()
                .hasSizeGreaterThanOrEqualTo(15)
                .hasSizeLessThanOrEqualTo(15);

        val drivingLicence = DrivingLicence.builder().driverSocialSecurityNumber(validSSNumber).build();

        Mockito.lenient().when( service.saveNewDrivingLicence(validSSNumber)).thenReturn(drivingLicence);

        assertThat(drivingLicence.getDriverSocialSecurityNumber())
                .isEqualTo(validSSNumber);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"lorem ipsum", "azertyuiop","09876543210987654321","098654"})
    void should_not_validate(String invalidSSNumber) {

        InvalidDriverSocialSecurityNumberException thrown = org.junit.jupiter.api.Assertions.assertThrows(InvalidDriverSocialSecurityNumberException.class, () -> service.saveNewDrivingLicence(invalidSSNumber),
                "InvalidDriverSocialSecurityNumberException error was expected");

        org.junit.jupiter.api.Assertions.assertEquals("Social Security Number Invalid !",thrown.getMessage());
    }
}

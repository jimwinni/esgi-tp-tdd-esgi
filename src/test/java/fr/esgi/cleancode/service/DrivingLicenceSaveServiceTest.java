package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.model.DrivingLicence;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.UUID;

public class DrivingLicenceSaveServiceTest {
    private InMemoryDatabase database = InMemoryDatabase.getInstance();
    private DrivingLicenceIdGenerationService drivingLicenceIdGenerationService = new DrivingLicenceIdGenerationService();

    @Test
    void should_validate() {
        final var id = drivingLicenceIdGenerationService.generateNewDrivingLicenceId();
        final var socialSecurityNumber = "123456789123459";

        assertThat(socialSecurityNumber)
                .isNotNull()
                .containsOnlyDigits()
                .hasSizeGreaterThanOrEqualTo(15)
                .hasSizeLessThanOrEqualTo(15);

        final var drivingLicence = DrivingLicence.builder().driverSocialSecurityNumber(socialSecurityNumber).build();

        Assertions.assertThatNoException().isThrownBy(() -> database.save(id, drivingLicence));

        assertThat(drivingLicence.getDriverSocialSecurityNumber())
                .isEqualTo(socialSecurityNumber);
    }
}

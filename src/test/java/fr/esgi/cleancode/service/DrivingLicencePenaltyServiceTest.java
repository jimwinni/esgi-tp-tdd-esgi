package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.ResourceNotFoundException;
import fr.esgi.cleancode.model.DrivingLicence;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class DrivingLicencePenaltyServiceTest {

    @InjectMocks
    private DrivingLicencePenaltyService service;
    @Mock
    private InMemoryDatabase database;
    @Captor
    private ArgumentCaptor<UUID> idCaptor;
    @Captor
    private ArgumentCaptor<DrivingLicence> drivingLicenceArgumentCaptor;

    @Test
    void should_reduce_points() {
        final var id = UUID.randomUUID();
        final var points = 5;
        final var drivingLicence = DrivingLicence.builder().id(id).build();

        assertThat(points)
                .isNotNull()
                .isGreaterThanOrEqualTo(0)
                .isLessThanOrEqualTo(12);

        Mockito.lenient().when( database.save(id,drivingLicence)).thenReturn(drivingLicence);
        Mockito.lenient().when( database.findById(id)).thenReturn(Optional.ofNullable(drivingLicence));

        final var newDrivingLicence = DrivingLicence.builder().id(drivingLicence.getId()).availablePoints(12 - points).build();

        service.reduceDrivingLicencePoints(id,points);

        Mockito.verify(database).save(idCaptor.capture(), drivingLicenceArgumentCaptor.capture());

        final var updatedDrivingLicenceId = idCaptor.getValue();
        final var updatedDrivingLicence = drivingLicenceArgumentCaptor.getValue();

        assertThat(updatedDrivingLicenceId)
                .isEqualTo(newDrivingLicence.getId());

        assertThat(updatedDrivingLicence.getAvailablePoints())
                .isEqualTo(newDrivingLicence.getAvailablePoints());
    }
    @Test
    void should_not_reduce_points() {
        final var id = UUID.randomUUID();
        final var points = 12;

        ResourceNotFoundException thrown = org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> service.reduceDrivingLicencePoints(id,points),
                "ResourceNotFoundException error was expected");

        org.junit.jupiter.api.Assertions.assertEquals("Driving Licence doesn't exist in database or Points can't be below 0",thrown.getMessage());
    }
}

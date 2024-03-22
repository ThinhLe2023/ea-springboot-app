package edu.miu.cs.cs544.Location;

import edu.miu.cs.cs544.domain.Location;
import edu.miu.cs.cs544.repository.LocationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LocationRepositoryTest {

    @Mock
    private LocationRepository locationRepository;

    @Test
    public void testSaveLocation() {
        // Create a sample location
        Location location = new Location();
        location.setId(1L);
        location.setName("Test Location");

        // Mock behavior of save method
        when(locationRepository.save(any(Location.class))).thenReturn(location);

        // Perform save operation
        Location savedLocation = locationRepository.save(location);

        // Verify that save method was called with the correct argument
        verify(locationRepository).save(eq(location));

        // Validate the saved location
        assertEquals(location.getId(), savedLocation.getId());
        assertEquals(location.getName(), savedLocation.getName());
    }

    @Test
    public void testFindLocationById() {
        // Sample location ID
        Long locationId = 1L;

        // Create a sample location
        Location location = new Location();
        location.setId(locationId);
        location.setName("Test Location");

        // Mock behavior of findById method
        when(locationRepository.findById(locationId)).thenReturn(Optional.of(location));

        // Perform findById operation
        Optional<Location> foundLocationOptional = locationRepository.findById(locationId);

        // Verify that findById method was called with the correct argument
        verify(locationRepository).findById(eq(locationId));

        // Validate the found location
        assertEquals(locationId, foundLocationOptional.get().getId());
        assertEquals(location.getName(), foundLocationOptional.get().getName());
    }

    @Test
    public void testDeleteLocation() {
        // Sample location ID
        Long locationId = 1L;

        // Perform deleteById operation
        locationRepository.deleteById(locationId);

        // Verify that deleteById method was called with the correct argument
        verify(locationRepository).deleteById(eq(locationId));
    }

}

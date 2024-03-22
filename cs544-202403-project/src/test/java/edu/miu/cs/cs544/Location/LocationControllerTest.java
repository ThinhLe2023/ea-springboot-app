package edu.miu.cs.cs544.Location;

import edu.miu.cs.cs544.controller.LocationController;
import edu.miu.cs.cs544.domain.Location;
import edu.miu.cs.cs544.service.LocationService;
import edu.miu.cs.cs544.service.contract.LocationPayload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Disabled
public class LocationControllerTest {

    @Mock
    private LocationService locationService;

    @InjectMocks
    private LocationController locationController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateLocation() {
        LocationPayload locationPayload = createLocationPayload();

        when(locationService.create(any(LocationPayload.class))).thenReturn(locationPayload);

        ResponseEntity<?> responseEntity = locationController.create(locationPayload);

        verify(locationService, times(1)).create(locationPayload);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateLocation() {
        Long locationId = 1L;
        LocationPayload locationPayload = createLocationPayload();

        when(locationService.update(eq(locationId), any(LocationPayload.class))).thenReturn(locationPayload);

        ResponseEntity<?> responseEntity = locationController.update(locationId, locationPayload);

        verify(locationService, times(1)).update(locationId, locationPayload);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteLocation() {
        Long locationId = 1L;

        ResponseEntity<?> responseEntity = locationController.delete(locationId);

        verify(locationService, times(1)).delete(locationId);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    private LocationPayload createLocationPayload() {
        LocationPayload locationPayload = new LocationPayload();
        locationPayload.setId(1L);
        locationPayload.setName("Test Location");
        // Set other attributes as needed

        return locationPayload;
    }

    private List<LocationPayload> createLocationPayloadList() {
        LocationPayload locationPayload1 = new LocationPayload();
        locationPayload1.setId(1L);
        locationPayload1.setName("Location 1");

        LocationPayload locationPayload2 = new LocationPayload();
        locationPayload2.setId(2L);
        locationPayload2.setName("Location 2");

        return Arrays.asList(locationPayload1, locationPayload2);
    }
}
/**
 * A unit test that tests only the InfoService class.
 */

package de.kurs.taskapi.service;

import de.kurs.taskapi.model.InfoResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InfoServiceTest {

    @Test
    void getApplicationInfoReturnsExpectedInfo() {
        // Create the object that will be tests.
        InfoService service = new InfoService();
        // Execute the method being tested.
        InfoResponse result = service.getApplicationInfo();

        //Verify the returned values are correct.
        assertEquals("DevOpsApi", result.application());
        assertEquals("1.0", result.version());
        assertEquals("Up and running!", result.status());
    }
}

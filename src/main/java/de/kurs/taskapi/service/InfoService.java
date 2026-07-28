package de.kurs.taskapi.service;

import de.kurs.taskapi.model.InfoResponse;
import org.springframework.stereotype.Service;

/** The service contains the business logic for the application.
 * 
 * It answers the question: "What information should the application return?"
 * 
 * Spring automatically creates one instance of this class because of the
 * @Service annotation. This instance is then injected into the InfoController.
 */

@Service
public class InfoService {

    /**
     * Creates an InfoResponse object with the application information.
     * 
     * Values are hardcoded here, but in a real application, 
     * they could be read from a configuration file or environment variables.
     * 
     * -Return Information about the application.
     */

    public InfoResponse getApplicationInfo() {
        return new InfoResponse(
            "DevOpsApi",
            "1.0",
            "Up and running!"
        );
    }
}

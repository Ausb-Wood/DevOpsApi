package de.kurs.taskapi.model;

/** 
 * This record represents the JSON response that will be sent back
 * to the client when the "api/info" is called.
 * 
 * Springboot automatically converts this object into JSON
 * before sending it over HTTP.
 * 
 * A record is a special Java type used for immutable data objects.
 * It automatically generates a constructor, getters(application(), version(), status()), 
 * equals(), hashCode(), and toString() methods.
 * 
 * Because this class only stores data and has no business logic,
 * a record is the best choice here.
 */

public record InfoResponse(
    String application,
    String version,
    String status
) {
}
package de.kurs.taskapi.service;

import org.springframework.stereotype.Service;

@Service
public class HelloService {
    public String getHello() {
        return "Hello DevOps";
    }
}

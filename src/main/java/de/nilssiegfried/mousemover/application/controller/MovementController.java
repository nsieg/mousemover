package de.nilssiegfried.mousemover.application.controller;

import de.nilssiegfried.mousemover.domain.entity.Movement;
import de.nilssiegfried.mousemover.domain.usecase.ConfigureMovement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movement")
@RequiredArgsConstructor
public class MovementController {

    private final ConfigureMovement configureMovement;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Movement status() {
        return configureMovement.get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void configure(@RequestBody Movement movement) {
        configureMovement.set(movement);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void enable() {
        configureMovement.start();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void disable() {
        configureMovement.stop();
    }
}

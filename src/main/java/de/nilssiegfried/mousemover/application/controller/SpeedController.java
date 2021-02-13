package de.nilssiegfried.mousemover.application.controller;

import de.nilssiegfried.mousemover.adapter.motor.PiMotorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/speed")
@RequiredArgsConstructor
public class SpeedController {

    private final PiMotorService piMotorService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public int status() {
        return piMotorService.getSpeed();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(@Valid @Min(0) @Max(1024) @RequestParam int runSpeed) {
        piMotorService.changeSpeed(runSpeed);
    }

}
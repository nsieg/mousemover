package de.nilssiegfried.mousemover.adapter.motor;

import com.pi4j.io.gpio.*;
import de.nilssiegfried.mousemover.adapter.port.MotorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PiMotorService implements MotorService {

    private GpioPinDigitalOutput PIN_ONE;
    private GpioPinDigitalOutput PIN_TWO;
    private GpioPinPwmOutput PIN_PWM;

    private final GpioController gpio = GpioFactory.getInstance();

    public PiMotorService() {
        PIN_ONE = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, PinState.LOW);
        PIN_TWO = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05, PinState.LOW);
        PIN_PWM = gpio.provisionPwmOutputPin(RaspiPin.GPIO_26, 250);

        PIN_PWM.setPwmRange(1024);
    }

    @Override
    public void changeSpeed(int runSpeed) {
        log.info("Changing speed to {}", runSpeed);
        PIN_PWM.setPwm(runSpeed);
    }

    @Override
    public int getSpeed() {
        return PIN_PWM.getPwm();
    }

    @Override
    public void start() {
        log.info("Starting motor by setting pin two to high");
        PIN_TWO.high();
    }

    @Override
    public void stop() {
        log.info("Stopping motor by setting pin two to low");
        PIN_TWO.low();
    }

}



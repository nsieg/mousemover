package de.nilssiegfried.mousemover.adapter.port;

public interface MotorService {

    void changeSpeed(int runSpeed);
    int getSpeed();
    void start();
    void stop();

}

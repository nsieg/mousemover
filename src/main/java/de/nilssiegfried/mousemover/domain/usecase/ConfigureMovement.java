package de.nilssiegfried.mousemover.domain.usecase;

import de.nilssiegfried.mousemover.adapter.port.MotorService;
import de.nilssiegfried.mousemover.domain.entity.Movement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Component
public class ConfigureMovement {

    private final TaskScheduler taskScheduler;
    private final MotorService motorService;

    private Movement movement;
    private List<ScheduledFuture<?>> scheduledFutures = new LinkedList<>();

    @Autowired
    public ConfigureMovement(TaskScheduler taskScheduler, MotorService motorService) {
        this.taskScheduler = taskScheduler;
        this.motorService = motorService;

        this.movement = new Movement(false, 10, 180);
    }

    public Movement get() {
        return movement;
    }

    public void set(Movement movement) {
        this.movement = movement;
        stopCurrentMovement();
        startMovement(movement);
    }

    public void start() {
        if(!this.movement.isEnabled()) {
            set(new Movement(true, this.movement.getSecondsInterval(), this.movement.getSecondsDelay()));
        }
    }

    public void stop() {
        if(this.movement.isEnabled()) {
            set(new Movement(false, this.movement.getSecondsInterval(), this.movement.getSecondsDelay()));
        }
    }

    private void stopCurrentMovement() {
        log.info("Stopping current movement. Found {} futures", scheduledFutures.size());
        ListIterator<ScheduledFuture<?>> iter = scheduledFutures.listIterator();
        while(iter.hasNext()) {
            ScheduledFuture<?> future = iter.next();
            future.cancel(false);
            while (!future.isDone()) { try { Thread.sleep(100); } catch (Exception e) {log.error("",e);} }
            iter.remove();
        }
    }

    private void startMovement(Movement movement) {
        log.info("Starting new movement {}", movement);
        if(movement.isEnabled()) {
            ScheduledFuture<?> future = taskScheduler.scheduleWithFixedDelay(() -> {
                motorService.start();
                taskScheduler.schedule(motorService::stop, Instant.now().plusSeconds(movement.getSecondsInterval()));
            }, Duration.ofSeconds(movement.getSecondsDelay()));
            scheduledFutures.add(future);
        }
    }

}

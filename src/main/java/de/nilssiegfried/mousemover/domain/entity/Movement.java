package de.nilssiegfried.mousemover.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Movement {

    private boolean enabled;
    private int secondsInterval;
    private int secondsDelay;

}

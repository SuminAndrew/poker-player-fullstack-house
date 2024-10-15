package org.leanpoker.player.protocol;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum Status {
    ACTIVE("active"),
    FOLDED("folded"),
    OUT("out");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    @JsonCreator
    public static Status fromString(String status) {
        for (Status s : Status.values()) {
            if (s.status.equalsIgnoreCase(status)) {
                return s;
            }
        }
        return null;
    }
}

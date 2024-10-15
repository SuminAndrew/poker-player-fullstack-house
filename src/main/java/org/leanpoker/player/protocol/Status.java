package org.leanpoker.player.protocol;

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
}

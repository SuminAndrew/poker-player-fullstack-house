package org.leanpoker.player.protocol;

import lombok.Getter;

@Getter
public enum Action {

    BET_REQUEST("bet_request"),
    SHOWDOWN("showdown"),
    VERSION("version"),
    ;

    private final String action;

    Action(String action) {
        this.action = action;
    }
}

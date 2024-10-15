package org.leanpoker.player.protocol;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum Action {
    BET_REQUEST("bet_request"),
    SHOWDOWN("showdown"),
    VERSION("version"),
    ;

    private final String action;

    Action(String action) {
        this.action = action;
    }

    @JsonCreator
    public static Action fromString(String action) {
        for (Action a : Action.values()) {
            if (a.action.equalsIgnoreCase(action)) {
                return a;
            }
        }
        return null;
    }
}

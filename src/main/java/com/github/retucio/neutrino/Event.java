package com.github.retucio.neutrino;

public class Event {

    private boolean cancelled = false;
    private Stage stage = Stage.PRE;

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancel() {
        cancelled = true;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public enum Stage {
        PRE,
        POST;
    }
}
package com.dmd.physical_iot.physical;

public class PiModel {
    private final long id;
    private final String content;

    public PiModel(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}

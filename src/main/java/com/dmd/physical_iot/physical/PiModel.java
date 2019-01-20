package com.dmd.physical_iot.physical;

// It looks like this file is never used; why does it exist? Why is it in the project?
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
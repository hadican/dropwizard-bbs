package com.sony.bbs.dropwizard.view;

import io.dropwizard.views.View;

import java.util.List;

import com.sony.bbs.dropwizard.core.Presentation;

public class PresentationView extends View {
	
    private final List<Presentation> presentations;

    public PresentationView(List<Presentation> presentations) {
        super("presentations.ftl");
        this.presentations = presentations;
    }

    public List<Presentation> getPresentations() {
        return presentations;
    }
}

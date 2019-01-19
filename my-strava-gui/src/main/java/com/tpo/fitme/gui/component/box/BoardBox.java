package com.tpo.fitme.gui.component.box;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;

/**
 * @author Tiberiu
 * @since 2019-01-19
 */
public class BoardBox extends CssLayout {

    private CssLayout mainDiv = new CssLayout();

    public BoardBox(Component component) {
        addStyleName("board-box-wrapper");
        setSizeFull();
        addComponent(mainDiv);
        mainDiv.addStyleName("board-box");
        mainDiv.setSizeFull();
        CssLayout inner = new CssLayout();
        inner.setSizeFull();
        inner.addStyleName("board-box-inner");
        inner.addComponent(component);
        mainDiv.addComponent(inner);
    }

    public BoardBox(Component component, String styleName) {
        this(component);
        mainDiv.addStyleName(styleName);
    }

    public void setNeedsAttention(boolean needsAttention) {
        mainDiv.setStyleName("board-box-needs-attention", needsAttention);
    }
}
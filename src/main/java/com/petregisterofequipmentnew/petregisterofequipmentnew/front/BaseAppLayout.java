package com.petregisterofequipmentnew.petregisterofequipmentnew.front;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class BaseAppLayout extends AppLayout {

    public BaseAppLayout() {

        H1 h1 = new H1("RegEquipment");
        h1.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("left", "var(--lumo-space-l)").set("margin", "0")
                .set("position", "absolute");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.getElement();

        addToNavbar(h1, horizontalLayout);

    }
}

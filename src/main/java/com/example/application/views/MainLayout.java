package com.example.application.views;

import com.example.application.security.SecurityService;
import com.example.application.views.list.ListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {
    private final SecurityService securityService;
    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        createDrawer();
    }

    private void createDrawer() {
        RouterLink routerLink = new RouterLink("List", ListView.class);
        routerLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink routerLink1 = new RouterLink("Dashboard", DashboardView.class);
        routerLink1.setHighlightCondition(HighlightConditions.sameLocation());

        VerticalLayout verticalLayout = new VerticalLayout(routerLink, routerLink1);

        addToDrawer(verticalLayout);
    }

    private void createHeader() {
        H1 logo = new H1("Vaadin Header");
        logo.addClassNames("text-l", "m-m");

        Button button = new Button("Log out", event -> securityService.logOut());


        HorizontalLayout horizontalLayout = new HorizontalLayout(new DrawerToggle(), logo, button);

        horizontalLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        horizontalLayout.expand(logo);
        horizontalLayout.setWidthFull();
        horizontalLayout.addClassNames("py-0", "px-m");

        addToNavbar(horizontalLayout);
    }
}

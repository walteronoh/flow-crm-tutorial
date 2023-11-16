package com.example.application.views.list;

import com.example.application.data.entity.Contact;
import com.example.application.services.CrmService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Collections;

@PageTitle("Contacts")
@Route(value = "")
public class ListView extends VerticalLayout {
    //we need a grid and a text field
    Grid<Contact> grid = new Grid<>(Contact.class);
    TextField filterText = new TextField();

    ContactForm contactForm;

    CrmService crmService;
    public ListView(CrmService crmService) {
        this.crmService = crmService;
        addClassName("list-view");
        setSizeFull();

        configureGrid();
        configureForm();

        add(
                getToolBar(),
                getContent()
        );

        updateList();

        closeEditor();
    }

    private void closeEditor() {
        contactForm.setContact(null);
        contactForm.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(crmService.findAllContacts(filterText.getValue()));
    }

    private Component getContent() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(grid, contactForm);
        horizontalLayout.setFlexGrow(2, grid);
        horizontalLayout.setFlexGrow(1, contactForm);

        horizontalLayout.setClassName("content");
        horizontalLayout.setSizeFull();

        return horizontalLayout;
    }
    private void configureForm() {
        contactForm = new ContactForm(crmService.findAllCompanies(), crmService.findAllStatuses());
        contactForm.setWidth("25em");
    }

    private Component getToolBar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add contact");
        HorizontalLayout toolBar = new HorizontalLayout(filterText, addContactButton);
        toolBar.addClassName("toolBar");
        return toolBar;
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        setSizeFull();
        grid.setColumns("firstName", "lastName", "email");
        grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Status");
        grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editContact(e.getValue()));
    }

    private void editContact(Contact contact) {
        if(contact == null) {
            closeEditor();
        } else {
            contactForm.setContact(contact);
            contactForm.setVisible(true);
            addClassName("editing");
        }
    }

}

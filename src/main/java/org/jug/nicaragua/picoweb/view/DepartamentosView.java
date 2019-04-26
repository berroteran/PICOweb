package org.jug.nicaragua.picoweb.view;

import java.io.Serializable;
import org.jug.nicaragua.picoweb.dao.BussinessException;
import org.jug.nicaragua.picoweb.dao.DepartamentoDAOImpl;
import org.jug.nicaragua.picoweb.modelo.Departamento;
import org.jug.nicaragua.picoweb.view.event.PICOWEBEventBus;
import org.jug.nicaragua.picoweb.view.forms.DepartamentosForm;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public final class DepartamentosView extends VerticalLayout implements View, Serializable {

  private static final long serialVersionUID = 1L;

  private final Grid<Departamento> grid;

  private Button createReport;

  public DepartamentosView() {
    setSizeFull();
    addStyleName("transactions");
    setMargin(false);
    setSpacing(false);
    PICOWEBEventBus.register(this);

    addComponent(buildToolbar());

    grid = buildGrid();

    addComponent(grid);
    setExpandRatio(grid, 1);
  }


  private Component buildToolbar() {
    HorizontalLayout header = new HorizontalLayout();
    header.addStyleName("viewheader");
    Responsive.makeResponsive(header);

    Label title = new Label("Departamentos de la Empresa");
    title.setSizeUndefined();
    title.addStyleName(ValoTheme.LABEL_H1);
    title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
    header.addComponent(title);

    createReport = buildCreateProceso();
    HorizontalLayout tools = new HorizontalLayout(buildFilter(), createReport);
    tools.addStyleName("toolbar");
    header.addComponent(tools);

    return header;
  }

  private Button buildCreateProceso() {
    final Button createReport = new Button("Nuevo Departamento");
    createReport.setStyleName(ValoTheme.BUTTON_PRIMARY);
    createReport.addClickListener(event -> createNewProceso());

    return createReport;
  }


  private Component buildFilter() {
    final TextField filter = new TextField();

    // TODO use new filtering API
    filter.addValueChangeListener(event -> {

      // Collection<Consultorio> consultorios =
      // DashboardUI.getDataProvider().getRecentTransactions(200).stream().filter(consultorios -> {
      // filterValue = filter.getValue().trim().toLowerCase();
      // return passesFilter(consultorios.getCountry()) || passesFilter(consultorios.getTitle()) ||
      // passesFilter(consultorios.getCity());
      // }).collect(Collectors.toList());
      //
      // ListDataProvider<Consultorio> dataProvider =
      // com.vaadin.data.provider.DataProvider.ofCollection(consultorios);
      // dataProvider.addSortComparator(Comparator.comparing(Consultorio::getTime).reversed()::compare);
      // grid.setDataProvider(dataProvider);
    });

    filter.setPlaceholder("Buscar por");
    filter.setIcon(FontAwesome.SEARCH);
    filter.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
    filter.addShortcutListener(new ShortcutListener("Clear", KeyCode.ESCAPE, null) {
      @Override
      public void handleAction(final Object sender, final Object target) {
        filter.setValue("");
      }
    });
    return filter;
  }

  private Grid<Departamento> buildGrid() {
    final Grid<Departamento> grid = new Grid<>();
    grid.setSelectionMode(SelectionMode.SINGLE);
    grid.setSizeFull();
    grid.setFooterVisible(true);
    grid.setColumnReorderingAllowed(true);
    grid.addColumn(Departamento::getDepartamento).setCaption("Departamento");
    grid.addColumn(Departamento::getDescripcion).setCaption("Descripcion");
    grid.addColumn(Departamento::getCreadopor).setCaption("Jefe");


    grid.setDataProvider(getDatos());

    grid.addComponentColumn(Funcion -> new Button(VaadinIcons.LOCK, e -> borrar(Funcion))); // borrr una fuincion del grid
    // eventos.
    grid.addItemClickListener(listener -> {
      if (listener.getMouseEventDetails().isDoubleClick())
        DepartamentosForm.open(listener.getItem());
    });
    return grid;
  }


  private DataProvider<Departamento, ?> getDatos() {

    ListDataProvider<Departamento> dataProvider = null;
    try {
      dataProvider = com.vaadin.data.provider.DataProvider.ofCollection((new DepartamentoDAOImpl()).findAll());
    } catch (BussinessException e1) {
      e1.printStackTrace();
      Notification.show(e1.getMessage(), Type.ERROR_MESSAGE);
    }
    return dataProvider;
  }

  private Object borrar(Departamento puesto) {
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  public void enter(final ViewChangeEvent event) {}

  private void createNewProceso() {
    
  }


}

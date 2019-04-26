package org.jug.nicaragua.picoweb.view;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedHashSet;
import java.util.Set;
import org.jug.nicaragua.picoweb.dao.BussinessException;
import org.jug.nicaragua.picoweb.dao.EvalCapacitacionDAOImpl;
import org.jug.nicaragua.picoweb.modelo.EvalCapacitacion;
import org.jug.nicaragua.picoweb.view.event.PICOWEBEvent.BrowserResizeEvent;
import org.jug.nicaragua.picoweb.view.event.PICOWEBEventBus;
import org.jug.nicaragua.picoweb.view.forms.EvalCapacitacionForm;
import com.google.common.eventbus.Subscribe;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.SingleSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public final class EvaluacionesVIew extends VerticalLayout implements View {

  private final Grid<EvalCapacitacion> grid;
  private SingleSelect<EvalCapacitacion> singleSelect;
  private Button createEvaluacion;
  private String filterValue = "";
  private static final DateFormat DATEFORMAT = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
  private static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#.##");
  private static final Set<Column<EvalCapacitacion, ?>> collapsibleColumns = new LinkedHashSet<>();

  public EvaluacionesVIew() {
    setSizeFull();
    addStyleName("transactions");
    setMargin(false);
    setSpacing(false);
    PICOWEBEventBus.register(this);

    addComponent(buildToolbar());

    grid = buildGrid();
    singleSelect = grid.asSingleSelect();
    addComponent(grid);
    setExpandRatio(grid, 1);
  }

  @Override
  public void detach() {
    super.detach();
    // A new instance of TransactionsView is created every time it's
    // navigated to so we'll need to clean up references to it on detach.
    PICOWEBEventBus.unregister(this);
  }

  private Component buildToolbar() {
    HorizontalLayout header = new HorizontalLayout();
    header.addStyleName("viewheader");
    Responsive.makeResponsive(header);

    Label title = new Label("Evaluaciones de Capacitacion");
    title.setSizeUndefined();
    title.addStyleName(ValoTheme.LABEL_H1);
    title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
    header.addComponent(title);

    createEvaluacion = buildCreateReport();
    HorizontalLayout tools = new HorizontalLayout( buildFiltro(), createEvaluacion );
    tools.addStyleName("toolbar");
    header.addComponent(tools);

    return header;
  }

  private Button buildCreateReport() {
    final Button createReport = new Button("Create Ev. Capacitacion");
    createReport.setStyleName(ValoTheme.BUTTON_PRIMARY);
    createReport.addClickListener(event -> createNewEval());
    return createReport;
  }

  private Component buildFiltro() {
    final TextField filter = new TextField();



    filter.setPlaceholder("Filter");
    filter.setIcon(FontAwesome.SEARCH);
    filter.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
    filter.addShortcutListener(new ShortcutListener("Limpiar", KeyCode.ESCAPE, null) {
      @Override
      public void handleAction(final Object sender, final Object target) {
        filter.setValue("");
      }
    });
    return filter;
  }

  private Grid<EvalCapacitacion> buildGrid() {
    final Grid<EvalCapacitacion> grid = new Grid<EvalCapacitacion>();
    grid.setSelectionMode(SelectionMode.SINGLE);
    grid.setSizeFull();


    collapsibleColumns.add(grid.addColumn(EvalCapacitacion::getCodCapacitacion).setCaption("Codigo"));
    collapsibleColumns.add(grid.addColumn(EvalCapacitacion::getFecha).setCaption("Fecha"));
    collapsibleColumns.add(grid.addColumn(EvalCapacitacion::getEvaluado).setCaption("Evaluado"));
    collapsibleColumns.add(grid.addColumn(EvalCapacitacion::getEvaluador).setCaption("Evaluador"));
    collapsibleColumns.add(grid.addColumn(EvalCapacitacion::getEstatus).setCaption("Estado"));
    
    grid.setColumnReorderingAllowed(true);


    grid.setDataProvider(getDatos());

    // TODO either add these to grid or do it with style generators here
    // grid.setColumnAlignment("seats", Align.RIGHT);
    // grid.setColumnAlignment("price", Align.RIGHT);

    // TODO add when footers implemented in v8
    grid.setFooterVisible(true);
    // grid.setColumnFooter("time", "Total");
    // grid.setColumnFooter("price", "$" + DECIMALFORMAT
    // .format(DashboardUI.getDataProvider().getTotalSum()));


    grid.addComponentColumn(Factura -> new Button(VaadinIcons.LOCK, e -> borrar(Factura)));
    grid.addComponentColumn(Factura -> new Button(VaadinIcons.TRASH, e -> borrar(Factura)));
    grid.addComponentColumn(Factura -> new Button(VaadinIcons.TREE_TABLE, e -> borrar(Factura)));
    return grid;
  }

  private Object borrar(EvalCapacitacion factura) {
    // TODO Auto-generated method stub
    return null;
  }

  private boolean defaultColumnsVisible() {
    boolean result = true;
    for (Column<EvalCapacitacion, ?> column : collapsibleColumns) {
      if (column.isHidden() == Page.getCurrent().getBrowserWindowWidth() < 800) {
        result = false;
      }
    }
    return result;
  }

  @Subscribe
  public void browserResized(final BrowserResizeEvent event) {
    // Some columns are collapsed when browser window width gets small
    // enough to make the table fit better.

    if (defaultColumnsVisible()) {
      for (Column<EvalCapacitacion, ?> column : collapsibleColumns) {
        column.setHidden(Page.getCurrent().getBrowserWindowWidth() < 800);
      }
    }
  }

  void createNewEval() {
    //EvalCapacitacionForm.open();
    EvalCapacitacionForm.open();
  }



  private DataProvider<EvalCapacitacion, ?> getDatos() {

    ListDataProvider<EvalCapacitacion> dataProvider = null;
    try {
      dataProvider = com.vaadin.data.provider.DataProvider.ofCollection((new EvalCapacitacionDAOImpl() ).findAll());
    } catch (BussinessException e1) {
      e1.printStackTrace();
      Notification.show(e1.getMessage(), Type.ERROR_MESSAGE);
    }
    return dataProvider;
  }


  private boolean passesFilter(String subject) {
    if (subject == null) {
      return false;
    }
    return subject.trim().toLowerCase().contains(filterValue);
  }

  @Override
  public void enter(final ViewChangeEvent event) {}
}

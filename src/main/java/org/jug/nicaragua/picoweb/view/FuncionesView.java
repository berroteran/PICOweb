package org.jug.nicaragua.picoweb.view;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.jug.nicaragua.picoweb.PICOWEBUI;
import org.jug.nicaragua.picoweb.domain.Transaction;
import org.jug.nicaragua.picoweb.view.event.PICOWEBEvent.BrowserResizeEvent;
import org.jug.nicaragua.picoweb.view.event.PICOWEBEventBus;
import com.google.common.eventbus.Subscribe;
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
import com.vaadin.ui.SingleSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public final class FuncionesView extends VerticalLayout implements View {

  private final Grid<Transaction> grid;
  private SingleSelect<Transaction> singleSelect;
  private Button createReport;
  private String filterValue = "";
  private static final DateFormat DATEFORMAT = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
  private static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#.##");
  private static final Set<Column<Transaction, ?>> collapsibleColumns = new LinkedHashSet<>();

  public FuncionesView() {
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

    Label title = new Label("Cargos");
    title.setSizeUndefined();
    title.addStyleName(ValoTheme.LABEL_H1);
    title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
    header.addComponent(title);

    createReport = buildCreateReport();
    HorizontalLayout tools = new HorizontalLayout(buildFilter(), createReport);
    tools.addStyleName("toolbar");
    header.addComponent(tools);

    return header;
  }

  private Button buildCreateReport() {
    final Button createReport = new Button("Nuevo Cargo");
    createReport.setStyleName(ValoTheme.BUTTON_PRIMARY);
    createReport.setDescription("Crear un nuevo cargo.");
    createReport.addClickListener(event -> createNewReportFromSelection());
    return createReport;
  }

  private Component buildFilter() {
    final TextField filter = new TextField();

    // TODO use new filtering API
    filter.addValueChangeListener(event -> {

      Collection<Transaction> transactions = PICOWEBUI.getDataProvider().getRecentTransactions(200).stream().filter(transaction -> {
        filterValue = filter.getValue().trim().toLowerCase();
        return passesFilter(transaction.getCountry()) || passesFilter(transaction.getTitle()) || passesFilter(transaction.getCity());
      }).collect(Collectors.toList());

      ListDataProvider<Transaction> dataProvider = com.vaadin.data.provider.DataProvider.ofCollection(transactions);
      dataProvider.addSortComparator(Comparator.comparing(Transaction::getTime).reversed()::compare);
      grid.setDataProvider(dataProvider);
    });

    filter.setPlaceholder("Filter");
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

  private Grid<Transaction> buildGrid() {
    final Grid<Transaction> grid = new Grid<>();
    grid.setSelectionMode(SelectionMode.SINGLE);
    grid.setSizeFull();

    Column<Transaction, String> time = grid.addColumn(transaction -> DATEFORMAT.format(transaction.getTime()));
    time.setId("Time").setHidable(true);

    collapsibleColumns.add(grid.addColumn(Transaction::getCountry).setId("Numero"));
    collapsibleColumns.add(grid.addColumn(Transaction::getCity).setId("Fecha"));
    collapsibleColumns.add(grid.addColumn(Transaction::getTheater).setId("Paciente"));
    collapsibleColumns.add(grid.addColumn(Transaction::getRoom).setId("Estado"));
    collapsibleColumns.add(grid.addColumn(Transaction::getRoom).setId("FechaVencimiento"));
    collapsibleColumns.add(grid.addColumn(Transaction::getSeats, new NumberRenderer()).setId("Dias"));
    grid.addColumn(transaction -> "$" + DECIMALFORMAT.format(transaction.getPrice())).setId("Monto").setHidable(true);

    grid.setColumnReorderingAllowed(true);

    ListDataProvider<Transaction> dataProvider = com.vaadin.data.provider.DataProvider.ofCollection(PICOWEBUI.getDataProvider().getRecentTransactions(200));
    dataProvider.addSortComparator(Comparator.comparing(Transaction::getTime).reversed()::compare);
    grid.setDataProvider(dataProvider);

    // TODO either add these to grid or do it with style generators here
    // grid.setColumnAlignment("seats", Align.RIGHT);
    // grid.setColumnAlignment("price", Align.RIGHT);

    // TODO add when footers implemented in v8
    // grid.setFooterVisible(true);
    // grid.setColumnFooter("time", "Total");
    // grid.setColumnFooter("price", "$" + DECIMALFORMAT
    // .format(DashboardUI.getDataProvider().getTotalSum()));

    // TODO add this functionality to grid?
    // grid.addActionHandler(new TransactionsActionHandler());

    
    grid.addComponentColumn ( Factura -> new Button( VaadinIcons.LOCK, e -> borrar(Factura)) );
    return grid;
  }

  private Object borrar(Transaction factura) {
    // TODO Auto-generated method stub
    return null;
  }

  private boolean defaultColumnsVisible() {
    boolean result = true;
    for (Column<Transaction, ?> column : collapsibleColumns) {
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
      for (Column<Transaction, ?> column : collapsibleColumns) {
        column.setHidden(Page.getCurrent().getBrowserWindowWidth() < 800);
      }
    }
  }

  void createNewReportFromSelection() {
    //PuestoDescripcionForm.nuevo();
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

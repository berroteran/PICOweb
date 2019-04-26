package org.jug.nicaragua.picoweb.view;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.jug.nicaragua.picoweb.PICOWEBUI;
import org.jug.nicaragua.picoweb.modelo.Usuario;
import org.jug.nicaragua.picoweb.view.event.PICOWEBEvent.BrowserResizeEvent;
import org.jug.nicaragua.picoweb.view.event.PICOWEBEventBus;
import org.jug.nicaragua.picoweb.view.forms.UsuarioForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.eventbus.Subscribe;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.SingleSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;


public final class UsuariosView extends VerticalLayout implements View {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private static final Logger logger = LoggerFactory.getLogger(UsuariosView.class);

  private final Grid<Usuario> grid;
  private SingleSelect<Usuario> singleSelect;
  private Button createUser;
  private String filterValue = "";
  private static final DateFormat DATEFORMAT = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
  private static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#.##");


  public UsuariosView() {
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

    Label title = new Label("Usuarios");
    title.setSizeUndefined();
    title.addStyleName(ValoTheme.LABEL_H1);
    title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
    header.addComponent(title);

    createUser = buildCreateUsuario();
    HorizontalLayout tools = new HorizontalLayout(buildFilter(), createUser);
    tools.addStyleName("toolbar");
    header.addComponent(tools);

    return header;
  }

  private Button buildCreateUsuario() {
    final Button createUsuario = new Button("Nuevo Usuario");
    createUsuario.setIcon(FontAwesome.USER);
    createUsuario.setStyleName(ValoTheme.BUTTON_PRIMARY);
    createUsuario.addClickListener(event -> createNewUsuario());
    return createUsuario;
  }

  private Component buildFilter() {
    final TextField filter = new TextField();

    // TODO use new filtering API
    filter.addValueChangeListener(event -> {
      //
      // Collection<Usuario> usuarios =
      // DashboardUI.getDataProvider().getRecentTransactions(200).stream().filter(usuarios -> {
      // filterValue = filter.getValue().trim().toLowerCase();
      // return passesFilter(usuarios.getCountry()) || passesFilter(usuarios.getTitle()) ||
      // passesFilter(usuarios.getCity());
      // }).collect(Collectors.toList());

      // ListDataProvider<Usuario> dataProvider =
      // com.vaadin.data.provider.DataProvider.ofCollection(usuarios);

      // grid.setDataProvider(dataProvider);
    });

    filter.setPlaceholder("Filtro");
    filter.setIcon(FontAwesome.SEARCH);
    filter.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
    filter.addShortcutListener(new ShortcutListener("Clear", KeyCode.ESCAPE, null) {
      /**
       * 
       */
      private static final long serialVersionUID = 1L;

      @Override
      public void handleAction(final Object sender, final Object target) {
        filter.setValue("");
      }
    });
    return filter;
  }

  private Grid<Usuario> buildGrid() {
    Grid<Usuario> grid = new Grid<>();
    grid.setSelectionMode(SelectionMode.SINGLE);
    grid.setSizeFull();
    try {
      // recupera Datos
      ListDataProvider<Usuario> dataProvider = com.vaadin.data.provider.DataProvider.ofCollection(PICOWEBUI.getDataProvider().getAllUsuarios());
      grid.setDataProvider(dataProvider);

      // configurando
      grid.addColumn(Usuario::getLogin).setId("Login").setCaption("Login");
      grid.addColumn(Usuario::getNombreCompleto).setId("Nombre").setCaption("Nombre ");
      grid.addColumn(Usuario::getRol).setId("Rol").setCaption("ROL");
      grid.addColumn(Usuario::getEstado).setId("Estado").setCaption("Estado");

      grid.addColumn(usuario -> formateaFecha(usuario.getFechaUltAcceso())).setId("Ultimo Acceso").setCaption("Ultimo Acceso");

      // eventos
      // grid.addSelectionListener(event -> createUser.setEnabled(!singleSelect.isEmpty()));
      grid.addItemClickListener(listener -> {
        if (listener.getMouseEventDetails().isDoubleClick())
            openUser(listener.getItem());
      });

      // acciones
      // grid.addComponentColumn (this:: borrarUser );
      // grid.addComponentColumn ( Usuario -> new Button( VaadinIcons.LOCK, e -> bloquearUser(Usuario)) );
      // grid.addComponentColumn ( Usuario -> new Button( VaadinIcons.PASSWORD, e ->
      // bloquearUser(Usuario)) );
      grid.addComponentColumn(this::acciones);

      // agregando Conextmenu
    } catch (Exception e) {
      e.printStackTrace();
    }


    return grid;
  }

  private void openUser(Usuario item) {
    Notification.show("usuario seleccionado" + item.getNombreCompleto());
    UsuarioForm.open(item);
  }

  private Component acciones(Usuario usuario) {
    HorizontalLayout tools = new HorizontalLayout();
    tools.addComponents(new Button(VaadinIcons.TRASH, e -> borrarUser(usuario)), new Button(VaadinIcons.LOCK, e -> bloquearUser(usuario)), new Button(VaadinIcons.PASSWORD, e -> bloquearUser(usuario)));
    return tools;
  }

  private Object bloquearUser(Usuario usuario) {
    // TODO Auto-generated method stub
    return null;
  }

  private String formateaFecha(Date fechaUltAcceso) {
    String cadena = "";
    try {
      cadena = DATEFORMAT.format(fechaUltAcceso);
    } catch (Exception e) {
    }
    return cadena;
  }

  @SuppressWarnings("serial")
  private Button borrarUser(Usuario usuario) {
    Button del = new Button(VaadinIcons.TRASH);
    del.addStyleName(ValoTheme.BUTTON_SMALL);
    del.addClickListener(new ClickListener() {
      @Override
      public void buttonClick(ClickEvent event) {
        // TODO Auto-generated method stub
      }
    });
    return del;
  }


  @Subscribe
  public void browserResized(final BrowserResizeEvent event) {

  }

  void createNewUsuario() {
    UsuarioForm.openNuevo();
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

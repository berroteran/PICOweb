package org.jug.nicaragua.picoweb.view.forms;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.hibernate.Session;
import org.jug.nicaragua.picoweb.PICOWEBUI;
import org.jug.nicaragua.picoweb.dao.HibernateUtil;
import org.jug.nicaragua.picoweb.modelo.Rol;
import org.jug.nicaragua.picoweb.modelo.Usuario;
import org.jug.nicaragua.picoweb.view.event.PICOWEBEvent.CloseOpenWindowsEvent;
import org.jug.nicaragua.picoweb.view.event.PICOWEBEvent.ProfileUpdatedEvent;
import org.jug.nicaragua.picoweb.view.event.PICOWEBEventBus;
import com.vaadin.annotations.PropertyId;
import com.vaadin.data.Binder;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.data.BindingValidationStatus;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;


public class UsuarioForm extends Window {

  private static final long serialVersionUID = 1L;

  private final static Logger log = Logger.getLogger( UsuarioForm.class.getName());

  public static final String ID = "usuarioFormWindows";

  // binder
  Binder<Usuario> binder = new Binder<Usuario>();
  Usuario user = null;

  @PropertyId("firstName")
  private TextField campoLogin  = new TextField("Login");;
  @PropertyId("firstName")
  private TextField campoNombres;
  @PropertyId("lastName")
  private TextField campoApellido;
  @PropertyId("title")
  private ComboBox<String> titleField;
  private ComboBox<Rol> cboRoles;
  @PropertyId("male")
  private RadioButtonGroup<Boolean> sexField;
  @PropertyId("email")
  private TextField emailField;
  @PropertyId("location")
  private TextField locationField;
  @PropertyId("phone")
  private TextField phoneField;
  @PropertyId("newsletterSubscription")
  private OptionalSelect<Integer> newsletterField;
  @PropertyId("website")
  private TextField websiteField;
  @PropertyId("bio")
  private TextArea bioField;

  private UsuarioForm(Usuario usuario) {
    addStyleName("profile-window");
    setId(ID);
    Responsive.makeResponsive(this);
    setModal(true);
    addCloseShortcut(KeyCode.ESCAPE, null);
    setResizable(false);
    setClosable(true);
    setCaption("Usuario");
    setIcon(FontAwesome.USER);
    setHeight(92.0f, Unit.PERCENTAGE);

    VerticalLayout content = new VerticalLayout();
    content.setSizeFull();
    content.setMargin(new MarginInfo(true, false, false, false)); // margen TODO
    content.setSpacing(false);
    // cargando datos
    if (usuario == null) {
      log.log(java.util.logging.Level.INFO, "Creando nuevo usuario");
      user = new Usuario();
      campoLogin.setEnabled(true);
    } else {
      user = usuario;
      campoLogin.setEnabled(false);
    }
    binder.setBean(user);
    
    Component formUsuario = buildProfileTab();
    //tbs
    TabSheet tabDetalles = new TabSheet();
    tabDetalles.setSizeFull();
    tabDetalles.hideTabs(true);
    tabDetalles.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
    tabDetalles.addStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
    tabDetalles.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
    tabDetalles.addComponent(formUsuario);


    content.addComponents(tabDetalles, buildFooter());
    content.setExpandRatio(tabDetalles, 1f);
    // content.setExpandRatio(detailsWrapper, 1f);
    // FIN
    setContent(content);
  }


  private Component buildProfileTab() {
    HorizontalLayout root = new HorizontalLayout();
    root.setWidth(100.0f, Unit.PERCENTAGE);
    root.setMargin(true);
    root.setResponsive(true);
    root.addStyleName("profile-form");
    

    // Foto
    VerticalLayout pic = new VerticalLayout();
    pic.setSizeUndefined();
    pic.setSpacing(true);
    Image profilePic = new Image(null, new ThemeResource("img/profile-pic-300px.jpg"));
    profilePic.setWidth(100.0f, Unit.PIXELS);

    Button upload = new Button("Cambiar ", new ClickListener() {
      /**
       * 
       */
      private static final long serialVersionUID = 1L;

      @Override
      public void buttonClick(ClickEvent event) {
        Notification.show("Not implementedo in this demo");
      }
    });
    upload.addStyleName(ValoTheme.BUTTON_TINY);
    pic.addComponents(profilePic, upload);

    // Formulario
    FormLayout formDetalles = new FormLayout();
    formDetalles.setResponsive(true);
    formDetalles.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

   
    campoLogin.setMaxLength(12);
    campoLogin.setRequiredIndicatorVisible(true);
    formDetalles.addComponent(campoLogin);

    campoNombres = new TextField("Nombres");
    campoNombres.setMaxLength(25);
    formDetalles.addComponent(campoNombres);

    campoApellido = new TextField("Apellidos");
    campoLogin.setMaxLength(25);
    formDetalles.addComponent(campoApellido);

    titleField = new ComboBox<>("Título", Arrays.asList("Mr.", "Mrs.", "Ms."));
    titleField.setPlaceholder("Debe especificar uno.");
    formDetalles.addComponent(titleField);

    // ROL
    cboRoles = new ComboBox<Rol>("ROL", PICOWEBUI.getDataProvider().getRolAll());
    cboRoles.setPlaceholder("Debe especificar uno.");
    cboRoles.setRequiredIndicatorVisible(true);
    formDetalles.addComponent(cboRoles);

    //
    PasswordField passwordField = new PasswordField("Clave");
    passwordField.setPlaceholder("Debe especificar.");
    passwordField.setRequiredIndicatorVisible(true);
    formDetalles.addComponent(passwordField);

    PasswordField passwordField2 = new PasswordField("Confirma");
    passwordField.setPlaceholder("Confirmar clave");
    formDetalles.addComponent(passwordField2);

    //
    DateField fechaCambio = new DateField("Cambio de Clave");
    formDetalles.addComponent(fechaCambio);

    fechaCambio = new DateField("Fecha Ultimo Ingreso");
    fechaCambio.setEnabled(false);
    formDetalles.addComponent(fechaCambio);

    sexField = new RadioButtonGroup<>("Sex", Arrays.asList(true, false));
    // sexField.setItemCaptionGenerator(item -> item ? "Hombre" : "Mujer");
    sexField.addStyleName("horizontal");
    formDetalles.addComponent(sexField);

    Label section = new Label("Informacion de Contacto");
    section.addStyleName(ValoTheme.LABEL_H4);
    section.addStyleName(ValoTheme.LABEL_COLORED);
    formDetalles.addComponent(section);

    emailField = new TextField("E-mail");
    emailField.setWidth("100%");
    // emailField.setRequiredIndicatorVisible(true);
    // TODO add validation that not empty, use binder
    formDetalles.addComponent(emailField);

    locationField = new TextField("Direccion");
    locationField.setWidth("100%");
    // locationField.setComponentError(new UserError("Esta direccion no existe."));
    formDetalles.addComponent(locationField);

    phoneField = new TextField("Telefono");
    phoneField.setWidth("100%");
    formDetalles.addComponent(phoneField);

    section = new Label("Información Adicional");
    section.addStyleName(ValoTheme.LABEL_H4);
    section.addStyleName(ValoTheme.LABEL_COLORED);
    formDetalles.addComponent(section);

    websiteField = new TextField("Website");
    websiteField.setPlaceholder("http://");
    websiteField.setWidth("100%");
    formDetalles.addComponent(websiteField);

    bioField = new TextArea("Bio");
    bioField.setWidth("100%");
    bioField.setRows(4);
    formDetalles.addComponent(bioField);

    // Aagregando Componentes
    root.addComponents(pic, formDetalles);
    root.setExpandRatio(formDetalles,  1);    

    try {
      // binder
      binder.forField(campoNombres).bind(Usuario::getNombres, Usuario::setNombres);
      binder.forField(campoApellido).bind(Usuario::getApellidos, Usuario::setApellidos);
      binder.forField(campoLogin).bind(Usuario::getLogin, Usuario::setLogin);
      binder.forField(emailField).bind(Usuario::getEmail, Usuario::setEmail);
    } catch (Exception e) {
      e.printStackTrace();
    }
    // binder.forField()).bind(Usuario::get, Usuario::set);
    return root;
  }

  private Component buildFooter() {
    HorizontalLayout footer = new HorizontalLayout();
    footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
    footer.setWidth(100.0f, Unit.PERCENTAGE);
    footer.setSpacing(false);

    Button ok = new Button("Guardar");
    ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
    ok.setIcon(FontAwesome.SAVE);
    ok.addClickListener(new ClickListener() {
      private static final long serialVersionUID = 1L;

      @Override
      public void buttonClick(ClickEvent event) {
        try {
          if (binder.writeBeanIfValid(user)) {
            user = binder.getBean();
            mtdGuardar();
          } else {
            BinderValidationStatus<Usuario> validate = binder.validate();
            String errorText = validate.getFieldValidationStatuses().stream().filter(BindingValidationStatus::isError).map(BindingValidationStatus::getMessage).map(Optional::get).distinct().collect(Collectors.joining(","));
          }
        } catch (Exception e) {
          e.printStackTrace();
          Notification.show("Error while updating profile", Type.ERROR_MESSAGE);
        }

      }
    });
    ok.focus();
    footer.addComponent(ok);
    footer.setComponentAlignment(ok, Alignment.TOP_RIGHT);
    return footer;
  }

  protected void mtdGuardar() throws Exception {

    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    Notification success;

    session.beginTransaction();

    if (user.getId() != null) {

      session.update(user);
      success = new Notification("Datos de usuario actualizados");

    } else {
      // user.set
      user.setFechaCreacion(new Date());
      user.setGenero('F');
      user.setActivo(false);
      session.save(user);
      success = new Notification("Usuario creado y Guardado");
    }
    session.getTransaction().commit();

    success.setDelayMsec(2000);
    success.setStyleName("bar success small");
    success.setPosition(Position.BOTTOM_CENTER);
    success.show(Page.getCurrent());

    PICOWEBEventBus.post(new ProfileUpdatedEvent());

  }

  /**
   * Metodo OPEN publico para abirir, editar.
   * 
   * @param user
   * @param preferencesTabActive
   */
  public static void open(Usuario user) {
    PICOWEBEventBus.post(new CloseOpenWindowsEvent());
    Window w = new UsuarioForm(user);
    UI.getCurrent().addWindow(w);
    w.focus();
  }

  public static void openNuevo() {
    PICOWEBEventBus.post(new CloseOpenWindowsEvent());
    Window w = new UsuarioForm(null);
    w.setModal(true);

    UI.getCurrent().addWindow(w);
    w.focus();
  }

  public void cargaDatos(Usuario user) {

  }

}

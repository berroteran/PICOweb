package org.jug.nicaragua.picoweb.view.ui.component;

import java.util.Arrays;
import org.jug.nicaragua.picoweb.modelo.Usuario;
import org.jug.nicaragua.picoweb.view.event.PICOWEBEvent.CloseOpenWindowsEvent;
import org.jug.nicaragua.picoweb.view.event.PICOWEBEvent.ProfileUpdatedEvent;
import org.jug.nicaragua.picoweb.view.event.PICOWEBEventBus;
import com.vaadin.annotations.PropertyId;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.UserError;
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

@SuppressWarnings("serial")
public class PerfilPreferencesWindow extends Window {

  public static final String ID = "profilepreferenceswindow";


  /*
   * Fields for editing the User object are defined here as class members. They are later bound to a
   * FieldGroup by calling fieldGroup.bindMemberFields(this). The Fields' values don't need to be
   * explicitly set, calling fieldGroup.setItemDataSource(user) synchronizes the fields with the user
   * object.
   */
  @PropertyId("firstName")
  private TextField firstNameField;
  @PropertyId("lastName")
  private TextField lastNameField;
  @PropertyId("title")
  private ComboBox<String> titleField;
  @PropertyId("male")
  private RadioButtonGroup<Boolean> sexField;
  @PropertyId("email")
  private TextField emailField;
  @PropertyId("location")
  private TextField locationField;
  @PropertyId("phone")
  private TextField phoneField;
  @PropertyId("website")
  private TextField websiteField;
  @PropertyId("bio")
  private TextArea bioField;

  private PerfilPreferencesWindow(Usuario user, final boolean preferencesTabOpen) {
    addStyleName("profile-window");
    setId(ID);
    Responsive.makeResponsive(this);

    setModal(true);
    setCloseShortcut(KeyCode.ESCAPE, null);
    setResizable(false);
    setClosable(false);
    setHeight(92.0f, Unit.PERCENTAGE);

    VerticalLayout content = new VerticalLayout();
    content.setSizeFull();
    content.setMargin(new MarginInfo(true, false, false, false));
    content.setSpacing(false);
    setContent(content);

    TabSheet detailsWrapper = new TabSheet();
    detailsWrapper.setSizeFull();
    detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
    detailsWrapper.addStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
    detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
    content.addComponent(detailsWrapper);
    content.setExpandRatio(detailsWrapper, 1f);

    detailsWrapper.addComponent(buildProfileTab());
    detailsWrapper.addComponent(buildPreferencesTab());

    if (preferencesTabOpen) {
      detailsWrapper.setSelectedTab(1);
    }

    content.addComponent(buildFooter());

  }

  private Component buildPreferencesTab() {
    VerticalLayout root = new VerticalLayout();
    root.setCaption("Preferencias");
    root.setIcon(FontAwesome.COGS);
    root.setSpacing(true);
    root.setMargin(true);
    root.setSizeFull();

    Label message = new Label("Not implemented aun");
    message.setSizeUndefined();
    message.addStyleName(ValoTheme.LABEL_LIGHT);
    root.addComponent(message);
    root.setComponentAlignment(message, Alignment.MIDDLE_CENTER);

    return root;
  }

  private Component buildProfileTab() {
    HorizontalLayout root = new HorizontalLayout();
    root.setCaption("Perfil");
    root.setIcon(FontAwesome.USER);
    root.setWidth(100.0f, Unit.PERCENTAGE);
    root.setMargin(true);
    root.addStyleName("profile-form");

    VerticalLayout pic = new VerticalLayout();
    pic.setSizeUndefined();
    pic.setSpacing(true);
    Image profilePic = new Image(null, new ThemeResource("img/profile-pic-300px.jpg"));
    profilePic.setWidth(100.0f, Unit.PIXELS);
    pic.addComponent(profilePic);

    Button upload = new Button("Cambiar…", new ClickListener() {
      @Override
      public void buttonClick(ClickEvent event) {
        Notification.show("Not implemented in this demo");
      }
    });
    upload.addStyleName(ValoTheme.BUTTON_TINY);
    pic.addComponent(upload);

    root.addComponent(pic);

    FormLayout details = new FormLayout();
    details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
    root.addComponent(details);
    root.setExpandRatio(details, 1);

    firstNameField = new TextField("Login");
    firstNameField.setMaxLength(12);
    details.addComponent(firstNameField);

    firstNameField = new TextField("Nombres");
    firstNameField.setMaxLength(25);
    details.addComponent(firstNameField);
    lastNameField = new TextField("Apellidos");
    firstNameField.setMaxLength(25);
    details.addComponent(lastNameField);

    titleField = new ComboBox<>("Título", Arrays.asList("Mr.", "Mrs.", "Ms."));
    titleField.setPlaceholder("Debe especificar uno.");
    details.addComponent(titleField);

    // ROL
    titleField = new ComboBox<>("ROL", Arrays.asList("Mr.", "Mrs.", "Ms."));
    titleField.setPlaceholder("Debe especificar uno.");
    details.addComponent(titleField);

    //
    PasswordField passwordField = new PasswordField("Clave");
    passwordField.setPlaceholder("Debe especificar.");
    details.addComponent(titleField);

    //
    DateField fechaCambio = new DateField("Cambio de Clave");
    details.addComponent(fechaCambio);

    fechaCambio = new DateField("Fecha Ultimo Ingreso");
    details.addComponent(fechaCambio);

    sexField = new RadioButtonGroup<>("Sex", Arrays.asList(true, false));
    sexField.setItemCaptionGenerator(item -> item ? "Hombre" : "Mujer");
    sexField.addStyleName("horizontal");
    details.addComponent(sexField);

    Label section = new Label("Informacion de Contacto");
    section.addStyleName(ValoTheme.LABEL_H4);
    section.addStyleName(ValoTheme.LABEL_COLORED);
    details.addComponent(section);

    emailField = new TextField("E-mail");
    emailField.setWidth("100%");
    emailField.setRequiredIndicatorVisible(true);
    // TODO add validation that not empty, use binder
    details.addComponent(emailField);

    locationField = new TextField("Direccion");
    locationField.setWidth("100%");
    locationField.setComponentError(new UserError("Esta direccion no existe."));
    details.addComponent(locationField);

    phoneField = new TextField("Telefono");
    phoneField.setWidth("100%");
    details.addComponent(phoneField);

    section = new Label("Información Adicional");
    section.addStyleName(ValoTheme.LABEL_H4);
    section.addStyleName(ValoTheme.LABEL_COLORED);
    details.addComponent(section);

    websiteField = new TextField("Website");
    websiteField.setPlaceholder("http://");
    websiteField.setWidth("100%");
    details.addComponent(websiteField);

    bioField = new TextArea("Bio");
    bioField.setWidth("100%");
    bioField.setRows(4);
    details.addComponent(bioField);

    return root;
  }

  private Component buildFooter() {
    HorizontalLayout footer = new HorizontalLayout();
    footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
    footer.setWidth(100.0f, Unit.PERCENTAGE);
    footer.setSpacing(false);

    Button ok = new Button("OK");
    ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
    ok.addClickListener(new ClickListener() {
      @Override
      public void buttonClick(ClickEvent event) {
        try {

          // Updated user should also be persisted to database. But
          // not in this demo.

          Notification success = new Notification("Profile updated successfully");
          success.setDelayMsec(2000);
          success.setStyleName("bar success small");
          success.setPosition(Position.BOTTOM_CENTER);
          success.show(Page.getCurrent());

          PICOWEBEventBus.post(new ProfileUpdatedEvent());
          close();
        } catch (Exception e) {
          Notification.show("Error while updating profile", Type.ERROR_MESSAGE);
        }

      }
    });
    ok.focus();
    footer.addComponent(ok);
    footer.setComponentAlignment(ok, Alignment.TOP_RIGHT);
    return footer;
  }

  public static void open(Usuario user, final boolean preferencesTabActive) {
    PICOWEBEventBus.post(new CloseOpenWindowsEvent());
    Window w = new PerfilPreferencesWindow(user, preferencesTabActive);
    UI.getCurrent().addWindow(w);
    w.focus();
  }
}

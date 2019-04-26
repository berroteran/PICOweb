package org.jug.nicaragua.picoweb.view;

import java.io.File;
import org.jug.nicaragua.picoweb.view.event.PICOWEBEvent.UserLoginRequestedEvent;
import org.jug.nicaragua.picoweb.view.event.PICOWEBEventBus;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class LoginView extends VerticalLayout {

  public LoginView() {
    setSizeFull();
    setMargin(false);
    setSpacing(false);

    Component loginForm = buildLoginForm();
    addComponents(loginForm);
    setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);

  }

  private Component buildLoginForm() {
    final VerticalLayout loginPanel = new VerticalLayout();

    // Find the application directory
    String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

    // Image as a file resource
    FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/rrh_banner.jpg"));
    Image banner = new Image();
    banner.setSource(resource);
    banner.setWidth("460px");
    banner.setHeight("100px");
    banner.setResponsive(true);

    loginPanel.setSizeUndefined();
    loginPanel.setMargin(false);
    Responsive.makeResponsive(loginPanel);
    loginPanel.addStyleName("login-panel");

    loginPanel.addComponents(banner, buildFields(), new CheckBox("Recuerdame", true));
    return loginPanel;
  }

  private Component buildFields() {
    HorizontalLayout fields = new HorizontalLayout();
    fields.addStyleName("fields");

    final TextField username = new TextField("Usuario");
    username.setIcon(FontAwesome.USER);
    username.setValue("usuario"); // TODO borrar esto
    username.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

    final PasswordField password = new PasswordField("Clave");
    password.setIcon(FontAwesome.LOCK);
    password.setValue("123"); // TODO borrar esto
    password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

    final Button signin = new Button("Entrar");
    signin.addStyleName(ValoTheme.BUTTON_PRIMARY);
    signin.setClickShortcut(KeyCode.ENTER);
    signin.focus();

    fields.addComponents(username, password, signin);
    fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);

    signin.addClickListener(new ClickListener() {
      @Override
      public void buttonClick(final ClickEvent event) {
        PICOWEBEventBus.post(new UserLoginRequestedEvent(username.getValue(), password.getValue()));
      }
    });
    return fields;
  }

  private Component buildLabels() {
    CssLayout labels = new CssLayout();
    labels.addStyleName("labels");

    Label welcome = new Label("Bienvenido");
    welcome.setSizeUndefined();
    welcome.addStyleName(ValoTheme.LABEL_H4);
    welcome.addStyleName(ValoTheme.LABEL_COLORED);
    labels.addComponent(welcome);

    Label title = new Label("JUG NICARAGUA PICO 2019");
    title.setSizeUndefined();
    title.addStyleName(ValoTheme.LABEL_H3);
    title.addStyleName(ValoTheme.LABEL_LIGHT);
    labels.addComponent(title);
    return labels;
  }

}

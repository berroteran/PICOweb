package org.jug.nicaragua.picoweb;

import java.util.Locale;
import org.jug.nicaragua.picoweb.dao.DataProvider;
import org.jug.nicaragua.picoweb.dao.data.DummyDataProvider;
import org.jug.nicaragua.picoweb.modelo.Usuario;
import org.jug.nicaragua.picoweb.view.LoginView;
import org.jug.nicaragua.picoweb.view.MainView;
import org.jug.nicaragua.picoweb.view.event.PICOWEBEvent.BrowserResizeEvent;
import org.jug.nicaragua.picoweb.view.event.PICOWEBEvent.CloseOpenWindowsEvent;
import org.jug.nicaragua.picoweb.view.event.PICOWEBEvent.UserLoggedOutEvent;
import org.jug.nicaragua.picoweb.view.event.PICOWEBEvent.UserLoginRequestedEvent;
import org.jug.nicaragua.picoweb.view.event.PICOWEBEventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Page;
import com.vaadin.server.Page.BrowserWindowResizeEvent;
import com.vaadin.server.Page.BrowserWindowResizeListener;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/** Widgetset("com.vaadin.demo.dashboard.DashboardWidgetSet") */

@Theme("dashboard")
@Widgetset("org.jug.nicaragua.picoweb.DashboardWidgetSet")
@Title("PICO web  2019")
@SuppressWarnings("serial")
public final class PICOWEBUI extends UI {

  /*
   * This field stores an access to the dummy backend layer. In real applications you most likely gain
   * access to your beans trough lookup or injection; and not in the UI but somewhere closer to where
   * they're actually accessed.
   */
  private final DataProvider dataProvider = new DummyDataProvider();
  private final PICOWEBEventBus dashboardEventbus = new PICOWEBEventBus();

  @Override
  protected void init(final VaadinRequest request) {

    // Etableciendo locale e idioma
    setLocale(Locale.forLanguageTag("es-ES"));

    PICOWEBEventBus.register(this);
    Responsive.makeResponsive(this);
    addStyleName(ValoTheme.UI_WITH_MENU);

    updateContent();

    // Some views need to be aware of browser resize events so a BrowserResizeEvent gets fired to the event bus on every occasion.
    Page.getCurrent().addBrowserWindowResizeListener(new BrowserWindowResizeListener() {
      @Override
      public void browserWindowResized(final BrowserWindowResizeEvent event) {
        PICOWEBEventBus.post(new BrowserResizeEvent());
      }
    });
  }


  @Subscribe
  public void userLoginRequested(final UserLoginRequestedEvent event) {
    Usuario user = null;
    try {
      user = getDataProvider().authenticate(event.getUserName(), event.getPassword());
    } catch (Exception e) {
      //errores al validdar.
      Notification.show("Error al intentar iniciar session:", e.getMessage() , Type.ERROR_MESSAGE).setPosition(Position.BOTTOM_CENTER);;
      e.printStackTrace();
    }
    VaadinSession.getCurrent().setAttribute(Usuario.class.getName(), user);
    //actualizar
    updateContent();
  }

  @Subscribe
  public void userLoggedOut(final UserLoggedOutEvent event) {
    // When the user logs out, current VaadinSession gets closed and the
    // page gets reloaded on the login screen. Do notice the this doesn't
    // invalidate the current HttpSession.
    VaadinSession.getCurrent().close();
    Page.getCurrent().reload();
  }

  @Subscribe
  public void closeOpenWindows(final CloseOpenWindowsEvent event) {
    for (Window window : getWindows()) {
      window.close();
    }
  }

  /**
   * @return An instance for accessing the (dummy) services layer.
   */
  public static DataProvider getDataProvider() {
    return ((PICOWEBUI) getCurrent()).dataProvider;
  }

  public static PICOWEBEventBus getDashboardEventbus() {
    return ((PICOWEBUI) getCurrent()).dashboardEventbus;
  }
  
  /**
   *Aqui se gestiona el login del sisstema, la seguridad.
   *busco el user en sesion, sino esta, se recarga el login.
   *
   */
  private void updateContent() {
    //TODO Mejorar la seguridad.
    Usuario user = (Usuario) VaadinSession.getCurrent().getAttribute(Usuario.class.getName());
    if (user != null ) { //&& "admin".equals(user.getRol())) {
      // Authenticated user
      setContent(new MainView());
      removeStyleName("loginview");
      getNavigator().navigateTo(getNavigator().getState());
    } else {
      setContent(new LoginView());
      addStyleName("loginview");
    }
  }

}

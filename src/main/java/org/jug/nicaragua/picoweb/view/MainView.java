package org.jug.nicaragua.picoweb.view;

import org.jug.nicaragua.picoweb.PantallaPrincipal;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

/*
 * Dashboard MainView is a simple HorizontalLayout that wraps the menu on the left and creates a
 * simple container for the navigator on the right.
 */
@SuppressWarnings("serial")
public class MainView extends HorizontalLayout {

  public MainView() {
    setSizeFull();
    addStyleName("mainview");
    setSpacing(false);

    //MENU
    addComponent(new DashboardMenu());

    //Cotenido
    ComponentContainer content = new CssLayout();
    content.addStyleName("view-content");
    content.setSizeFull(); // no tocar, asi funciona
   
    addComponent(content);
    setExpandRatio(content, 1.0f);
    setResponsive(true);

    //TODO, no aplicar undefined
    //Llamada a pantalla princiapl el contenido. 
    new PantallaPrincipal(content);
  }
}

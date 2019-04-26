package org.jug.nicaragua.picoweb.view;

import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

/**
 * constantes enumeradoras de las vistas.
 * 
 * @author lkf
 *
 */
public enum PantallasViewType {
  DASHBOARD( "Inicio", DashboardView.class, FontAwesome.HOME, true),
  REPORTS( "Reportes", ReportsView.class, FontAwesome.FILE_TEXT_O, true),
  CARGOS( "Diario ", ReportsView.class, FontAwesome.FILE_TEXT_O, true),
  PROCESOS( "Catalogo", ReportsView.class, FontAwesome.FILE_TEXT_O, true),
  PERSONAS( "Periodos", ReportsView.class, FontAwesome.FILE_TEXT_O, true),
  //CAPACITAIONES( "Capacitaciones", EvaluacionesVIew.class, FontAwesome.BAR_CHART_O, false),
  
  //CAPACITAIONES( "Formacion", EvaluacionesVIew.class, FontAwesome.BAR_CHART_O, false),
  
  //VACANTES( "Vacantes", CargosView.class, FontAwesome.TABLE, false),
  DEPARTAMENTOS( "xxx", DepartamentosView.class, FontAwesome.TABLE, false),
  
  
  USUARIOS( "Usuarios", UsuariosView.class, FontAwesome.USERS, false), 
  EMPRESA( "Empresa", EmpresaView.class, FontAwesome.HOME, false);

  private final String viewName;
  private final Class<? extends View> viewClass;
  private final Resource icon;
  private final boolean stateful;

  private PantallasViewType(final String viewName, final Class<? extends View> viewClass, final Resource icon, final boolean stateful) {
    this.viewName = viewName;
    this.viewClass = viewClass;
    this.icon = icon;
    this.stateful = stateful;
  }

  public boolean isStateful() {
    return stateful;
  }

  public String getViewName() {
    return viewName;
  }

  public Class<? extends View> getViewClass() {
    return viewClass;
  }

  public Resource getIcon() {
    return icon;
  }

  public static PantallasViewType getByViewName(final String viewName) {
    PantallasViewType result = null;
    for (PantallasViewType viewType : values()) {
      if (viewType.getViewName().equals(viewName)) {
        result = viewType;
        break;
      }
    }
    return result;
  }

}

package org.jug.nicaragua.picoweb.view;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

public class Utils {
  
  /**
   * Muestra un mesaje en el view principal.
   * @param typeHumanizedMessage
   * @param page
   * @param string
   */
  public static void Mensajes(Type tipoMensaje, Page page, String titulo) {
    Mensajes(tipoMensaje, page, titulo, null);
  }

  public static void Mensajes(Type tipoMensaje, Page page, String titulo, String message) {
    Notification noti = new Notification(titulo,tipoMensaje);
    
    noti.setPosition(Position.BOTTOM_CENTER);
    noti.setDelayMsec(2500);
    
    noti.show( page );
  }
}

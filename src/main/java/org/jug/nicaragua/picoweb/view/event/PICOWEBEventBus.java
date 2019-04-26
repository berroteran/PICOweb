package org.jug.nicaragua.picoweb.view.event;

import org.jug.nicaragua.picoweb.PICOWEBUI;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;

/**
 * A simple wrapper for Guava event bus. Defines static convenience methods for relevant actions.
 */
public class PICOWEBEventBus implements SubscriberExceptionHandler {

  private final EventBus eventBus = new EventBus(this);

  public static void post(final Object event) {
    PICOWEBUI.getDashboardEventbus().eventBus.post(event);
  }

  public static void register(final Object object) {
    PICOWEBUI.getDashboardEventbus().eventBus.register(object);
  }

  public static void unregister(final Object object) {
    PICOWEBUI.getDashboardEventbus().eventBus.unregister(object);
  }

  @Override
  public final void handleException(final Throwable exception, final SubscriberExceptionContext context) { 
    exception.printStackTrace();
  }
}

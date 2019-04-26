package org.jug.nicaragua.picoweb.view.forms;

import java.util.Optional;
import java.util.stream.Collectors;
import org.jug.nicaragua.picoweb.dao.BussinessException;
import org.jug.nicaragua.picoweb.dao.EvalCapacitacionDAOImpl;
import org.jug.nicaragua.picoweb.dao.PersonaDAOImpl;
import org.jug.nicaragua.picoweb.modelo.EvalCapacitacion;
import org.jug.nicaragua.picoweb.modelo.Persona;
import org.jug.nicaragua.picoweb.view.event.PICOWEBEvent.CloseOpenWindowsEvent;
import org.jug.nicaragua.picoweb.view.event.PICOWEBEventBus;
import com.vaadin.data.Binder;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.data.BindingValidationStatus;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class EvalCapacitacionForm extends Window {

  private static final long serialVersionUID = 6955691865061046856L;
  private static EvalCapacitacionForm instancia;

  // binder
  Binder<EvalCapacitacion> binder = new Binder<EvalCapacitacion>();
  private EvalCapacitacion evaluacion = null;

  public EvalCapacitacionForm() {
    setCaption("Nueva Evaluación");
    configuraForm(new EvalCapacitacion());
  }


  public EvalCapacitacionForm(EvalCapacitacion editarProceso) {
    setCaption("Editando Capacitacion");
    configuraForm(editarProceso);
  }

  private void configuraForm(EvalCapacitacion process) {
    instancia = this;
    Responsive.makeResponsive(this);
    addCloseShortcut(KeyCode.ESCAPE, null);
    setIcon(FontAwesome.USER);
    setModal(true);
    setClosable(true);
    setWidth(80f, Unit.PERCENTAGE);
    setHeight(90.0f, Unit.PERCENTAGE);

    // contenido
    VerticalLayout mainLayout = new VerticalLayout();
    mainLayout.setSizeFull();
    mainLayout.setMargin(false);
    TabSheet tab = new TabSheet();
    tab.setSizeFull();

    // Formulario
    FormLayout myForm = createForm(new FormLayout());
    // myForm.setSizeFull();
    //myForm.setHeightUndefined();
    Panel p = new Panel("Datos del Evaluacion", myForm);
    p.setSizeFull();
    p.setResponsive(true);
    p.setWidth("100%");
    //tabs
    tab.addComponent( new VerticalLayout(myForm, getAcuerdos(), getPlanCarrera() ));
    // Esamblando
    mainLayout.addComponents( tab,buildFooter());
    mainLayout.setExpandRatio( tab, 1);
    
    //setdata
    evaluacion = process ;
    binder.setBean(evaluacion);
    // salidFinal.
    setContent(mainLayout);
  }


  private Panel getAcuerdos() {
   Panel p = new Panel("De Acuerdo a los resultados de la evaluacion");
   p.setSizeFull();
   p.setResponsive(true);
   p.setWidth("100%");
   TextArea fortalezas = new TextArea("Fortalezas");
   TextArea debilidades = new TextArea("Debilidades");
   fortalezas.setWidth("100%");
   debilidades.setWidth("100%");
   VerticalLayout v = new VerticalLayout(fortalezas, debilidades);
   v.setSizeFull();
   p.setContent(v);
   return p;
  }
  
  
  private Component getPlanCarrera() {
    Panel p = new Panel("Plan de Carrera");
    p.setSizeFull();
    TextArea obe = new TextArea("Observacion del Evaluado");
    TextArea obs = new TextArea("Observacion del Evaluador");
    obe.setWidth("100%");
    obs.setWidth("100%");
    HorizontalLayout h = new HorizontalLayout(obe, obs);
    p.setContent(h);
    h.setSizeFull();
    return p;
  }


  private FormLayout createForm(FormLayout myForm) {
    myForm.setSizeFull();
    myForm.setSizeUndefined();
    myForm.setSpacing(true);

    //campos
    ComboBox<Persona> evaluado = new ComboBox<Persona>("Evaluado");
    evaluado.setDataProvider( getDataEvaluado() );
    evaluado.setItemCaptionGenerator(Persona::getPrimerNombre);
    evaluado.setPlaceholder("Seleccione uno");
    evaluado.setWidth("350px");
    TextField cargo = new TextField("Cargo");
    cargo.setReadOnly(true);
    
    ComboBox<Persona> evaluador = new ComboBox<Persona>("Evaluador");
    evaluador.setDataProvider( getDataEvaluado() );
    evaluador.setItemCaptionGenerator(Persona::getPrimerNombre);
    evaluador.setPlaceholder("Seleccione uno");
    evaluador.setWidth("350px");
    TextField cargoe = new TextField("Cargo Evaluador");
    cargoe.setReadOnly(true);
    HorizontalLayout l1 = new HorizontalLayout(evaluado, cargo, evaluador, cargoe);
    TextField periodo = new TextField("Periodo Evaluacion");
    TextField antiuguedad = new TextField("Antiguedad ");
    HorizontalLayout l3 = new HorizontalLayout(periodo, antiuguedad);
    l1.setSizeFull();

    l3.setSizeFull();
    
    //binder
    try {
      // binder
      //binder.forField(id) .withConverter( new StringToIntegerConverter("must be integer"))  .bind(Proceso::getId );

    } catch (Exception e) {
      e.printStackTrace();
    }

    myForm.addComponents(l1, l3 );
    return myForm;
  }

  private ListDataProvider<Persona> getDataEvaluado() {
    ListDataProvider<Persona> dataProvider = null;
    try {
      dataProvider = com.vaadin.data.provider.DataProvider.ofCollection((new PersonaDAOImpl()).findAll());
    } catch (BussinessException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
      Notification.show(e1.getMessage(), Type.ERROR_MESSAGE);
    }
    return dataProvider;
  }


  private Component buildFooter() {
    HorizontalLayout footer = new HorizontalLayout();
    footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
    footer.setWidth(100.0f, Unit.PERCENTAGE);
    footer.setSpacing(false);

    Button btnSave = new Button("Guardar");
    btnSave.setIcon(FontAwesome.SAVE);
    btnSave.addStyleName(ValoTheme.BUTTON_PRIMARY);

    Button btnReporte = new Button("Imprimir");
    btnReporte.addStyleName(ValoTheme.BUTTON_FRIENDLY);
    
    footer.addComponents(btnReporte, btnSave);
    footer.setComponentAlignment(btnSave, Alignment.TOP_RIGHT);
    btnSave.addClickListener(new ClickListener() {
      private static final long serialVersionUID = 1L;

      @Override
      public void buttonClick(final ClickEvent event) {
        try {
          BinderValidationStatus<EvalCapacitacion> validate = binder.validate();
          try {
            //TODO validar si el evaluado y el evaluador son los mismo.
            if (binder.writeBeanIfValid(evaluacion)) {
              evaluacion = binder.getBean();
              new EvalCapacitacionDAOImpl().saveOrUpdate(evaluacion);

              // Exito.
              Notification.show("Datos del Evaluacion Guardados Correctamente.", Type.TRAY_NOTIFICATION);

            } else {
              String errorText = validate.getFieldValidationStatuses().stream().filter(BindingValidationStatus::isError).map(BindingValidationStatus::getMessage).map(Optional::get).distinct().collect(Collectors.joining(","));
            }
          } catch (Exception e) {
            e.printStackTrace();
            Notification.show("Error al Guardar datos de la Evaluacion.", Type.ERROR_MESSAGE);
          }

        } catch (Exception e) {
          Notification.show("Datos de la Evaluacion Incorrectos.", Type.ERROR_MESSAGE);
          e.printStackTrace();
        }
      }
    });
    
    return footer;
  }

  public static void open(EvalCapacitacion p) {
    PICOWEBEventBus.post(new CloseOpenWindowsEvent());
    Window w;
    if (p == null)
      w = new EvalCapacitacionForm();
    else
      w = new EvalCapacitacionForm(p);
    UI.getCurrent().addWindow(w);
    w.focus();
  }

  public static void open() {
    open(null);
  }

  public static EvalCapacitacionForm getInstancia() {
    return instancia == null ? new EvalCapacitacionForm() : instancia;
  }

}

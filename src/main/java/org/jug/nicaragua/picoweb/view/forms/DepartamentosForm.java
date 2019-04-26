package org.jug.nicaragua.picoweb.view.forms;

import java.util.Optional;
import java.util.stream.Collectors;
import org.jug.nicaragua.picoweb.dao.DepartamentoDAOImpl;
import org.jug.nicaragua.picoweb.modelo.Departamento;
import org.jug.nicaragua.picoweb.view.event.PICOWEBEvent.CloseOpenWindowsEvent;
import org.jug.nicaragua.picoweb.view.event.PICOWEBEventBus;
import com.vaadin.data.Binder;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.data.BindingValidationStatus;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class DepartamentosForm extends Window {

  private static final long serialVersionUID = 6955691865061046856L;
  private static DepartamentosForm instancia;

  // binder
  Binder<Departamento> binder = new Binder<Departamento>();
  private Departamento proceso = null;


  public DepartamentosForm() {
    setCaption("Nuevo Departamento");
    configuraForm(new Departamento());
  }


  public DepartamentosForm(Departamento editarProceso) {
    setCaption("Editando Departamento   ");
    configuraForm(editarProceso);
  }

  private void configuraForm(Departamento process) {
    instancia = this;
    Responsive.makeResponsive(this);
    addCloseShortcut(KeyCode.ESCAPE, null);
    setIcon(FontAwesome.USER);
    setModal(true);
    setClosable(true);
    setWidth(400, Unit.PIXELS);
    setHeight(550, Unit.PIXELS);

    // contenido
    VerticalLayout mainLayout = new VerticalLayout();
    mainLayout.setSizeFull();

    // Formulario
    FormLayout myForm = createForm(new FormLayout());
    // myForm.setSizeFull();
    myForm.setHeightUndefined();
    Panel p = new Panel("Descripcion del Puesto", myForm);
    p.setSizeFull();
    p.setResponsive(true);

    // Esamblando
    mainLayout.addComponents(p, buildFooter());
    mainLayout.setExpandRatio(p, 1);

    // setdata
    proceso = process;
    binder.setBean(proceso);
    // salidFinal.
    setContent(mainLayout);
  }



  private FormLayout createForm(FormLayout myForm) {
    myForm.setSizeFull();
    myForm.setSizeUndefined();
    myForm.setWidth("350px");
    myForm.setSpacing(true);

    // campos
    TextField id = new TextField("ID");
    id.setReadOnly(true);
    TextField proceso = new TextField("Prceso");
    TextArea descripcion = new TextArea("Descripcion");
    TextField procesoSuperior = new TextField("Proceso superior");
    procesoSuperior.setValue("0");
    TextField responsable = new TextField("Puesto Responsble");
    responsable.setValue("0");


    // binder
    try {
      // binder
      // binder.forField(id) .withConverter( new StringToIntegerConverter("must be integer"))
      // .bind(Proceso::getId );
      binder.forField(proceso).bind(Departamento::getDepartamento, Departamento::setDepartamento);
      binder.forField(descripcion).bind(Departamento::getDescripcion, Departamento::setDescripcion);
      binder.forField(procesoSuperior).withConverter(new StringToIntegerConverter("must be integer")).bind(Departamento::getIdsuperior, Departamento::setIdsuperior);
      binder.forField(responsable).withConverter(new StringToIntegerConverter("must be integer")).bind(Departamento::getIdresponsable, Departamento::setIdresponsable);
    } catch (Exception e) {
      e.printStackTrace();
    }

    myForm.addComponents(id, proceso, descripcion, procesoSuperior, responsable);
    return myForm;
  }


  private Component buildFooter() {
    HorizontalLayout footer = new HorizontalLayout();
    footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
    footer.setWidth(100.0f, Unit.PERCENTAGE);
    footer.setSpacing(false);

    Button btnSave = new Button("Guardar");
    btnSave.setIcon(FontAwesome.SAVE);
    btnSave.addStyleName(ValoTheme.BUTTON_PRIMARY);

    footer.addComponents(btnSave);
    footer.setComponentAlignment(btnSave, Alignment.TOP_RIGHT);
    btnSave.addClickListener(new ClickListener() {
      private static final long serialVersionUID = 1L;

      @Override
      public void buttonClick(final ClickEvent event) {
        try {
          BinderValidationStatus<Departamento> validate = binder.validate();
          try {
            if (binder.writeBeanIfValid(proceso)) {
              proceso = binder.getBean();
              new DepartamentoDAOImpl().saveOrUpdate(proceso);

              // Exito.
              Notification.show("Datos del Prceso Guardados Correctamente.", Type.TRAY_NOTIFICATION);

            } else {
              String errorText = validate.getFieldValidationStatuses().stream().filter(BindingValidationStatus::isError).map(BindingValidationStatus::getMessage).map(Optional::get).distinct().collect(Collectors.joining(","));
            }
          } catch (Exception e) {
            e.printStackTrace();
            Notification.show("Error al Guardar datos del Proceso.", Type.ERROR_MESSAGE);
          }

        } catch (Exception e) {
          Notification.show("Datos de Proceso Incorrectos.", Type.ERROR_MESSAGE);
          e.printStackTrace();
        }
      }
    });
    return footer;
  }


  public static void open(Departamento p) {
    PICOWEBEventBus.post(new CloseOpenWindowsEvent());
    Window w;
    if (p == null)
      w = new DepartamentosForm();
    else
      w = new DepartamentosForm(p);
    UI.getCurrent().addWindow(w);
    w.focus();
  }

  public static void open() {
    open(null);
  }

  public static DepartamentosForm getInstancia() {
    return instancia == null ? new DepartamentosForm() : instancia;
  }

}

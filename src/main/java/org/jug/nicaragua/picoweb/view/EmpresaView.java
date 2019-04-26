package org.jug.nicaragua.picoweb.view;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.hibernate.Session;
import org.jug.nicaragua.picoweb.dao.HibernateUtil;
import org.jug.nicaragua.picoweb.dao.data.PaisData;
import org.jug.nicaragua.picoweb.domain.Transaction;
import org.jug.nicaragua.picoweb.modelo.Empresa;
import org.jug.nicaragua.picoweb.modelo.Pais;
import org.jug.nicaragua.picoweb.view.event.PICOWEBEvent.BrowserResizeEvent;
import org.jug.nicaragua.picoweb.view.event.PICOWEBEvent.EmpresaSave;
import org.jug.nicaragua.picoweb.view.event.PICOWEBEvent.ProfileUpdatedEvent;
import org.jug.nicaragua.picoweb.view.event.PICOWEBEventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.eventbus.Subscribe;
import com.vaadin.data.Binder;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.data.BindingValidationStatus;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.server.UserError;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public final class EmpresaView extends VerticalLayout implements View {

  private static final Logger log = LoggerFactory.getLogger(EmpresaView.class);

  private Button createReport;

  private static final DateFormat DATEFORMAT = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
  private static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#.##");
  private static final Set<Column<Transaction, ?>> collapsibleColumns = new LinkedHashSet<>();
  FormLayout myFormulario;

  public EmpresaView() {
    // setSizeFull();
    addStyleName("transactions");
    setMargin(false);
    setSpacing(false);
    setHeightUndefined();
    PICOWEBEventBus.register(this);
    // responsive
    this.setResponsive(true);


    addComponent(buildToolbar());
    // Formulario
    Panel panel = new Panel(getFormulario());
    panel.setResponsive(true);
    panel.setSizeFull();

    // addComponent(myFormulario);
    addComponent(panel);

    // ajustando
    // setExpandRatio(myFormulario, 1);

  }



  @Override
  public void detach() {
    super.detach();
    // A new instance of TransactionsView is created every time it's
    // navigated to so we'll need to clean up references to it on detach.
    try {
      PICOWEBEventBus.unregister(this);
    } catch (Exception e) {

    }
  }

  private Component buildToolbar() {
    HorizontalLayout header = new HorizontalLayout();
    header.addStyleName("viewheader");
    Responsive.makeResponsive(header);

    Label title = new Label("Datos de la Empresa");
    title.setSizeUndefined();
    title.addStyleName(ValoTheme.LABEL_H1);
    title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
    header.addComponent(title);

    createReport = buildCreateReport();
    HorizontalLayout tools = new HorizontalLayout(createReport); // aqui barra
    tools.addStyleName("toolbar");
    header.addComponent(tools);

    return header;
  }

  private Button buildCreateReport() {
    final Button createReport = new Button("Imprimir");
    createReport.setDescription("Imprimir");
    createReport.addClickListener(event -> createNewReportFromSelection());
    createReport.setEnabled(true);
    return createReport;
  }

  private boolean defaultColumnsVisible() {
    boolean result = true;
    for (Column<Transaction, ?> column : collapsibleColumns) {
      if (column.isHidden() == Page.getCurrent().getBrowserWindowWidth() < 800) {
        result = false;
      }
    }
    return result;
  }

  @Subscribe
  public void browserResized(final BrowserResizeEvent event) {
    // Some columns are collapsed when browser window width gets small
    // enough to make the table fit better.

    if (defaultColumnsVisible()) {
      for (Column<Transaction, ?> column : collapsibleColumns) {
        column.setHidden(Page.getCurrent().getBrowserWindowWidth() < 800);
      }
    }
  }

  void createNewReportFromSelection() {

  }


  @Override
  public void enter(final ViewChangeEvent event) {

  }


  private FormLayout getFormulario() {
    myFormulario = new FormLayout();
    myFormulario.setHeightUndefined();
    myFormulario.setResponsive(true);
    myFormulario.setSpacing(true);
    myFormulario.setMargin(true);

    Binder<Empresa> binder = new Binder<Empresa>();

    Empresa miEmpresa = getEmpresa();

    // conectando con el BEAN
    binder.setBean(miEmpresa);

    // Create the fields
    TextField razonSocial = new TextField("Razon Social");
    razonSocial.setWidth("550");
    razonSocial.setMaxLength(250);
    razonSocial.setResponsive(true);
    // firstName.setValueChangeMode(ValueChangeMode.EAGER);
    TextField nombreComercial = new TextField("Nombre Comercial");
    nombreComercial.setWidth("250px");
    nombreComercial.setMaxLength(100);
    // lastName.setValueChangeMode(ValueChangeMode.EAGER);
    TextField noRUC = new TextField("No RUC");
    // phone.setValueChangeMode(ValueChangeMode.EAGER);
    TextArea direccion = new TextArea("Direccion");
    direccion.setWidth("600px");
    direccion.setHeight("70px");
    direccion.setComponentError(new UserError("Esta direccion no existe."));

    TextField ciudad = new TextField("Ciudad");
    ciudad.setWidth("50%");
    ComboBox<Pais> pais = new ComboBox<Pais>("Pais");
    pais.setDataProvider(PaisData.getData());
    pais.setDescription(Pais.class.getName());
    pais.setEmptySelectionAllowed(false);

    HorizontalLayout direccion2 = new HorizontalLayout();
    direccion2.setWidth("800");
    direccion2.addComponents(pais, ciudad);

    HorizontalLayout telefonos = new HorizontalLayout();
    telefonos.setResponsive(true);
    TextField telefono1 = new TextField("Telefono1");
    TextField telefono2 = new TextField("Telefono2");
    telefonos.addComponents(telefono1, telefono2);
    telefonos.setWidth("800");

    TextField website = new TextField("Website");
    website.setWidth("400");
    website.setMaxLength(55);
    website.setPlaceholder("http://");
    TextField email = new TextField("E-mail");
    email.setWidth("400");
    email.setMaxLength(55);
    TextField nombreCheque = new TextField("Cheque a Nombre de:");
    nombreCheque.setWidth("400");
    nombreCheque.setMaxLength(55);


    SerializablePredicate<String> phoneOrEmailPredicate = value -> !telefono1.getValue().trim().isEmpty() || !email.getValue().trim().isEmpty();

    // E-mail and phone have specific validators
    // Binding<Contact, String> emailBinding =
    // binder.forField(email).withValidator(phoneOrEmailPredicate, "Both phone and
    // email cannot be empty").withValidator(new EmailValidator("Incorrect email
    // address"))
    // .bind(Contact::getEmail, Contact::setEmail);

    // Binding<Contact, String> phoneBinding =
    // binder.forField(phone).withValidator(phoneOrEmailPredicate, "Both phone and email cannot be
    // empty").bind(Contact::getPhone, Contact::setPhone);

    // Trigger cross-field validation when the other field is changed
    // email.addValueChangeListener(event -> phoneBinding.validate());
    // phone.addValueChangeListener(event -> emailBinding.validate());

    // Establecer Requeridos
    noRUC.setRequiredIndicatorVisible(true);
    razonSocial.setRequiredIndicatorVisible(true);
    nombreComercial.setRequiredIndicatorVisible(true);

    binder.forField(noRUC).withValidator(new StringLengthValidator("Es necesario el No RUC", 1, null)).bind(Empresa::getNoRUC, Empresa::setNoRUC);
    binder.forField(razonSocial).withValidator(new StringLengthValidator("La razon social es necesaria", 1, null)).bind(Empresa::getRazonSocial, Empresa::setRazonSocial);
    binder.forField(nombreComercial).withValidator(new StringLengthValidator("El nombre comercial es necesario", 1, null)).bind(Empresa::getNombreComercial, Empresa::setNombreComercial);
    binder.forField(direccion).bind(Empresa::getDireccion, Empresa::setDireccion);
    binder.forField(ciudad).bind(Empresa::getCiudad, Empresa::setCiudad);
    // binder.forField(pais).bind(Empresa::getPais, Empresa::setPais);
    binder.forField(telefono1).bind(Empresa::getTelefono1, Empresa::setTelefono1);
    binder.forField(telefono2).bind(Empresa::getTelefono2, Empresa::setTelefono2);
    binder.forField(email).bind(Empresa::getTelefono1, Empresa::setTelefono1);
    binder.forField(website).bind(Empresa::getWebsite, Empresa::setWebsite);
    binder.forField(nombreCheque).bind(Empresa::getNombreCheque, Empresa::setNombreCheque);

    // Birthdate and doNotCall don't need any special validators
    // binder.bind(doNotCall, Contact::isDoNotCall, Contact::setDoNotCall);
    // binder.bind(birthDate, Contact::getBirthDate, Contact::setBirthDate);



    Button save = new Button("Guardar");
    save.setStyleName(ValoTheme.BUTTON_PRIMARY);
    save.setIcon(FontAwesome.SAVE);
    Button reset = new Button("Restablecer");
    Button chglogo = new Button("Actualizar Logo");

    // Button bar
    HorizontalLayout acciones = new HorizontalLayout();
    acciones.addStyleName("toolbar");
    acciones.setResponsive(true);
    acciones.addComponents(save, reset, chglogo);


    // Click listeners for the buttons
    save.addClickListener(event -> {
      try {
        BinderValidationStatus<Empresa> validate = binder.validate();
        if (binder.writeBeanIfValid(miEmpresa)) {
          try {

            mtdGuardar(miEmpresa);
          } catch (Exception e) {
            e.printStackTrace();
          }
        } else {
          String errorText = validate.getFieldValidationStatuses().stream().filter(BindingValidationStatus::isError).map(BindingValidationStatus::getMessage).map(Optional::get).distinct().collect(Collectors.joining(","));
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    reset.addClickListener(event -> {
      // clear fields by setting null
      binder.readBean(null);
      // infoLabel.setText("");
      // doNotCall.setValue(false);
    });



    // agregando los componentes.
    myFormulario.addComponents(noRUC, razonSocial, nombreComercial, direccion, direccion2, telefonos, email, website, nombreCheque);
    myFormulario.addComponent(acciones);

    // set valaues

    // return
    return myFormulario;

  }

  private void mtdGuardar(Empresa emp) {
    Notification noti = new Notification("Datos de la empresa");
    noti.setDelayMsec(3000);
    noti.setPosition(Position.BOTTOM_CENTER);

    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    try {
      if (emp.getId() > 0) {
        emp.setFechaActualizacion(new Date());
        session.update(emp);
        noti.setDescription("Datos de Empresa Actualizados");
        PICOWEBEventBus.post(new EmpresaSave());
      } else {
        emp.setFechaCreacion(new Date());
        session.save(emp);
        noti.setDescription("Empresa creada y Guardada");
        PICOWEBEventBus.post(new ProfileUpdatedEvent());
      }
      // guardar
      session.getTransaction().commit();
      noti.setStyleName("bar success small");
    } catch (Exception e) {
      e.printStackTrace();
      session.getTransaction().rollback();
      noti.setStyleName("bar error small");
      noti.setCaption("Error al intentar guardar los datos : " + e.getMessage());
    } finally {
      session.close();
      noti.show(Page.getCurrent());
    }

  }

  private Empresa getEmpresa() {
    // return (List<Paciente>) pacientesServ.findAll();
    Empresa emp = new Empresa();
    Session session = null;
    try {
      session = HibernateUtil.getSessionFactory().getCurrentSession();

      session.beginTransaction();
      List<Empresa> datos = (List<Empresa>) session.createQuery("from Empresa").list();
      session.getTransaction().commit();
      for (Empresa empresa : datos) {
        log.info("Empresa: " + empresa);
        emp = empresa;
        break;
      }

    } catch (Exception e) {
      session.getTransaction().commit();
      e.printStackTrace();
    } finally {
      session.close();
    }
    return emp;
  }

}

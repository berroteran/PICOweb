package org.jug.nicaragua.picoweb.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Personas")
public class Persona implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 4688056627551185492L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paciente_Sequence")
  @SequenceGenerator(name = "paciente_Sequence", sequenceName = "Paciente_SEQ")
  private Long id;
  private String primerNombre;
  private String segundoNombre;
  private String primerApellido;
  private String segundoApellido;
  private String codigo; // CodigoPaciente
  private String genero;
  private String trato;
  private Date fechaNacimiento;
  private String Profesion;
  private String codID;
  private String pais;
  private String ciudad;
  private String direccion;
  private String email;
  private String telefono;
  private String recomendadoPor;
  private Date ultimaVisita;
  private String Observaciones;
  private Date fechaIngreso;
  private Date fechaCreacion;
  private Long creadoPor;
  // @OneToOne
  // @JoinColumn(name = "IdExpediente")
  // private Expediente expediente;

  //relaciones  
  
  //Metodos
  public Persona() {}

  public Persona(String firstName, String lastName) {
    this.primerNombre = firstName;
    this.primerApellido = lastName;
  }

  public Persona(String firstName, String lastName, Date fechaIngreso) {
    this.primerNombre = firstName;
    this.primerApellido = lastName;
    this.fechaIngreso = fechaIngreso;
  }

  public Persona(String firstName, String segNombre, String lastName, String segApellido) {
    this.primerNombre = firstName;
    this.primerApellido = lastName;
    this.segundoNombre = segNombre;
    this.segundoApellido = segApellido;
  }


  public Long getId() {
    return id ==null ? 0 : id;
  }

  public String getFirstName() {
    return primerNombre;
  }


  public String getPrimerApellido() {
    return primerApellido;
  }

  public String getFullName() {
    return primerNombre + " " + segundoNombre + " " + primerApellido + " " + segundoApellido;
  }


  public void setPrimerApellido(String lastName) {
    this.primerApellido = lastName;
  }

  @Override
  public String toString() {
    return String.format("Customer[id=%d, primerNombre='%s', lastName='%s']", id, primerNombre, primerApellido);
  }

  public Date getFechaIngreso() {
    // TODO Auto-generated method stub
    return fechaIngreso;
  }

  public String getPrimerNombre() {
    return primerNombre;
  }

  public void setPrimerNombre(String primerNombre) {
    this.primerNombre = primerNombre;
  }

  public String getSegundoNombre() {
    return segundoNombre;
  }

  public void setSegundoNombre(String segundoNombre) {
    this.segundoNombre = segundoNombre;
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public String getGenero() {
    return genero;
  }

  public void setGenero(String genero) {
    this.genero = genero;
  }

  public String getTrato() {
    return trato;
  }

  public void setTrato(String trato) {
    this.trato = trato;
  }

  public int getEdad() {
    return 0;
  }


  public Date getUltimaVisita() {
    return ultimaVisita;
  }

  public void setUltimaVisita(Date ultimaVisita) {
    this.ultimaVisita = ultimaVisita;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setFechaIngreso(Date fechaIngreso) {
    this.fechaIngreso = fechaIngreso;
  }


  public String getCiudad() {
    return ciudad;
  }

  public int getExamenesPendientes() {

    return 1;
  }

  public int getPagosPendintes() {
    return 0;
  }

  public String getSegundoApellido() {
    return segundoApellido;
  }

  public void setSegundoApellido(String segundoApellido) {
    this.segundoApellido = segundoApellido;
  }

  public Date getFechaNacimiento() {
    return fechaNacimiento;
  }

  public void setFechaNacimiento(Date fechaNacimiento) {
    this.fechaNacimiento = fechaNacimiento;
  }

  public String getProfesion() {
    return Profesion;
  }

  public void setProfesion(String profesion) {
    Profesion = profesion;
  }

  public String getCodID() {
    return codID;
  }

  /**
   * ID de ciudadano
   * @param codID
   */
  public void setCodID(String codID) {
    this.codID = codID;
  }

  public String getRecomendadoPor() {
    return recomendadoPor;
  }

  public void setRecomendadoPor(String recomendadoPor) {
    this.recomendadoPor = recomendadoPor;
  }

  public String getObservaciones() {
    return Observaciones;
  }

  public void setObservaciones(String observaciones) {
    Observaciones = observaciones;
  }


  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacioin) {
    this.fechaCreacion = fechaCreacioin;
  }

  public Long getCreadoPor() {
    return creadoPor;
  }

  public void setCreadoPor(Long creadoPor) {
    this.creadoPor = creadoPor;
  }

  public String getPais() {
    return pais;
  }

  public void setPais(String pais) {
    this.pais = pais;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public void setCiudad(String ciudad) {
    this.ciudad = ciudad;
  }


}

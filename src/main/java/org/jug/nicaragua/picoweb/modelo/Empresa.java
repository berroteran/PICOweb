package org.jug.nicaragua.picoweb.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Empresa")
public class Empresa implements Serializable {


  /**
   * 
   */
  private static final long serialVersionUID = -8659666232295439753L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "empresa_Sequence")
  @SequenceGenerator(name = "empresa_Sequence", sequenceName = "EMPRESA_SEQ")
  private Long id;
  @Column
  private String noRUC;
  @Column
  private String razonSocial;
  @Column
  private String nombreComercial;
  @Column
  private String siglas;
  @Column
  private String website;
  @Column
  private String direccion;
  @Column
  private int idCiudad;
  @Column
  private int idDepartmaento;
  @Column
  private int idPais;
  @Column
  private String telefono1;
  @Column
  private String telefono2;
  @Column
  private String email;
  @Column
  private String nombreCheque;
  @Column
  private Date fechaCreacion;
  @Column
  private Date fechaActualizacion;



  public Long getId() {
    return id == null ? 0 : id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNoRUC() {
    return noRUC;
  }

  public void setNoRUC(String noRUC) {
    this.noRUC = noRUC;
  }

  public String getRazonSocial() {
    return razonSocial;
  }

  public void setRazonSocial(String razonSocial) {
    this.razonSocial = razonSocial;
  }

  public String getNombreComercial() {
    return nombreComercial;
  }

  public void setNombreComercial(String nombreComercial) {
    this.nombreComercial = nombreComercial;
  }

  public String getSiglas() {
    return siglas;
  }

  public void setSiglas(String siglas) {
    this.siglas = siglas;
  }

  public String getWebsite() {
    return website;
  }

  public void setWebsite(String website) {
    this.website = website;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public int getIdCiudad() {
    return idCiudad;
  }

  public void setIdCiudad(int idCiudad) {
    this.idCiudad = idCiudad;
  }

  public int getIdDepartmaento() {
    return idDepartmaento;
  }

  public void setIdDepartmaento(int idDepartmaento) {
    this.idDepartmaento = idDepartmaento;
  }

  public int getIdPais() {
    return idPais;
  }

  public void setIdPais(int idPais) {
    this.idPais = idPais;
  }

  public String getTelefono1() {
    return telefono1;
  }

  public void setTelefono1(String telefono1) {
    this.telefono1 = telefono1;
  }

  public String getTelefono2() {
    return telefono2;
  }

  public void setTelefono2(String telefono2) {
    this.telefono2 = telefono2;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getNombreCheque() {
    return nombreCheque;
  }

  public void setNombreCheque(String nombreCheque) {
    this.nombreCheque = nombreCheque;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public Date getFechaActualizacion() {
    return fechaActualizacion;
  }

  public void setFechaActualizacion(Date fechaActualizacion) {
    this.fechaActualizacion = fechaActualizacion;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  
  //otros metodos. 
  public String getCiudad() {
    return String.valueOf(idCiudad);
  }

  public void setCiudad(String idCiudad) {
    // this.idCiudad = idCiudad;
  }

  public String getPais() {
    return String.valueOf(idPais);
  }

  public void setPais(String p) {
    //
  }

}

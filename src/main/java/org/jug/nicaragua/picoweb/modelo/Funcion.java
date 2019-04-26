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
@Table(name = "Funciones")
public class Funcion implements Serializable {

  private static final long serialVersionUID = -7470491595562567642L;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "funcion_Sequence")
  @SequenceGenerator(name = "funcion_Sequence", sequenceName = "FUNCION_SEQ")
  private Long id;
  @Column (length=200)
  private String funcion;
  private String descripcion;
  private Date fechaCreacion;
  private Long creadoPor;
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getFuncion() {
    return funcion;
  }
  public void setFuncion(String fun) {
    funcion = fun;
  }
  public String getObservacion() {
    return descripcion;
  }
  public void setObservacion(String observacion) {
    descripcion = observacion;
  }
  public Date getFechaCreacion() {
    return fechaCreacion;
  }
  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }
  public Long getCreadoPor() {
    return creadoPor;
  }
  public void setCreadoPor(Long creadoPor) {
    this.creadoPor = creadoPor;
  }
}

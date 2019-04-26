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
@Table(name = "Permisos")
public class Permiso implements Serializable{

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tarea_Sequence")
  @SequenceGenerator(name = "tarea_Sequence", sequenceName = "TASK_SEQ")
  private Long id;
  @Column
  private String tarea;
  @Column
  private String descripcion;
  @Column
  private Date fechaCreacion;
  @Column
  private Date fechaUltAcceso;
  @Column
  private Byte activo;


}

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
@Table(name = "ExamenesTipo")
public class ExamenTipo implements Serializable{

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "examentipo_Sequence")
  @SequenceGenerator(name = "examentipo_Sequence", sequenceName = "EXAMENTIPO_SEQ")
  private Long id;
  @Column
  private Date fechaCreacion;

}

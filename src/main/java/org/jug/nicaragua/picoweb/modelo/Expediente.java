package org.jug.nicaragua.picoweb.modelo;


import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Expediente implements Serializable{


  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "expediente_Sequence")
  @SequenceGenerator(name = "expediente_Sequence", sequenceName = "EXPEDIENTE_SEQ")
  private Long id;

}

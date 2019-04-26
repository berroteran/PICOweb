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
@Table(name = "Facturas")
public class Factura implements Serializable{

  private static final long serialVersionUID = 5401630964368607190L;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "factura_Sequence")
  @SequenceGenerator(name = "factura_Sequence", sequenceName = "FACTURA_SEQ")
  private Long id;
  @Column
  private Long noFactura;
  private Date fecha;
  private String Concepto;
 
  private Long idPaciente;
  private Boolean financiado;
  private int formaDePago;
  private Date fechaCreacion;
  @Column
  private Date fechaVencimiento;
  private Date fechaPago;
  private int estado;


}

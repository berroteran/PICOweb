package org.jug.nicaragua.picoweb.modelo;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Roles")
public class Rol  implements Serializable{

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rol_Sequence")
  @SequenceGenerator(name = "rol_Sequence", sequenceName = "ROL_SEQ")
  private Long id;
  @Column
  private String rol;
  @Column
  private String descripcion;
  @Column
  private Date fechaCreacion;
  @Column
  private Date fechaUltAcceso;
  @Column
  private Byte activo;
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Permiso> examen = new ArrayList<Permiso>();


  @Override
  public String toString() {
    return rol;
  }

}

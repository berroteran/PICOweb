package org.jug.nicaragua.picoweb.modelo;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Usuarios")
public class Usuario implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -2043312586077146242L;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuarios_Sequesuccessnce")
  @SequenceGenerator(name = "usuarios_Sequence", sequenceName = "usuario_SEQ")
  private Long id;
  @Column(unique = true)
  private String login;
  @Column
  private String nombres;
  @Column
  private String apellidos;
  @Column
  private String clave;
  @Column
  private String titulo;
  @Column
  private Character genero;
  @Column
  private String email;
  @Column
  private String direccion;
  @Column
  private String telefono;
  @Column
  private Date fechaCambioClave;
  @Column
  private Boolean activo;
  @OneToOne
  @JoinColumn(name = "idRol")
  private Rol rol;
  @Column
  private Date fechaCreacion;
  @Column
  private Date fechaUltAcceso;
  @Column
  private Boolean cambiarClave;

  
  
  // Metodos

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getNombres() {
    return nombres;
  }

  public void setNombres(String nombres) {
    this.nombres = nombres;
  }

  public String getApellidos() {
    return apellidos;
  }

  public void setApellidos(String apellidos) {
    this.apellidos = apellidos;
  }

  public String getClave() {
    return clave;
  }

  public void setClave(String clave) {
    this.clave = clave;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public char getGenero() {
    return genero;
  }

  public void setGenero(char genero) {
    this.genero = genero;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public Date getFechaCambioClave() {
    return fechaCambioClave;
  }

  public void setFechaCambioClave(Date fechaCambioClave) {
    this.fechaCambioClave = fechaCambioClave;
  }

  public Boolean getActivo() {
    return activo;
  }

  public void setActivo(Boolean activo) {
    this.activo = activo;
  }



  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public Date getFechaUltAcceso() {
    return fechaUltAcceso;
  }

  public void setFechaUltAcceso(Date fechaUltAcceso) {
    this.fechaUltAcceso = fechaUltAcceso;
  }

  public Boolean getCambiarClave() {
    return cambiarClave;
  }

  public void setCambiarClave(Boolean cambiarClave) {
    this.cambiarClave = cambiarClave;
  }

  public String getNombreCompleto() {
    return nombres + " " + apellidos;
  }
  
  public String getRol() {
    return rol == null ? "" : rol.toString();
  }
  
  public String getEstado() {
    return activo ? "Activo" : "Inactivo";
  }

  public void setRol(Rol rol) {
    this.rol = rol;
  }

  public void setRoleName(String string) {
    
  }
  
}

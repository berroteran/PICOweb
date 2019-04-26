package org.jug.nicaragua.picoweb.modelo;

import java.io.Serializable;

public class Pais implements Serializable{

  private String iso;
  private String code;
  private String name;

  public Pais(String iso, String code, String name) {
    this.iso = iso;
    this.code = code;
    this.name = name;
  }

  public String toString() {
    //return iso + " - " + code + " - " + name.toUpperCase();
    return name.toUpperCase();
  }
  

  public String getIso() {
    return iso;
  }

  public void setIso(String iso) {
    this.iso = iso;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}

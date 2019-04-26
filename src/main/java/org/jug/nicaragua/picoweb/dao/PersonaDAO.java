package org.jug.nicaragua.picoweb.dao;

import org.jug.nicaragua.picoweb.modelo.Persona;

public interface PersonaDAO extends GenericDAO<Persona,Integer>{

  Persona getByEmailAndPassword(String email, String password);
  

}

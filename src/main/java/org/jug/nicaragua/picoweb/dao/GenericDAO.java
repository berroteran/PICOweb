package org.jug.nicaragua.picoweb.dao;

import java.io.Serializable;
import java.util.Collection;


public interface GenericDAO<T, ID extends Serializable> {
  T create() throws BussinessException;

  void saveOrUpdate(T entity) throws BussinessException;

  T finById(ID id) throws BussinessException;
  

  void delete(ID id) throws BussinessException;

  Collection<T> findAll() throws BussinessException;

}

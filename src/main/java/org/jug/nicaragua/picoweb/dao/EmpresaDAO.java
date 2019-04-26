package org.jug.nicaragua.picoweb.dao;

import java.util.List;
import java.util.Optional;
import org.jug.nicaragua.picoweb.modelo.Usuario;

public interface EmpresaDAO {

  Usuario getByEmailAndPassword(String email, String password);
  
  Optional<Usuario> get(long id);
  
  List<Usuario> getAll();
  
  void save(Usuario t);
  
  void update(Usuario u);
  
  void delete(Usuario u);

}

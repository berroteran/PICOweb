package org.jug.nicaragua.picoweb.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import org.jug.nicaragua.picoweb.modelo.Usuario;

public class UsuariosDaoImpl implements UsuarioDao {

  private static final Logger logger = Logger.getLogger(UsuariosDaoImpl.class.getCanonicalName());
  private UsuarioDao userDao;
  private List<Usuario> usuarios = new ArrayList<>();

  public UsuariosDaoImpl() {

  }

  @Override
  public Optional<Usuario> get(long id) {
    return Optional.ofNullable(usuarios.get((int) id));
  }

  @Override
  public List<Usuario> getAll() {
    return usuarios;
  }

  @Override
  public void save(Usuario user) {
    usuarios.add(user);
  }

  @Override
  public void update(Usuario user) {
    // user.setName(Objects.requireNonNull(params[0], "Name cannot be null"));
    // user.setEmail(Objects.requireNonNull(params[1], "Email cannot be null"));

    usuarios.add(user);
  }

  @Override
  public void delete(Usuario user) {
    usuarios.remove(user);
  }

  @Override
  public Usuario getByEmailAndPassword(String email, String password) {
    // TODO Auto-generated method stub
    return null;
  }


}

package org.jug.nicaragua.picoweb.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;



public class GenericDAOImpl<T, ID extends Serializable> implements GenericDAO<T, ID> {

  SessionFactory sessionFactory;

  private final static Logger LOGGER = Logger.getLogger(GenericDAOImpl.class.getName());

  @PersistenceContext
  private Class<T> persistentClass;

  @SuppressWarnings("unchecked")
  public GenericDAOImpl() {
    try {
      sessionFactory = HibernateUtil.getSessionFactory();
      this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }

  }

  @Override
  public T create() throws BussinessException {
    try {
      return getEntityClass().newInstance();
    } catch (InstantiationException | IllegalAccessException ex) {
      throw new RuntimeException(ex);
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public void saveOrUpdate(T entity) throws BussinessException {
    Session session = sessionFactory.getCurrentSession();
    try {
      session.beginTransaction();

      // validando
      ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
      javax.validation.Validator validator = factory.getValidator();

      Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);

      if (constraintViolations.size() > 0) {
        System.out.println("Constraint Violations occurred..");
        for (ConstraintViolation<T> contraints : constraintViolations) {
          System.out.println(contraints.getRootBeanClass().getSimpleName() + "." + contraints.getPropertyPath() + " " + contraints.getMessage());
        }
      }
      session.saveOrUpdate(entity);
      session.getTransaction().commit();

    } catch (javax.validation.ConstraintViolationException cve) {
      try {
        if (session.getTransaction().isActive()) {
          session.getTransaction().rollback();
        }
      } catch (Exception exc) {
        LOGGER.log(Level.WARNING, "Falló al hacer un rollback", exc);
      }
      throw new BussinessException(cve);
      
    } catch (org.hibernate.exception.ConstraintViolationException cve) {
      try {
        if (session.getTransaction().isActive()) {
          session.getTransaction().rollback();
        }
      } catch (Exception exc) {
        LOGGER.log(Level.WARNING, "Falló al hacer un rollback", exc);
      }
      throw new BussinessException(cve);
      
    } catch (RuntimeException ex) {
      try {
        if (session.getTransaction().isActive()) {
          session.getTransaction().rollback();
        }
      } catch (Exception exc) {
        LOGGER.log(Level.WARNING, "Falló al hacer un rollback", exc);
      }
      throw ex;
      
    } catch (Exception ex) {
      
      ex.printStackTrace();
      throw new RuntimeException(ex);
    }finally {
      try {
        if (session.getTransaction().isActive()) {
          session.getTransaction().rollback();
        }
      } catch (Exception exc) {
        LOGGER.log(Level.WARNING, "Falló al hacer un rollback", exc);
      }
    }
  }

  @Override
  public T finById(ID id) throws BussinessException {
    Session session = sessionFactory.getCurrentSession();
    try {
      session.beginTransaction();
      T entity = (T) session.get(getEntityClass(), id);
      session.getTransaction().commit();

      return entity;
    } catch (javax.validation.ConstraintViolationException cve) {
      try {
        if (session.getTransaction().isActive()) {
          session.getTransaction().rollback();
        }
      } catch (Exception exc) {
        LOGGER.log(Level.WARNING, "Falló al hacer un rollback", exc);
      }
      throw new BussinessException(cve);
    } catch (org.hibernate.exception.ConstraintViolationException cve) {
      try {
        if (session.getTransaction().isActive()) {
          session.getTransaction().rollback();
        }
      } catch (Exception exc) {
        LOGGER.log(Level.WARNING, "Falló al hacer un rollback", exc);
      }
      throw new BussinessException(cve);
    } catch (RuntimeException ex) {
      try {
        if (session.getTransaction().isActive()) {
          session.getTransaction().rollback();
        }
      } catch (Exception exc) {
        LOGGER.log(Level.WARNING, "Falló al hacer un rollback", exc);
      }
      throw ex;
    } catch (Exception ex) {
      try {
        if (session.getTransaction().isActive()) {
          session.getTransaction().rollback();
        }
      } catch (Exception exc) {
        LOGGER.log(Level.WARNING, "Falló al hacer un rollback", exc);
      }
      throw new RuntimeException(ex);
    }finally {
      try {
        if (session.getTransaction().isActive()) {
          session.getTransaction().rollback();
        }
      } catch (Exception exc) {
        LOGGER.log(Level.WARNING, "Falló al cerrar la sesion", exc);
      }
    }
  }

  @Override
  public void delete(ID id) throws BussinessException {
    Session session = sessionFactory.getCurrentSession();
    try {
      session.beginTransaction();
      T entity = (T) session.get(getEntityClass(), id);
      if (entity == null) {
        throw new BussinessException(new BussinessMessage(null, "Los datos a borrar ya no existen"));
      }
      session.delete(entity);
      session.getTransaction().commit();
    } catch (javax.validation.ConstraintViolationException cve) {
      try {
        if (session.getTransaction().isActive()) {
          session.getTransaction().rollback();
        }
      } catch (Exception exc) {
        LOGGER.log(Level.WARNING, "Falló al hacer un rollback", exc);
      }
      throw new BussinessException(cve);
    } catch (org.hibernate.exception.ConstraintViolationException cve) {
      try {
        if (session.getTransaction().isActive()) {
          session.getTransaction().rollback();
        }
      } catch (Exception exc) {
        LOGGER.log(Level.WARNING, "Falló al hacer un rollback", exc);
      }
      throw new BussinessException(cve);
    } catch (BussinessException ex) {
      try {
        if (session.getTransaction().isActive()) {
          session.getTransaction().rollback();
        }
      } catch (Exception exc) {
        LOGGER.log(Level.WARNING, "Falló al hacer un rollback", exc);
      }
      throw ex;
    } catch (RuntimeException ex) {
      try {
        if (session.getTransaction().isActive()) {
          session.getTransaction().rollback();
        }
      } catch (Exception exc) {
        LOGGER.log(Level.WARNING, "Falló al hacer un rollback", exc);
      }
      throw ex;
    } catch (Exception ex) {
      try {
        if (session.getTransaction().isActive()) {
          session.getTransaction().rollback();
        }
      } catch (Exception exc) {
        LOGGER.log(Level.WARNING, "Falló al hacer un rollback", exc);
      }
      throw new RuntimeException(ex);
    }finally {
      try {
        if (session.getTransaction().isActive()) {
          session.getTransaction().rollback();
        }
      } catch (Exception exc) {
        LOGGER.log(Level.WARNING, "Falló al cerrar la sesion", exc);
      }
    }
  }

  @Override
  public Collection<T> findAll() throws BussinessException {
    Session session = sessionFactory.getCurrentSession();
    try {
      session.beginTransaction();

      Query query = session.createQuery("SELECT e FROM " + getEntityClass().getName() + " e");
      List<T> entities = query.list();

      return entities;

    } catch (javax.validation.ConstraintViolationException cve) {
      try {
        if (session.getTransaction().isActive()) {
          session.getTransaction().rollback();
        }
      } catch (Exception exc) {
        LOGGER.log(Level.WARNING, "Falló al hacer un rollback", exc);
      }
      throw new BussinessException(cve);

    } catch (org.hibernate.exception.ConstraintViolationException cve) {
      try {
        if (session.getTransaction().isActive()) {
          session.getTransaction().rollback();
        }
      } catch (Exception exc) {
        LOGGER.log(Level.WARNING, "Falló al hacer un rollback", exc);
      }
      throw new BussinessException(cve);
    
    } catch (RuntimeException ex) {
      try {
        if (session.getTransaction().isActive()) {
          session.getTransaction().rollback();
        }
      } catch (Exception exc) {
        LOGGER.log(Level.WARNING, "Falló al hacer un rollback", exc);
      }
      throw ex;
    
    } catch (Exception ex) {
      try {
        if (session.getTransaction().isActive()) {
          session.getTransaction().rollback();
        }
      } catch (Exception exc) {
        LOGGER.log(Level.WARNING, "Falló al hacer un rollback", exc);
      }
      throw new RuntimeException(ex);
    }finally {
      try {
        if (session.getTransaction().isActive()) {
          session.getTransaction().rollback();
        }
      } catch (Exception exc) {
        LOGGER.log(Level.WARNING, "Falló al cerrar la sesion", exc);
      }
    }


  }


  private Class<T> getEntityClass() {
    return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
  }

}

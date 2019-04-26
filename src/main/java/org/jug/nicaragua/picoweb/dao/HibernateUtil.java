package org.jug.nicaragua.picoweb.dao;

import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.context.internal.ThreadLocalSessionContext;
import org.hibernate.service.ServiceRegistry;

/**
 * @author imssbora
 */
public class HibernateUtil {
  private static StandardServiceRegistry registroServicio;
  private static SessionFactory sessionFactory;
  private static final Logger logger = Logger.getLogger(HibernateUtil.class.getName());

  public static synchronized SessionFactory getSessionFactory() {
    if (sessionFactory == null) {
      try {
        // Create the SessionFactory from hibernate.cfg.xml
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        System.out.println("Hibernate Configuration loaded");

        // Agregando entidades
        //configuration.addAnnotatedClass(org.jug.nicaragua.picoweb.modelo.Paciente.class);
        configuration.addAnnotatedClass(org.jug.nicaragua.picoweb.modelo.Usuario.class);
        configuration.addAnnotatedClass(org.jug.nicaragua.picoweb.modelo.Rol.class);
        configuration.addAnnotatedClass(org.jug.nicaragua.picoweb.modelo.Permiso.class);
        configuration.addAnnotatedClass(org.jug.nicaragua.picoweb.modelo.Empresa.class);

        configuration.addAnnotatedClass(org.jug.nicaragua.picoweb.modelo.Funcion.class);
        configuration.addAnnotatedClass(org.jug.nicaragua.picoweb.modelo.Proceso.class);
        configuration.addAnnotatedClass(org.jug.nicaragua.picoweb.modelo.Persona.class);
        configuration.addAnnotatedClass(org.jug.nicaragua.picoweb.modelo.Departamento.class);
        configuration.addAnnotatedClass(org.jug.nicaragua.picoweb.modelo.Empleado.class);
        configuration.addAnnotatedClass(org.jug.nicaragua.picoweb.modelo.EvalCapacitacion.class);


        // Create registry
        // registry = new StandardServiceRegistryBuilder().configure().build();
        
        registroServicio = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        System.out.println("Hibernate serviceRegistry created");

        // Create MetadataSources
        MetadataSources sources = new MetadataSources(registroServicio);

        // Create Metadata
        Metadata metadata = sources.getMetadataBuilder().build();

        // Create SessionFactory
        //sessionFactory = metadata.getSessionFactoryBuilder().build();
        try {
          sessionFactory = configuration.buildSessionFactory(registroServicio);
        } catch (Exception e) {
           
        }

      } catch (Exception e) {
        e.printStackTrace();
        if (registroServicio != null) {
          StandardServiceRegistryBuilder.destroy(registroServicio);
        }
      }
    }
    return sessionFactory;
  }

  public static void shutdown() {
    if (registroServicio != null) {
      StandardServiceRegistryBuilder.destroy(registroServicio);
    }
  }
  
  
  //otros metodos.
  public static void openSessionAndBindToThread() {
    Session session = sessionFactory.openSession();
    ThreadLocalSessionContext.bind(session);
}


  public static synchronized void buildSessionFactory() {
    if (sessionFactory == null) {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.setProperty("hibernate.current_session_context_class", "thread");
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }
}
  
public static SessionFactory getSessionFactory2() {
    if (sessionFactory==null)  {
        buildSessionFactory();
    }
    return sessionFactory;
}

public static void closeSessionAndUnbindFromThread() {
    Session session = ThreadLocalSessionContext.unbind(sessionFactory);
    if (session!=null) {
        session.close();
    }
}

public static void closeSessionFactory() {
    if ((sessionFactory!=null) && (sessionFactory.isClosed()==false)) {
        sessionFactory.close();
    }
}
}

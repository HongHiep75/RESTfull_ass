package utils;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
  private final static SessionFactory FACTORY;
  
  static {
	  Configuration conf = new Configuration();
	  Properties pros = new  Properties();
	  pros.put(Environment.DIALECT, "org.hibernate.dialect.OracleDialect");
	  pros.put(Environment.DRIVER, "oracle.jdbc.driver.OracleDriver");
	  pros.put(Environment.URL, "jdbc:oracle:thin:@localhost:1521:xe");
	  pros.put(Environment.USER, "HIEP1");
	  pros.put(Environment.PASS, "12345");
	  
	  conf.setProperties(pros);
//	  conf.addAnnotatedClass(enity.Test.class);
	  conf.addAnnotatedClass(enity.Person.class);
	  conf.addAnnotatedClass(enity.Student.class);
	  ServiceRegistry registry = new StandardServiceRegistryBuilder()
			                            .applySettings(conf.getProperties())
			                            .build();
	  FACTORY = conf.buildSessionFactory(registry);
  }
  
  public static SessionFactory getSessionFactory() {
	  return FACTORY;
  }
  
}

package javamavenhibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class HibernateUtil {
	
	public static EntityManagerFactory factor = null;
	
	static {
		init();
	}
	
	private static void init() {
		
		try {
			if(factor == null) {
				
				factor = Persistence.createEntityManagerFactory("java-maven-hibernate");	
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static EntityManager getEntityManager() {
		return factor.createEntityManager(); /*Prover a parte da persistencia*/
	}
	
	public static Object getPrimarykey(Object entity) { // Retorna a PK
		return factor.getPersistenceUnitUtil().getIdentifier(entity);
	}
	
	
}

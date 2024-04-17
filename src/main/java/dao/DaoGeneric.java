package dao;

import java.util.List;

import org.hibernate.Session;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import javamavenhibernate.HibernateUtil;

public class DaoGeneric<E> {

	private EntityManager entityManager = HibernateUtil.getEntityManager();
	
	
	public void salvar(E entidade) {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.persist(entidade);
		entityTransaction.commit();
	}
	
	/*Busca 1*/
	
	public E consulta(E entidade) {
		Object id = HibernateUtil.getPrimarykey(entidade);
		E e = (E) entityManager.find(entidade.getClass(), id);
		return e;
	}
	
	/* Busca 2
	public E consultaId(Long id, E entidade) {
		E e = (E) entityManager.find(entidade.getClass(), id);
		return e;
	}
	*/
	
	/*Update*/
	public E UpdateMerge(E entidade) {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		E entidadeSalva = entityManager.merge(entidade);
		entityTransaction.commit();
		return entidadeSalva;
	}
	
	/*Delete*/
	
	public void deletarId(E entidade) {
		Object id = HibernateUtil.getPrimarykey(entidade);
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		entityManager.createNativeQuery("DELETE FROM "+ entidade.getClass().getSimpleName().toLowerCase() + " WHERE id = :id").setParameter("id", id).executeUpdate();
		
		entityTransaction.commit();
	}
	
	public List<E> consultaTodos(Class<E> entidade){
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		List<E> retornoConsulta = entityManager.createNativeQuery("SELECT * FROM "+ entidade.getSimpleName().toLowerCase(), entidade).getResultList();
		entityTransaction.commit();
		
		return retornoConsulta;	
	}

	
	public EntityManager getEntityManager() {
		return entityManager;
	}

}

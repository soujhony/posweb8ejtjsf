package dao;

import javax.persistence.Query;

import modelo.Sala;
import util.JPAUtil;

public class JPASalaDAO extends JPACrudDao<Sala> {
	
	public JPASalaDAO(JPAUtil jpa){
		super(jpa, Sala.class);
	}
	
	public boolean verificarSalaUnicaBloco(Sala sala) throws Exception{
		Query q = jpa.getEntityManager().createNamedQuery("salaCampus");
		q.setParameter(1, sala.getCampus());
		q.setParameter(2, sala.getBloco());
		q.setParameter(3, sala.getNumeroSala());
		q.setParameter(4, sala.getCodigo());
		Long contador = (Long) q.getSingleResult();
		if(contador > 0) {
			return false;
		}
		else
			return true;
	}

}

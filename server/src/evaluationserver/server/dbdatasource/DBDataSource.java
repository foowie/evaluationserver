package evaluationserver.server.dbdatasource;

import evaluationserver.server.entities.Solution;
import evaluationserver.server.entities.SystemReply;
import evaluationserver.server.datasource.DataSource;
import evaluationserver.server.datasource.Result;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class DBDataSource implements DataSource {
	private static final Logger logger = Logger.getLogger(DBDataSource.class.getPackage().getName());	

	protected final EntityManager em;

	public DBDataSource(EntityManager em) {
		this.em = em;
	}

	@Override
	public void setResult(Solution solution, Result result) {
		logger.log(Level.FINER, ("Update result of solution " + solution.getId()));
		synchronized(em) {
			// TODO - check for noresultexception
			SystemReply reply = (SystemReply) em.createQuery("SELECT r FROM SystemReply r WHERE r.key = :key").setParameter("key", result.getReply().getCode()).getSingleResult();

			if(solution.getEvaluationLockUntil().before(new Date())) {
				// this solving goes unexpectedly long ... throw it away
				logger.log(Level.WARNING, ("Testing of solution goes unexpectedly long and was throwed away, solution id=" + solution.getId()));
				return;
			}
			
			em.getTransaction().begin();
			solution.setDateEvaluated(result.getStart());
			solution.setMemory(result.getMemory());
			solution.setTime(result.getTime());
			solution.setSystemReply(reply);
			em.flush();
			em.getTransaction().commit();
		}
	}

	@Override
	public Solution takeNextUnresolvedSolution() {
		logger.log(Level.FINER, ("Take next unresolved solution"));
		Solution solution = null;
		synchronized(em) {
			while(solution == null) {
				// create transaction
				// take next free solution
				try {
					solution = (Solution) em.createQuery("SELECT s FROM Solution s WHERE s.dateEvaluated IS NULL AND (s.evaluationLockUntil IS NULL OR s.evaluationLockUntil < CURRENT_TIMESTAMP)").setMaxResults(1).getSingleResult();
				} catch(NoResultException e) {
					logger.log(Level.FINER, ("No free solution to took"));
					return null;
				}
				// lock it
				em.getTransaction().begin();
				em.lock(solution, LockModeType.PESSIMISTIC_WRITE);
				Query query = em.createQuery("SELECT s.dateEvaluated FROM Solution s WHERE s.id = :id");
				query.setParameter("id", solution.getId());
				try {
					Date date = (Date) query.getSingleResult();
					if(date != null) {
						logger.log(Level.FINE, ("Record taken by another thread during retrieve, id=" + solution.getId()));
						em.getTransaction().rollback();
						continue;
					}
				} catch(NoResultException e) {
					// something goes wrong ...
					logger.log(Level.FINE, ("Record deleted during retrieve, id=" + solution.getId()));
					em.getTransaction().rollback();
					continue;
				}
				
				// update evaluationLockUntil
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.MILLISECOND, solution.getTime());
				calendar.add(Calendar.MINUTE, 5);
				solution.setEvaluationLockUntil(calendar.getTime());
				em.flush();
				em.getTransaction().commit();
			}
		}
		logger.log(Level.FINER, ("Unresolved solution found id=" + solution.getId()));
		return solution;
	}


	
}

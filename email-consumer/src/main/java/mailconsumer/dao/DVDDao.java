package mailconsumer.dao;

import entity.DVD;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by Vlad on 18-Nov-17.
 */
public class DVDDao {

    private SessionFactory sessionFactory;

    public DVDDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public DVD getDVD(Integer id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<DVD> dvds = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("FROM DVD where id=:id");
            query.setParameter("id",id);
            dvds = query.list();
            transaction.commit();
        }catch (HibernateException e) {
            if(transaction != null)
                transaction.rollback();
        }finally {
            session.close();
        }
        return (dvds != null && !dvds.isEmpty()) ? dvds.get(0) : null;
    }
}

package mailconsumer.dao;

import entity.Email;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by Vlad on 18-Nov-17.
 */
public class EmailDao {

    private SessionFactory sessionFactory;

    public EmailDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Email> getEmails() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<Email> emails = null;
        try{
            transaction = session.beginTransaction();
            emails = session.createQuery("FROM Email").list();
            transaction.commit();
        }catch (HibernateException e) {
            if(transaction != null)
                transaction.rollback();
        }finally {
            session.close();
        }
        return emails;
    }
}

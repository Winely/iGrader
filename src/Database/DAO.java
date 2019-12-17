package Database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.Serializable;
import java.util.List;

public class DAO {
    private static SessionFactory factory;

    public DAO(){
        init();
    }

    private void init(){
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml") // configures settings from hibernate.cfg.xml
                .build();
        try {
            factory = new MetadataSources(registry).
                    buildMetadata().
                    buildSessionFactory();
        }
        catch (Exception e) {
            StandardServiceRegistryBuilder.destroy( registry );
            e.printStackTrace();
        }
    }

    protected static Session getSession(){
        return factory.openSession();
    }

    /**
     * add new line
     * @param e new line
     */
    public <E> void save(E e){
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        session.save(e);
        tx.commit();
        session.close();
    }

    /**
     * delete line
     * @param e target entity
     * @param pk primary key
     */
    public <E> void delete(Class<E> e, Serializable pk){
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        E entity = this.findById(e, pk);
        if(entity!=null){
            session.delete(entity);
            tx.commit();
        }
        else{
            System.out.println("Data with primary key"+ pk.toString() +" does not exist, cannot delete.");
        }
        session.close();
    }

    /**
     * find data
     * @param e entity
     * @param pk primary key
     */
    public <E> E findById(Class<E> e, Serializable pk){
        Session session = getSession();
        E result = session.get(e,pk);
        session.close();
        return result;
    }

    /**
     * update data
     * @param o updated data
     */
    public void update(Object o){
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        session.update(o);
        tx.commit();
        session.close();
    }

    /***
     * refresh the object from database
     * @param o object to be refreshed
     */
    public void refresh(Object o){
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        session.refresh(o);
        tx.commit();
        session.close();
    }

    /**
     * query
     * @param hql SQL query string
     */
    public static List query(String hql){
        Session session = getSession();
        List result = session.createQuery(hql).list();
        session.close();
        return result;
    }

    public <E> void saveMany(List<E> list){
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        for (E e : list) {
            session.save(e);
        }
        tx.commit();
        session.close();
    }
}

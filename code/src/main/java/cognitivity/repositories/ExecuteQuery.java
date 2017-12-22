//package cognitivity.repositories;
//
//import java.util.*;
//import javax.persistence.*;
//
//
//import org.hibernate.*;
//import org.hibernate.query.NativeQuery;
//
//
//public class ExecuteQuery{
//    /**
//     * Sets new value of cognitiveTest
//     * @param stringQuery:
//     *           the sql query that we want to run
//     * @param factory:
//     *               opened SessionFactory that opens when someone wants to read data
//     * @return List of objects that answers the query
//     */
//    public List execute(String stringQuery, SessionFactory factory){
//        Session session = factory.openSession();
//        Transaction tx = null;
//        List data = null;
//        try {
//            tx = session.beginTransaction();
//            NativeQuery query = session.createNativeQuery( stringQuery );
//            data = query.list();
//        } catch(HibernateException e) {
//            if( tx != null)
//                tx.rollback();
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
//        return data;
//    }
//}

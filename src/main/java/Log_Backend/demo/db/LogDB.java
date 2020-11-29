package Log_Backend.demo.db;

import Log_Backend.demo.bo.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;

public class LogDB {
    public static boolean createLog(String title, String content, UsersEntity currUser) {
        LogEntity newLog = new LogEntity();
        newLog.setTitle(title);
        newLog.setContent(content);
        UsersEntity user = currUser;
        newLog.setUserId(user);
        Session session = HibernateUtil.getFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(newLog);
        session.save(newLog);
        transaction.commit();
        session.close();
        return true;
    }
    public static LogEntity getLog(int logID){
        Session session = null;
        try{
            session = HibernateUtil.getFactory().openSession();
            session.beginTransaction();
            LogEntity result = (LogEntity) session.createQuery
                    ("From LogEntity WHERE id = '" + logID + "'")
                    .uniqueResult();
            session.getTransaction().commit();
            return result;
        }finally{
            session.close();
        }
    }

    public static ArrayList<Log> getLogs(UsersEntity currentUser){
        Session session = null;
        try{
            session = HibernateUtil.getFactory().openSession();
            session.beginTransaction();
            ArrayList<LogEntity> result = (ArrayList<LogEntity>)
                    session.createQuery("From LogEntity where userId = '" + currentUser.getId() + "'")
                            .list();
            return entityToLog(result);
        }finally{
            session.close();
        }
    }

    public static ArrayList<Log> entityToLog(ArrayList<LogEntity> temp){
        ArrayList<Log> result = new ArrayList<>();
        for(int i = 0; i < temp.size(); i++){
            User u = new User();
            u.setUsername(temp.get(i).getUserId().getUsername());
            u.setPassword(temp.get(i).getUserId().getPassword());
            Log t = new Log(temp.get(i).getId(), temp.get(i).getTitle(), temp.get(i).getContent(), u);
            result.add(t);
        }
        return result;
    }

    public static ArrayList<Log> getAllLogs() {
        Session session = null;
        try{
            session = HibernateUtil.getFactory().openSession();
            session.beginTransaction();
            ArrayList<LogEntity> result = (ArrayList<LogEntity>)
                    session.createQuery("From LogEntity ").list();
            return entityToLog(result);
        }finally{
            session.close();
        }
    }

    public static ArrayList<Log> getOtherLogs(String currentUser) {
        Session session = null;
        try{
            session = HibernateUtil.getFactory().openSession();
            session.beginTransaction();
            ArrayList<LogEntity> result = (ArrayList<LogEntity>)
                    session.createQuery("From LogEntity WHERE userId not in " +
                            "(select id from UsersEntity WHERE username = '" + currentUser + "')").list();
            return entityToLog(result);
        }finally{
            session.close();
        }
    }
}

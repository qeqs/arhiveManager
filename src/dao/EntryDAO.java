package dao;

import entities.ArhiveEntity;
import entities.EntryEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EntryDAO extends AbstractDAO<EntryEntity> {
    public List<EntryEntity> getAllByArhive(ArhiveEntity arhiveEntity) {
        Session session = null;
        List<EntryEntity> list = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
           int id = arhiveEntity.getId();
//            Query query = session.createQuery(
//                    "select e from entry e " +
//                            "where e.id =:id"
//            )
//                    .setInteger("id",id);
            list= this.getAll();
            ArrayList<EntryEntity> tempList = new ArrayList<EntryEntity>();
            for (EntryEntity entry:list) {
                if(entry.getArhive().getId() != id){
                    tempList.add(entry);
                }
            }
            list.removeAll(tempList);
        } catch (Exception e) {
            System.out.println("Select error");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return list;
    }
}

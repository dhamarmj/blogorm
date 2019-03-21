package pucmm.edu.dhamarmj.Services;

import pucmm.edu.dhamarmj.Encapsulation.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

public class UserServices extends DatabaseServices<User> {
    private static UserServices instancia;

    private UserServices() {
        super(User.class);
    }

    public static UserServices getInstancia() {
        if (instancia == null) {
            instancia = new UserServices();
        }
        return instancia;
    }


    public User getUser(String username, String password) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select e from User e where e.username like :username and e.password like :password", User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        List<User> list = query.getResultList();
        if (list.size() >0)
            return list.get(0);
        else
            return null;

    }


}

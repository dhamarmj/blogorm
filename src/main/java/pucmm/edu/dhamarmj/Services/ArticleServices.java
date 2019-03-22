package pucmm.edu.dhamarmj.Services;

import org.eclipse.jetty.server.session.JDBCSessionManager;
import pucmm.edu.dhamarmj.Encapsulation.Article;
import pucmm.edu.dhamarmj.Encapsulation.Label;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class ArticleServices extends DatabaseServices<Article> {
    private static ArticleServices instancia;
    int pageSize = 5;
    private ArticleServices() {
        super(Article.class);
    }

    public static ArticleServices getInstancia() {
        if (instancia == null) {
            instancia = new ArticleServices();
        }
        return instancia;
    }

    //    /**
//     *
//     * @param nombre
//     * @return
//     */s
//    public List<Article> findAllByLabel(int id){
//        EntityManager em = getEntityManager();
//        Query query = em.createQuery("select e from Article e " +
//                "inner join ARTICLE_LABEL a on a.FK_LABEL = e.id " +
//                "where a.FK_LABEL like :labelId");
//        query.setParameter("labelId", id);
//        List<Article> lista = query.getResultList();
//        return lista;
//    }
    public List<Article> getLabelArticles(long labelid, int lastPageNumber) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select e from Article e join e.labels l where l.id like :labelid order by e.fecha desc", Article.class);
        query.setParameter("labelid", labelid);
        query.setFirstResult((lastPageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public List<Article> paginateArticles(int lastPageNumber) {
        EntityManager em = getEntityManager();
        Query selectQuery = em.createQuery("From Article e order by e.fecha desc");
        selectQuery.setFirstResult((lastPageNumber - 1) * pageSize);
        selectQuery.setMaxResults(pageSize);
        return selectQuery.getResultList();
    }


}

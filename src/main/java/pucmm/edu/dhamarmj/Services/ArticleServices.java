package pucmm.edu.dhamarmj.Services;

import pucmm.edu.dhamarmj.Encapsulation.Article;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ArticleServices extends DatabaseServices<Article> {
    private static ArticleServices instancia;

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

}

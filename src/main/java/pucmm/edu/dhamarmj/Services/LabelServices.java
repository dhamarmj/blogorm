package pucmm.edu.dhamarmj.Services;

import pucmm.edu.dhamarmj.Encapsulation.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class LabelServices extends DatabaseServices<Label> {
    private static LabelServices instancia;

    private LabelServices() {
        super(Label.class);
    }

    public static LabelServices getInstancia() {
        if (instancia == null) {
            instancia = new LabelServices();
        }
        return instancia;
    }

    public Label getLabel(String labelName) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select e from Label e where e.labelText like :labelName", Label.class);
        query.setParameter("labelName", labelName);
        List<Label> list = query.getResultList();
        if (list.size() > 0)
            return list.get(0);
        else
            return null;

    }

    public Label InsertLabel(Label etiqueta) {
        String label = etiqueta.getLabelText().toLowerCase();
        etiqueta.setLabelText(label.substring(0,1).toUpperCase() + label.substring(1));
        Label l = getLabel(etiqueta.getLabelText());
        if (l == null) {
            crear(etiqueta);
            l = getLabel(etiqueta.getLabelText());
        }
        return l;
    }


}

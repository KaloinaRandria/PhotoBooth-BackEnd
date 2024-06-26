package org.photobooth.restapi.service;

import org.entityframework.dev.Metric;
import org.photobooth.restapi.http.data.ServiceData;
import org.photobooth.restapi.http.data.ValueRangeData;
import org.photobooth.restapi.model.Notification;
import org.photobooth.restapi.model.ServComp;
import org.photobooth.restapi.model.TarifComp;
import org.photobooth.restapi.model.stat.ServCompTarif;

import java.util.List;

public class ServCompService extends Service{
    public ServCompService() {
        super();
    }

    public List<ServComp> getAllService() throws Exception {
        List<ServComp> servComps =  this.getAll(ServComp.class);
        for (ServComp servComp : servComps) {
            getAndSetTarif(servComp);
        }
        return servComps;
    }

    private void getAndSetTarif(ServComp servComp) throws Exception {
        List<ServCompTarif> servCompTarifs = getNgContext().executeToList(ServCompTarif.class, "SELECT * FROM v_tarif_service where id_comp_service = ?", servComp.getId_comp_service());
        servComp.setTarif(servCompTarifs);
    }

    public String insertService(ServComp servComp) throws Exception {
        return (String) getNgContext().save(servComp);
    }

    public void deleteService(String idService) throws Exception {
        getNgContext().delete(ServComp.class, idService);
    }

    public void updateService(ServComp servComp) throws Exception {
        getNgContext().update(servComp);
    }

    public void newService(ServiceData serviceData) throws Exception {
        try {
            getNgContext().setAutoCommit(false);
            ServComp serv = new ServComp();
            serv.setColor(serviceData.getColor());
            serv.setPrix_unitaire(0.0);
            serv.setIcon(serviceData.getIcon());
            serv.setIntitule(serviceData.getLabel());
            Metric.print(serv);
            String id_service = (String) getNgContext().save(serv);

            for (ValueRangeData valueRangeData : serviceData.getValueRange()) {
                TarifComp tarifComp = new TarifComp();
                tarifComp.setId_comp_service(id_service);
                tarifComp.setId_value_ranges(valueRangeData.getId());
                tarifComp.setPrix(valueRangeData.getPrice());
                getNgContext().save(tarifComp);
            }

            Notification notification = new Notification();
            notification.setLibele("New service added : " + id_service);
            notification.setType("info");
            notification.setIcon("mdi mdi-bookmark-plus-outline");
            getNgContext().save(notification);

            getNgContext().commit();
            getNgContext().setAutoCommit(true);
        } catch (Exception e) {
            getNgContext().rollBack();
            getNgContext().commit();
            getNgContext().setAutoCommit(true);
            throw e;
        }
    }
}

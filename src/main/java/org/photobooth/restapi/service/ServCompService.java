package org.photobooth.restapi.service;

import org.photobooth.restapi.http.data.ServiceData;
import org.photobooth.restapi.http.data.ValueRangeData;
import org.photobooth.restapi.model.ServComp;
import org.photobooth.restapi.model.TarifComp;

import java.util.List;

public class ServCompService extends Service{
    public ServCompService() {
        super();
    }

    public List<ServComp> getAllService() throws Exception {
        return this.getAll(ServComp.class);
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
        ServComp serv = new ServComp();
        serv.setIcon(serviceData.getIcon());
        serv.setIntitule(serviceData.getLabel());
        String id_service = (String) getNgContext().save(serv);

        for (ValueRangeData valueRangeData : serviceData.getValueRange()) {
            TarifComp tarifComp = new TarifComp();
            tarifComp.setId_comp_service(id_service);
            tarifComp.setId_value_ranges(valueRangeData.getId());
            tarifComp.setPrix(valueRangeData.getPrice());
            getNgContext().save(tarifComp);
        }
    }


}

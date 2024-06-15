package org.photobooth.restapi.service;

import org.photobooth.restapi.model.ServComp;

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
}

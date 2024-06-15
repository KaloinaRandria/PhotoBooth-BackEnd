package org.photobooth.restapi.service;

import org.entityframework.error.EntityNotFoundException;
import org.photobooth.restapi.model.Role;

import java.util.List;
import java.util.Optional;

public class RoleService extends Service{
    public RoleService() {
        super();
    }

    public List<Role> getAllRole() throws Exception {
        return getNgContext().findAll(Role.class);
    }

    public Optional<Role> getRoleById(String id) throws Exception {
        try {
            Role role = getNgContext().findById(id, Role.class);
            return Optional.ofNullable(role);
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }

    public String save(Role role) throws Exception {
        return (String) getNgContext().save(role);
    }

    public void update(Role role) throws Exception {
        getNgContext().update(role);
    }

    public void delete(String id) throws Exception {
        getNgContext().delete(Role.class, id);
    }
}

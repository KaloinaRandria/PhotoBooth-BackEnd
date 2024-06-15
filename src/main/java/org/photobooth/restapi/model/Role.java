package org.photobooth.restapi.model;

import org.entityframework.tools.Col;
import org.entityframework.tools.Primary;
import org.entityframework.tools.Table;

@Table(name = "role")
public class Role {

    @Primary(isSequence = true, sequenceName = "role_seq", prefixe = "ROLE_")
    private String id_role;
    private String intitule;

    public Role() { }

    public Role(String id_role, String intitule) {
        setId_role(id_role);
        setIntitule(intitule);
    }

    public String getId_role() {
        return id_role;
    }

    public void setId_role(String id_role) {
        this.id_role = id_role;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }
}

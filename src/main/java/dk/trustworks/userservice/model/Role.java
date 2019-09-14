package dk.trustworks.userservice.model;


import dk.trustworks.userservice.model.enums.RoleType;
import dk.trustworks.userservice.utils.BeanUtils;
import io.vertx.core.json.JsonObject;

/**
 * Created by hans on 23/06/2017.
 */
public class Role {

    private String uuid;

    private RoleType role;

    public Role() {
    }

    public Role(String uuid, RoleType role) {
        this.uuid = uuid;
        this.role = role;
    }

    public Role(JsonObject json) {
        BeanUtils.populateFields(this, json);
    }

    public Role(RoleType role) {
        this.role = role;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Role{" + "uuid='" + uuid + '\'' +
                ", role=" + role +
                '}';
    }
}

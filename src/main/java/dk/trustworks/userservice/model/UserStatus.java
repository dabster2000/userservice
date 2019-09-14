package dk.trustworks.userservice.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dk.trustworks.userservice.model.enums.ConsultantType;
import dk.trustworks.userservice.model.enums.StatusType;
import dk.trustworks.userservice.utils.BeanUtils;
import dk.trustworks.userservice.utils.LocalDateDeserializer;
import dk.trustworks.userservice.utils.LocalDateSerializer;
import io.vertx.core.json.JsonObject;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Created by hans on 23/06/2017.
 */
public class UserStatus {

    private String uuid;
    private ConsultantType type;
    private StatusType status;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate statusdate;
    private int allocation;

    private String useruuid;

    public UserStatus() {
    }

    public UserStatus(ConsultantType type, StatusType status, LocalDate statusdate, int allocation, String useruuid) {
        this.useruuid = useruuid;
        uuid = UUID.randomUUID().toString();
        this.type = type;
        this.status = status;
        this.statusdate = statusdate;
        this.allocation = allocation;
    }

    public UserStatus(String uuid, ConsultantType type, StatusType status, LocalDate statusdate, int allocation, String useruuid) {
        this.uuid = uuid;
        this.type = type;
        this.status = status;
        this.statusdate = statusdate;
        this.allocation = allocation;
        this.useruuid = useruuid;
    }

    public UserStatus(JsonObject jsonObject) {
        BeanUtils.populateFields(this, jsonObject);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public LocalDate getStatusdate() {
        return statusdate;
    }

    public void setStatusdate(LocalDate statusdate) {
        this.statusdate = statusdate;
    }

    public int getAllocation() {
        return allocation;
    }

    public void setAllocation(int allocation) {
        this.allocation = allocation;
    }

    public ConsultantType getType() {
        return type;
    }

    public void setType(ConsultantType type) {
        this.type = type;
    }

    public String getUseruuid() {
        return useruuid;
    }

    public void setUseruuid(String useruuid) {
        this.useruuid = useruuid;
    }

    @Override
    public String toString() {
        return "UserStatus{" +
                "uuid='" + uuid + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", statusdate=" + statusdate +
                ", allocation=" + allocation +
                ", useruuid='" + useruuid + '\'' +
                '}';
    }
}

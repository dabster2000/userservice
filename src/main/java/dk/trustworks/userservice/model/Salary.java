package dk.trustworks.userservice.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dk.trustworks.userservice.utils.BeanUtils;
import dk.trustworks.userservice.utils.LocalDateDeserializer;
import dk.trustworks.userservice.utils.LocalDateSerializer;
import io.vertx.core.json.JsonObject;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Created by hans on 23/06/2017.
 */

public class Salary {

    private String uuid;
    private int salary;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate activefrom;

    private String useruuid;

    public Salary() {
    }

    public Salary(LocalDate activeFrom, int salary, String useruuid) {
        this.useruuid = useruuid;
        uuid = UUID.randomUUID().toString();
        this.activefrom = activeFrom;
        this.salary = salary;
    }

    public Salary(String uuid, int salary, LocalDate activefrom, String useruuid) {
        this.uuid = uuid;
        this.salary = salary;
        this.activefrom = activefrom;
        this.useruuid = useruuid;
    }

    public Salary(JsonObject json)  {
        BeanUtils.populateFields(this, json);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public LocalDate getActivefrom() {
        return activefrom;
    }

    public void setActivefrom(LocalDate activefrom) {
        this.activefrom = activefrom;
    }

    public String getUseruuid() {
        return useruuid;
    }

    public void setUseruuid(String useruuid) {
        this.useruuid = useruuid;
    }

    @Override
    public String toString() {
        return "Salary{" +
                "uuid='" + uuid + '\'' +
                ", salary=" + salary +
                ", activefrom=" + activefrom +
                ", useruuid='" + useruuid + '\'' +
                '}';
    }
}

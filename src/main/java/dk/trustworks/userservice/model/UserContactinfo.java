package dk.trustworks.userservice.model;

import dk.trustworks.userservice.utils.BeanUtils;
import io.vertx.core.json.JsonObject;

/**
 * Created by hans on 23/06/2017.
 */
public class UserContactinfo {

    private int id;

    private String streetName;

    private String postalCode;

    private String city;

    private String phone;

    public UserContactinfo() {
    }

    public UserContactinfo(int id, String streetName, String postalCode, String city, String phone) {
        this.id = id;
        this.streetName = streetName;
        this.postalCode = postalCode;
        this.city = city;
        this.phone = phone;
    }

    public UserContactinfo(JsonObject jsonObject) {
        BeanUtils.populateFields(this, jsonObject);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserContactinfo{" +
                "id=" + id +
                ", streetName='" + streetName + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

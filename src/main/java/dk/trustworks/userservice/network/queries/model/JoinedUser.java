package dk.trustworks.userservice.network.queries.model;

import dk.trustworks.userservice.model.enums.ConsultantType;
import dk.trustworks.userservice.model.enums.RoleType;
import dk.trustworks.userservice.model.enums.StatusType;
import dk.trustworks.userservice.utils.BeanUtils;
import io.vertx.core.json.JsonObject;

import java.time.LocalDate;

/*
{
  "uuid": "cc3697a0-581b-11e6-8b77-86f30ca893d3",
  "active": true,
  "created": "2016-07-31T22:00:00Z",
  "email": "anne.walther@trustworks.dk",
  "firstname": "Anne",
  "lastname": "Walther",
  "password": "$2a$10$3YZGzAUe4aVsUggm/JTqqeHMuWfep5oRHeNGr53UPrBCCIAgxwPqC",
  "username": "anne.walther",
  "slackusername": "U1GKZK1NZ",
  "birthday": "1990-07-19",
  "roleuuid": "a78979fc-66de-41e3-95fa-ff0c178072b6",
  "role": "USER",
  "salaryuuid": "3f01f249-dbc5-4b38-9c91-9f0b0e91b77d",
  "useruuid": "cc3697a0-581b-11e6-8b77-86f30ca893d3",
  "salary": 43000,
  "activefrom": "2018-04-01",
  "contactid": null,
  "street": null,
  "postalcode": null,
  "city": null,
  "phone": null,
  "statusuuid": "1768b2ea-2a48-4016-a1e8-a87945d994d4",
  "status": "ACTIVE",
  "statusdate": "2016-08-01",
  "allocation": 0,
  "type": "STUDENT"
}
 */

public class JoinedUser {

    private String uuid;
    private boolean active;
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    private String username;
    private String slackusername;
    private LocalDate birthday;

    // role
    private String roleuuid;
    private RoleType role;

    // salary
    private String salaryuuid;
    private Integer salary;
    private LocalDate activefrom;

    // contact
    private Integer contactid;
    private String streetName;
    private String postalCode;
    private String city;
    private String phone;

    // status
    private String statusuuid;
    private ConsultantType type;
    private StatusType status;
    private LocalDate statusdate;
    private Integer allocation;

    public JoinedUser(JsonObject json) {
        BeanUtils.populateFields(this, json);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSlackusername() {
        return slackusername;
    }

    public void setSlackusername(String slackusername) {
        this.slackusername = slackusername;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getRoleuuid() {
        return roleuuid;
    }

    public void setRoleuuid(String roleuuid) {
        this.roleuuid = roleuuid;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public String getSalaryuuid() {
        return salaryuuid;
    }

    public void setSalaryuuid(String salaryuuid) {
        this.salaryuuid = salaryuuid;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public LocalDate getActivefrom() {
        return activefrom;
    }

    public void setActivefrom(LocalDate activefrom) {
        this.activefrom = activefrom;
    }

    public Integer getContactid() {
        return contactid;
    }

    public void setContactid(Integer contactid) {
        this.contactid = contactid;
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

    public String getStatusuuid() {
        return statusuuid;
    }

    public void setStatusuuid(String statusuuid) {
        this.statusuuid = statusuuid;
    }

    public ConsultantType getType() {
        return type;
    }

    public void setType(ConsultantType type) {
        this.type = type;
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

    public Integer getAllocation() {
        return allocation;
    }

    public void setAllocation(Integer allocation) {
        this.allocation = allocation;
    }

    @Override
    public String toString() {
        return "JoinedUser{" +
                "uuid='" + uuid + '\'' +
                ", active=" + active +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", slackusername='" + slackusername + '\'' +
                ", birthday=" + birthday +
                ", roleuuid='" + roleuuid + '\'' +
                ", role=" + role +
                ", salaryuuid='" + salaryuuid + '\'' +
                ", salary=" + salary +
                ", activefrom=" + activefrom +
                ", contactid=" + contactid +
                ", streetName='" + streetName + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", phone='" + phone + '\'' +
                ", statusuuid='" + statusuuid + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", statusdate=" + statusdate +
                ", allocation=" + allocation +
                '}';
    }
}



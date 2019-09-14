package dk.trustworks.userservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ImmutableMap;
import dk.trustworks.userservice.utils.BeanUtils;
import dk.trustworks.userservice.utils.LocalDateDeserializer;
import dk.trustworks.userservice.utils.LocalDateSerializer;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.util.GlobalTracer;
import io.vertx.core.json.JsonObject;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by hans on 23/06/2017.
 */

public class User {
    private String uuid;
    private boolean active;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate created;
    private String email;
    private String firstname;
    private String lastname;
    @JsonIgnore
    private String password;
    private String username;
    private String slackusername;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthday;
    private List<Salary> salaries = new ArrayList<>();
    private List<UserStatus> statuses = new ArrayList<>();
    private List<Role> roleList = new ArrayList<>();
    private UserContactinfo userContactinfo;

    public User() {
        uuid = UUID.randomUUID().toString();
        created = LocalDate.now();
    }

    public User(String uuid, boolean active, String email, String firstname, String lastname, String password, String username, String slackusername, LocalDate birthday) {
        this.uuid = uuid;
        this.active = active;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.username = username;
        this.slackusername = slackusername;
        this.birthday = birthday;
    }

    public User(JsonObject json) {
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

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public UserContactinfo getUserContactinfo() {
        return userContactinfo;
    }

    public void setUserContactinfo(UserContactinfo userContactinfo) {
        this.userContactinfo = userContactinfo;
    }

    public Boolean checkPassword(String passwordPlainText) {
        Span span = GlobalTracer.get().buildSpan("checkPassword").start();
        try (Scope scope = GlobalTracer.get().activateSpan(span)) {
            span.log(ImmutableMap.of("param", "value", "password", password));
            if (password.trim().equals("")) {
                span.log("Failed login attempt (no password) by " + getUsername());
                return false;
            }
            if (BCrypt.checkpw(passwordPlainText, password)) {
                span.log("Login by " + getUsername());
                return true;
            } else {
                span.log("Failed login attempt (wrong password) by " + getUsername());
                return false;
            }
        } catch (Exception e) {
            span.log(e.toString());
        } finally {
            span.finish();
        }
        return false;
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

    public List<Salary> getSalaries() {
        return salaries;
    }

    public void setSalaries(List<Salary> salaries) {
        this.salaries = salaries;
    }

    public List<UserStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<UserStatus> statuses) {
        this.statuses = statuses;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    @Override
    public String toString() {
        return "User{" +
                "uuid='" + uuid + '\'' +
                ", active=" + active +
                ", created=" + created +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", slackusername='" + slackusername + '\'' +
                ", birthday=" + birthday +
                ", salaries=" + salaries.size() +
                ", statuses=" + statuses.size() +
                ", roleList=" + roleList.size() +
                ", userContactinfo=" + userContactinfo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getUuid().equals(user.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUuid());
    }
}

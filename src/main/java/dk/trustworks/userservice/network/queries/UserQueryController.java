package dk.trustworks.userservice.network.queries;

import dk.trustworks.base.annotations.BaseUrl;
import dk.trustworks.base.annotations.Url;
import dk.trustworks.userservice.model.User;
import dk.trustworks.userservice.network.dto.LoginTokenResult;
import dk.trustworks.userservice.repositories.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.vertx.core.json.Json;
import io.vertx.reactivex.ext.web.RoutingContext;

import javax.crypto.SecretKey;

import static dk.trustworks.userservice.ActionHelper.*;

@BaseUrl("/users")
public class UserQueryController {

    private UserRepository userRepository;

    public UserQueryController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Url("/")
    public void getAllUsers(RoutingContext rc) {
        System.out.println("UserQueryController.getAllUsers");
        userRepository.getAllUsers().subscribe(ok(rc));
    }

    public void getOne(RoutingContext rc) {
        String uuid = rc.pathParam("uuid");
        userRepository.getOne(uuid).subscribe(ok(rc));
    }

    public void findByOrderByUsername(RoutingContext rc) {
        String username = rc.request().getParam("username");
        userRepository.findByUsername(username).subscribe(ok(rc));
    }

    public void findUsersByDateAndStatusListAndTypes(RoutingContext rc) {
        String date = rc.request().getParam("date");
        String[] statusList = rc.request().getParam("consultantStatusList").split(",");
        String[] types = rc.request().getParam("consultantTypes").split(",");
        userRepository.findUsersByDateAndStatusListAndTypes(date, statusList, types).subscribe(ok(rc));
    }

    public void calculateCapacityByMonthByUser(RoutingContext rc) {
        String useruuid = rc.request().getParam("useruuid");
        String statusdate = rc.request().getParam("statusdate");
        userRepository.calculateCapacityByMonthByUser(useruuid, statusdate).subscribe(ok(rc));
    }

    public void updateOne(RoutingContext rc) {
        String uuid = rc.request().getParam("uuid");
        User user = rc.getBodyAsJson().mapTo(User.class);
        userRepository.updateOne(uuid, user).subscribe(noContent(rc), onError(rc));
    }

    public void login(RoutingContext rc) {
        String username = rc.request().getParam("username");
        System.out.println("username = " + username);
        String password = rc.request().getParam("password");
        System.out.println("password = " + password);
        userRepository.findByUsername(username).subscribe(user -> {
            LoginTokenResult tokenResult;
            if(user.checkPassword(password)) {
                SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
                String jws = Jwts.builder().setSubject(username).signWith(key).compact();
                tokenResult = new LoginTokenResult(jws, true, "");
            } else {
                tokenResult = new LoginTokenResult("", false, "Login failed");
            }
            rc.response().setStatusCode(200)
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(Json.encodePrettily(tokenResult));
        });
    }

/*

    @GetAction("command/login")
    public LoginTokenResult login(String username, String password) {
        Span span = GlobalTracer.get().buildSpan("login").start();
        try (Scope scope = GlobalTracer.get().activateSpan(span)) {
            span.log(ImmutableMap.of("param", "value", "username", username));
            span.log(ImmutableMap.of("param", "value", "password", password));
            User user = findByUsername(username);
            LoginTokenResult tokenResult;
            if(user.checkPassword(password)) {
                SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
                String jws = Jwts.builder().setSubject(username).signWith(key).compact();
                tokenResult = new LoginTokenResult(jws, true, "");
            } else {
                tokenResult = new LoginTokenResult("", false, "Login failed");
            }
            return tokenResult;
        } catch (Exception e) {
            span.log(e.toString()); // Report any errors properly.
        } finally {
            span.finish(); // Optionally close the Span.
        }
        return new LoginTokenResult("", false, "Login failed");
    }

 */
}

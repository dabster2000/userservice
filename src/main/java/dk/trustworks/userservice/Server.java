package dk.trustworks.userservice;

import dk.trustworks.userservice.network.commands.RoleCommandController;
import dk.trustworks.userservice.network.commands.SalaryCommandController;
import dk.trustworks.userservice.network.commands.StatusCommandController;
import dk.trustworks.userservice.network.queries.UserQueryController;
import dk.trustworks.userservice.repositories.*;
import io.jaegertracing.Configuration;
import io.opentracing.util.GlobalTracer;
import io.reactivex.Completable;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.CompletableHelper;
import io.vertx.reactivex.config.ConfigRetriever;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.http.HttpServerResponse;
import io.vertx.reactivex.ext.jdbc.JDBCClient;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.BodyHandler;

import java.util.Locale;
import java.util.TimeZone;

public class Server extends AbstractVerticle {

    private UserQueryController userQueryController;

    private SalaryCommandController salaryCommandController;

    private RoleCommandController roleCommandController;

    private StatusCommandController statusCommandController;

    private ContactInfoRepository contactInfoRepository;

    public Server() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public void start(Future<Void> fut) {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"));
        Locale.setDefault(new Locale("da", "DK"));

        Configuration.SamplerConfiguration samplerConfiguration = Configuration.SamplerConfiguration.fromEnv().withType("const").withParam(1);
        Configuration.ReporterConfiguration reporterConfiguration = Configuration.ReporterConfiguration.fromEnv().withLogSpans(true);
        Configuration configuration = new Configuration("userservice").withSampler(samplerConfiguration).withReporter(reporterConfiguration);

        GlobalTracer.registerIfAbsent(configuration.getTracer());

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        router.route("/").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response
                    .putHeader("content-type", "text/html")
                    .end("<h1>Hello from my first Vert.x 3 application</h1>");
        });

        //Reflections reflections = new Reflections("dk.trustworks.userservice");
        /*
        Set<Class<?>> reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("dk.trustworks.userservice"))
        ).getTypesAnnotatedWith(BaseUrl.class);
        System.out.println("reflections.size() = " + reflections.size());
        //Set<Class<? extends Object>> allClasses = reflections.getSubTypesOf(Object.class);
        for (Class<?> aClass : reflections) {
            System.out.println("aClass = " + aClass.getName());
            if (aClass.isAnnotationPresent(BaseUrl.class)) {
                BaseUrl classAnnotation = aClass.getAnnotation(BaseUrl.class);
                try {
                    //Object instance = aClass.getDeclaredConstructor().newInstance();

                    for (Method declaredMethod : aClass.getDeclaredMethods()) {
                        System.out.println("declaredMethod.getName() = " + declaredMethod.getName());
                        if(!declaredMethod.isAnnotationPresent(Url.class)) continue;
                        Url methodAnnotation = declaredMethod.getAnnotation(Url.class);
                        router.get(classAnnotation.value()+methodAnnotation.value()).handler(routingContext -> {
                            try {
                                System.out.println("Adding url");
                                declaredMethod.invoke(instance, routingContext);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
         */

        ConfigRetriever retriever = ConfigRetriever.create(vertx);
        retriever.rxGetConfig()
                .doOnSuccess(config -> {
                    System.out.println("config = " + config);
                    userQueryController = new UserQueryController(new UserRepository(JDBCClient.createShared(vertx, config, "usermanager")));
                    salaryCommandController = new SalaryCommandController(new SalaryRepository(JDBCClient.createShared(vertx, config, "usermanager")));
                    roleCommandController = new RoleCommandController(new RoleRepository(JDBCClient.createShared(vertx, config, "usermanager")));
                    statusCommandController = new StatusCommandController(new StatusRepository(JDBCClient.createShared(vertx, config, "usermanager")));
                    contactInfoRepository = new ContactInfoRepository(JDBCClient.createShared(vertx, config, "usermanager"));

                    System.out.println("userQueryController = " + userQueryController);
                    router.get("/users").handler(userQueryController::getAllUsers);
                    router.get("/users/:uuid").handler(userQueryController::getOne);
                    router.get("/users/search/findByUsername").handler(userQueryController::findByOrderByUsername);
                    router.get("/users/search/findUsersByDateAndStatusListAndTypes").handler(userQueryController::findUsersByDateAndStatusListAndTypes);
                    router.get("/users/command/calculateCapacityByMonthByUser").handler(userQueryController::calculateCapacityByMonthByUser);
                    router.get("/users/command/login").handler(userQueryController::login);
                    router.put("/users/:uuid").handler(userQueryController::updateOne);

                    router.get("/users/:useruuid/salaries").handler(salaryCommandController::getUserSalaries);
                    router.post("/users/:useruuid/salaries").handler(salaryCommandController::createUserSalary);
                    router.delete("/users/:useruuid/salaries/:salaryuuid").handler(salaryCommandController::deleteUserSalary);

                    router.post("/users/:useruuid/statuses").handler(statusCommandController::createUserSalary);
                    router.delete("/users/:useruuid/statuses/:statusuuid").handler(statusCommandController::deleteUserSalary);

                    router.post("/users/:useruuid/roles").handler(roleCommandController::createUserRole);
                    router.delete("/users/:useruuid/roles").handler(roleCommandController::deleteUserRoles);
                })
                .flatMapCompletable(config -> createHttpServer(config, router))
                .subscribe(CompletableHelper.toObserver(fut));


    }

    private Completable createHttpServer(JsonObject config, Router router) {
        return vertx
                .createHttpServer()
                .requestHandler(router::accept)
                .rxListen(config.getInteger("HTTP_PORT", 5460))
                .ignoreElement();
    }
}
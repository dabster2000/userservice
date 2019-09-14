package dk.trustworks.userservice;

import dk.trustworks.userservice.network.queries.UserQueryController;
import dk.trustworks.userservice.repositories.*;
import io.jaegertracing.Configuration;
import io.opentracing.util.GlobalTracer;
import io.reactivex.Completable;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.CompletableHelper;
import io.vertx.reactivex.config.ConfigRetriever;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.http.HttpServerResponse;
import io.vertx.reactivex.ext.jdbc.JDBCClient;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.handler.BodyHandler;

import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static dk.trustworks.userservice.ActionHelper.ok;

public class Server extends AbstractVerticle {

    private UserQueryController userQueryController;

    private SalaryRepository salaryRepository;

    private RoleRepository roleRepository;

    private StatusRepository statusRepository;

    private ContactInfoRepository contactInfoRepository;

    public Server() {
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<AsyncResult<String>> q = new ArrayBlockingQueue<>(1);
        Vertx.vertx().deployVerticle(new Server(), q::offer);
        AsyncResult<String> result = q.take();
        if (result.failed()) {
            throw new RuntimeException(result.cause());
        }
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
                    salaryRepository = new SalaryRepository(JDBCClient.createShared(vertx, config, "usermanager"));
                    roleRepository = new RoleRepository(JDBCClient.createShared(vertx, config, "usermanager"));
                    statusRepository = new StatusRepository(JDBCClient.createShared(vertx, config, "usermanager"));
                    contactInfoRepository = new ContactInfoRepository(JDBCClient.createShared(vertx, config, "usermanager"));

                    System.out.println("userQueryController = " + userQueryController);
                    router.get("/users").handler(userQueryController::getAllUsers);
                    router.get("/users/:uuid").handler(userQueryController::getOne);
                    router.get("/users/:useruuid/salaries").handler(this::getUserSalaries);
                    router.get("/users/search/findByUsername").handler(userQueryController::findByOrderByUsername);
                    router.get("/users/search/findUsersByDateAndStatusListAndTypes").handler(userQueryController::findUsersByDateAndStatusListAndTypes);
                    router.get("/users/command/calculateCapacityByMonthByUser").handler(userQueryController::calculateCapacityByMonthByUser);
                    router.get("/users/command/login").handler(userQueryController::login);
                    router.put("/users/:uuid").handler(userQueryController::updateOne);
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

    private void getUserSalaries(RoutingContext rc) {
        String useruuid = rc.pathParam("useruuid");
        salaryRepository.getAllUserSalaries(useruuid).subscribe(ok(rc));
    }


}
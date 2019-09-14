package dk.trustworks.userservice.network.commands;

import dk.trustworks.userservice.model.UserStatus;
import dk.trustworks.userservice.repositories.StatusRepository;
import io.vertx.reactivex.ext.web.RoutingContext;

import static dk.trustworks.userservice.ActionHelper.noContent;
import static dk.trustworks.userservice.ActionHelper.onError;

//@Controller("/users")
public class StatusCommandController { //extends Controller.Util {


    private StatusRepository statusRepository;

    public StatusCommandController(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public void createUserSalary(RoutingContext rc) {
        String useruuid = rc.pathParam("useruuid");
        UserStatus status = rc.getBodyAsJson().mapTo(UserStatus.class);
        statusRepository.create(useruuid, status).subscribe(noContent(rc), onError(rc));
    }

    public void deleteUserSalary(RoutingContext rc) {
        String statusuuid = rc.pathParam("statusuuid");
        statusRepository.delete(statusuuid).subscribe(noContent(rc), onError(rc));
    }
/*
    private EbeanDao<String, User> userDao;
    private EbeanDao<String, UserStatus> userStatusDao;

    @Inject
    public StatusCommandRepository(EbeanDao<String, User> userDao, EbeanDao<String, UserStatus> userStatusDao) {
        this.userDao = userDao;
        this.userStatusDao = userStatusDao;
    }

    @PostAction("{useruuid}/statuses")
    public void createUserStatus(String useruuid, UserStatus userStatus) {
        Logger.info("SalaryCommandRepository.createUserStatus");
        Logger.info("useruuid = [" + useruuid + "], userStatus = [" + userStatus + "]");
        // TODO: Validate userStatus
        User user = userDao.findById(useruuid);
        user.getStatuses().add(userStatus);
        userDao.save(user);
    }

    @DeleteAction("{useruuid}/userstatuses/{userstatusuuid}")
    public void deleteUserStatus(String useruuid, String userstatusuuid) {
        Logger.info("SalaryCommandRepository.deleteUserStatus");
        Logger.info("useruuid = [" + useruuid + "], userstatusuuid = [" + userstatusuuid + "]");
        userStatusDao.deleteById(userstatusuuid);
    }

 */
}
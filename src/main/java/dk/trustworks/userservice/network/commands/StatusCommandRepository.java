package dk.trustworks.userservice.network.commands;

//@Controller("/users")
public class StatusCommandRepository { //extends Controller.Util {
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
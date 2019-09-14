package dk.trustworks.userservice.network.commands;

//@Controller("/users")
public class RoleCommandRepository { //extends Controller.Util {
/*
    private EbeanDao<String, User> userDao;
    private EbeanDao<String, Role> roleDao;

    @Inject
    public RoleCommandRepository(EbeanDao<String, User> userDao, EbeanDao<String, Role> roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @PostAction("{useruuid}/roles")
    public void createRole(String useruuid, Role role) {
        Logger.info("SalaryCommandRepository.createRole");
        Logger.info("useruuid = [" + useruuid + "], role = [" + role + "]");
        // TODO: Validate role
        Validate.matchesPattern(role.getUuid(), "", "UUID must be blank");
        role.setUuid(UUID.randomUUID().toString());
        User user = userDao.findById(useruuid);
        user.getRoleList().add(role);
        userDao.save(user);
    }

    @DeleteAction("{useruuid}/roles/type/{role}")
    public void deleteRole(String useruuid, String role) {
        Logger.info("SalaryCommandRepository.deleteRole");
        Logger.info("useruuid = [" + useruuid + "], role = [" + role + "]");
        try {
            roleDao.ebean(false).sqlUpdate("DELETE FROM userservice.roles WHERE useruuid like :useruuid AND role like :role;")
                    .setParameter("useruuid", useruuid)
                    .setParameter("role", role).executeNow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 */
}
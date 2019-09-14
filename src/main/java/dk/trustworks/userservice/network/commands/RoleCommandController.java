package dk.trustworks.userservice.network.commands;

import dk.trustworks.userservice.model.Role;
import dk.trustworks.userservice.repositories.RoleRepository;
import io.vertx.reactivex.ext.web.RoutingContext;

import static dk.trustworks.userservice.ActionHelper.noContent;
import static dk.trustworks.userservice.ActionHelper.onError;

//@Controller("/users")
public class RoleCommandController { //extends Controller.Util {

    private RoleRepository roleRepository;

    public RoleCommandController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void createUserRole(RoutingContext rc) {
        System.out.println("RoleCommandController.createUserRole");
        String useruuid = rc.pathParam("useruuid");
        Role role = rc.getBodyAsJson().mapTo(Role.class);
        roleRepository.create(useruuid, role).subscribe(noContent(rc), onError(rc));
    }

    public void deleteUserRoles(RoutingContext rc) {
        System.out.println("RoleCommandController.deleteUserRoles");
        String useruuid = rc.pathParam("useruuid");
        roleRepository.delete(useruuid).subscribe(noContent(rc), onError(rc));;
    }
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

    @Override
    public String toString() {
        return "RoleCommandController{}";
    }
}
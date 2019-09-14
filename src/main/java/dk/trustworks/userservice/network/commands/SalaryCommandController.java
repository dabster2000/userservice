package dk.trustworks.userservice.network.commands;

import dk.trustworks.userservice.model.Salary;
import dk.trustworks.userservice.repositories.SalaryRepository;
import io.vertx.reactivex.ext.web.RoutingContext;

import static dk.trustworks.userservice.ActionHelper.*;

//@Controller("/users")
public class SalaryCommandController {//extends Controller.Util {

    private SalaryRepository salaryRepository;

    public SalaryCommandController(SalaryRepository salaryRepository) {
        this.salaryRepository = salaryRepository;
    }

    public void getUserSalaries(RoutingContext rc) {
        String useruuid = rc.pathParam("useruuid");
        salaryRepository.getAllUserSalaries(useruuid).subscribe(ok(rc));
    }

    public void createUserSalary(RoutingContext rc) {
        String useruuid = rc.pathParam("useruuid");
        Salary salary = rc.getBodyAsJson().mapTo(Salary.class);
        salaryRepository.create(useruuid, salary).subscribe(noContent(rc), onError(rc));
    }

    public void deleteUserSalary(RoutingContext rc) {
        System.out.println("SalaryCommandController.deleteUserSalary");
        String salaryuuid = rc.pathParam("salaryuuid");
        salaryRepository.delete(salaryuuid).subscribe(noContent(rc), onError(rc));;
    }

/*
    private EbeanDao<String, User> userDao;
    private EbeanDao<String, Salary> salaryDao;

    @Inject
    public SalaryCommandRepository(EbeanDao<String, User> userDao, EbeanDao<String, Salary> salaryDao) {
        this.userDao = userDao;
        this.salaryDao = salaryDao;
    }

    @PostAction("{useruuid}/salaries")
    public void createSalary(String useruuid, Salary salary) {
        Logger.info("SalaryCommandRepository.createSalary");
        Logger.info("useruuid = [" + useruuid + "], salary = [" + salary + "]");
        // TODO: Validate salary
        User user = userDao.findById(useruuid);
        user.getSalaries().add(salary);
        userDao.save(user);
    }

    @DeleteAction("{useruuid}/salaries/{salaryuuid}")
    public void deleteSalary(String useruuid, String salaryuuid) {
        Logger.info("SalaryCommandRepository.deleteSalary");
        Logger.info("useruuid = [" + useruuid + "], salaryuuid = [" + salaryuuid + "]");
        salaryDao.deleteById(salaryuuid);
    }

 */
}
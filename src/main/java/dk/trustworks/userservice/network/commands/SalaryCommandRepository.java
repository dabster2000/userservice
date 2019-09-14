package dk.trustworks.userservice.network.commands;

//@Controller("/users")
public class SalaryCommandRepository {//extends Controller.Util {
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
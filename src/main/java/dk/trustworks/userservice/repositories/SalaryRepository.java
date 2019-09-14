package dk.trustworks.userservice.repositories;

import dk.trustworks.userservice.model.Salary;
import io.reactivex.Single;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.SQLOptions;
import io.vertx.reactivex.ext.jdbc.JDBCClient;
import io.vertx.reactivex.ext.sql.SQLConnection;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class SalaryRepository {

    private JDBCClient jdbc;

    public SalaryRepository(JDBCClient jdbc) {
        this.jdbc = jdbc;
    }

    private Single<SQLConnection> connect() {
        return jdbc.rxGetConnection()
                .map(c -> c.setOptions(new SQLOptions().setAutoGeneratedKeys(true)));
    }

    private Single<List<Salary>> query(SQLConnection connection) {
        return connection.rxQuery("SELECT * FROM salary")
                .map(rs -> rs.getRows().stream().map(Salary::new).collect(Collectors.toList()))
                .doFinally(connection::close);
    }

    private Single<List<Salary>> queryByUser(SQLConnection connection, String userUUID) {
        return connection.rxQueryWithParams("SELECT * FROM salary where useruuid LIKE ?", new JsonArray().add(userUUID))
                .map(rs -> rs.getRows().stream().map(Salary::new).collect(Collectors.toList()))
                .doFinally(connection::close);
    }

    private Single<Salary> queryOne(SQLConnection connection, String uuid) {
        String sql = "SELECT * FROM salary WHERE uuid LIKE ?";
        return connection.rxQueryWithParams(sql, new JsonArray().add(uuid))
                .doFinally(connection::close)
                .map(rs -> {
                    List<JsonObject> rows = rs.getRows();
                    if (rows.size() == 0) {
                        throw new NoSuchElementException("No salary with id " + uuid);
                    } else {
                        JsonObject row = rows.get(0);
                        return new Salary(row);
                    }
                });
    }

    public Single<List<Salary>> getAllSalaries() {
        return connect().flatMap(this::query);
    }

    /*
    public Single<List<Salary>> getAllUserSalaries(String userUUID) {
        return connect().flatMap((SQLConnection connection) -> connection.rxQueryWithParams(
                "SELECT * FROM salary where useruuid LIKE ?",
                new JsonArray().add(userUUID))
                .map(rs -> rs.getRows().stream().map(Salary::new).collect(Collectors.toList()))
                .doFinally(connection::close));
    }

     */

    public Single<List<Salary>> getAllUserSalaries(String... userUUIDs) {
        return connect().flatMap((SQLConnection connection) -> connection.rxQueryWithParams(
                "SELECT * FROM salary where useruuid IN ?",
                new JsonArray().add(userUUIDs))
                .map(rs -> rs.getRows().stream().map(Salary::new).collect(Collectors.toList()))
                .doFinally(connection::close));
    }

    public Single<Salary> getOne(String uuid) {
        return connect().flatMap(connection -> queryOne(connection, uuid));
    }
}
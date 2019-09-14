package dk.trustworks.userservice.repositories;

import dk.trustworks.userservice.model.UserStatus;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.sql.SQLOptions;
import io.vertx.reactivex.ext.jdbc.JDBCClient;
import io.vertx.reactivex.ext.sql.SQLConnection;

import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

public class StatusRepository {

    private JDBCClient jdbc;

    public StatusRepository(JDBCClient jdbc) {
        this.jdbc = jdbc;
    }

    private Single<SQLConnection> connect() {
        return jdbc.rxGetConnection()
                .map(c -> c.setOptions(new SQLOptions().setAutoGeneratedKeys(true)));
    }

    public Completable create(String useruuid, UserStatus status) {
        return connect().flatMapCompletable(connection -> {
            String sql = "INSERT INTO usermanager.userstatus " +
                    "(uuid, useruuid, status, statusdate, allocation, type) " +
                    "VALUES " +
                    "(?, ?, ?, ?, ?, ?);";
            JsonArray params = new JsonArray()
                    .add(status.getUuid())
                    .add(useruuid)
                    .add(status.getStatus().name())
                    .add(status.getStatusdate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .add(status.getAllocation())
                    .add(status.getType().name());
            return connection.rxUpdateWithParams(sql, params)
                    .flatMapCompletable(ur ->
                            ur.getUpdated() == 0 ?
                                    Completable.error(new NoSuchElementException("No user with uuid " + useruuid))
                                    : Completable.complete()
                    )
                    .doFinally(connection::close);
        });
    }

    public Completable delete(String statusuuid) {
        return connect().flatMapCompletable(connection -> {
            String sql = "DELETE FROM usermanager.userstatus WHERE uuid LIKE ?;";
            JsonArray params = new JsonArray().add(statusuuid);

            return connection.rxUpdateWithParams(sql, params)
                    .flatMapCompletable(ur ->
                            ur.getUpdated() == 0 ?
                                    Completable.error(new NoSuchElementException("No status with uuid " + statusuuid))
                                    : Completable.complete()
                    )
                    .doFinally(connection::close);
        });
    }
}

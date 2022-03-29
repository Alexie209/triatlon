package com.example.triatlon.repository.db;

import com.example.triatlon.domain.Arbitru;
import com.example.triatlon.domain.validators.Validator;
import com.example.triatlon.repository.Repository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArbitruDbRepository implements Repository<Long, Arbitru> {
    protected Map<Long, Arbitru> entities;

    private final Validator<Arbitru> validator;

    private final JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public ArbitruDbRepository(Properties props, Validator<Arbitru> validator) {
        logger.info("Initializing ArbitruDBRepository with properties: {} ", props);
        dbUtils = new JdbcUtils(props);

        this.validator = validator;
        entities = new HashMap<>();
    }


    @Override
    public Arbitru save(Arbitru entity) {
        logger.traceEntry("saving task {}", entity);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement(
                "insert into arbitri (first_name, last_name, username, password ) values (?, ?, ?, ?)")) {
            preStmt.setString(1, entity.getFirstName());
            preStmt.setString(2, entity.getLastName());
            preStmt.setString(3, entity.getUsername());
            preStmt.setString(4, entity.getPassword());
            preStmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error" + ex);
        }
        logger.traceExit();
        Arbitru arbitru = new Arbitru(entity.getFirstName(), entity.getLastName(), entity.getUsername(), entity.getPassword());
        entities.put(entity.getId(), arbitru);
        return arbitru;
    }

    @Override
    public Arbitru delete(Long aLong) {
        logger.traceEntry("delete task {}", aLong);
        Connection con = dbUtils.getConnection();
        if (aLong == null) {
            throw new IllegalArgumentException("deleted entity doesn't exist");
        }
        try(PreparedStatement preparedStatement = con.prepareStatement("delete from arbitri where id = ?")){
            preparedStatement.setLong(1, aLong);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error" + e);
        }
        logger.traceExit();
        entities.remove(aLong);

        return null;
    }

    @Override
    public void update(Arbitru entity) {
        logger.traceEntry("update task {}", entity);
        Connection con = dbUtils.getConnection();
        if (entity == null)
            throw new IllegalArgumentException("entity must be not null");
        validator.validate(entity);
        String sql = "update arbitri set first_name = ?, last_name = ?, username = ?, password = ? where id = ?";
        try(PreparedStatement preparedStatement = con.prepareStatement("update arbitri set first_name = ?, last_name = ?, username = ?, password = ? where id = ?")){
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setString(3, entity.getUsername());
            preparedStatement.setString(4, entity.getPassword());
            preparedStatement.setLong(5, entity.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error" + e);
        }
        logger.traceExit();
        entities.put(entity.getId(), entity);
        if (entities.get(entity.getId()) != null) {
            entities.put(entity.getId(), entity);
        }
    }

    @Override
    public Arbitru findOne(Long aLong) {
        logger.traceEntry("find one task {}", aLong);
        Connection con = dbUtils.getConnection();
        if (aLong == null)
            throw new IllegalArgumentException("ID must be not null");
        try(PreparedStatement preparedStatement = con.prepareStatement("select * from arbitri where id = ? ")){

            preparedStatement.setLong(1, aLong);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");

            return new Arbitru(firstName, lastName, username, password);
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error" + e);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public List<Arbitru> findAll() {
        logger.traceEntry("find all task ");
        Connection con = dbUtils.getConnection();
        List<Arbitru> users = new ArrayList<>();
        try(PreparedStatement preparedStatement = con.prepareStatement("select * from arbitri ")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                Arbitru user = new Arbitru(firstName, lastName, username, password);
                user.setId(id);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error" + e);
        }
        logger.traceExit();
        return null;
    }


    @Override
    public Arbitru getByUsername(String username) {
        logger.traceEntry("get by username {} ", username);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("select * from arbitri where username = ? ")) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            long id = resultSet.getLong("id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String passwordHash = resultSet.getString("password");
            Arbitru arbitru = new Arbitru(firstName, lastName, username, passwordHash);
            arbitru.setId(id);
            return arbitru;
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error" + e);
        }
        logger.traceExit();
        return null;
    }
}
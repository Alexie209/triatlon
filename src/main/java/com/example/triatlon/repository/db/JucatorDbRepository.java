package com.example.triatlon.repository.db;

import com.example.triatlon.domain.Jucator;
import com.example.triatlon.domain.validators.Validator;
import com.example.triatlon.repository.Repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class JucatorDbRepository implements Repository<Long, Jucator> {
    protected Map<Long, Jucator> entities;

    private final Validator<Jucator> validator;

    private final JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public JucatorDbRepository(Properties properties, Validator<Jucator> validator) {
        logger.info("Initializing JucatorDbRepository with properties: {} ", properties);
        dbUtils = new JdbcUtils(properties);
        this.validator = validator;

        entities = new HashMap<>();
    }
    @Override
    public Jucator findOne(Long aLong) {
        logger.traceEntry("find one task {}", aLong);
        Connection con = dbUtils.getConnection();
        if(aLong == null)
            throw new IllegalArgumentException("ID must be not null");
        try(PreparedStatement preparedStatement = con.prepareStatement("select * from jucatori where id = ?")){
            preparedStatement.setLong(1, aLong);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            String firstName = resultSet.getString("firstname");
            String lastName = resultSet.getString("lastname");
            int total_puncte = resultSet.getInt("total_puncte");
            return new Jucator(firstName, lastName, total_puncte);
        } catch (SQLException throwables) {
            logger.error(throwables);
            System.err.println("Error" + throwables);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public List<Jucator> findAll() {
        logger.traceEntry("find all task ");
        Connection con = dbUtils.getConnection();
        List<Jucator> jucatori = new ArrayList<>();
        try(PreparedStatement preparedStatement = con.prepareStatement("SELECT * from jucatori")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Long id =resultSet.getLong("id");
                String firstName =resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                int total_puncte = resultSet.getInt("total_puncte");
                Jucator jucator = new Jucator(firstName, lastName, total_puncte);
                jucator.setId(id);
                jucatori.add(jucator);
            }
            return jucatori;
        } catch (SQLException throwables) {
            logger.error(throwables);
            System.err.println("Error" + throwables);
        }
        logger.traceExit();
        return jucatori;
    }

    @Override
    public Jucator save(Jucator entity) {
        logger.traceEntry("saving task {}", entity);
        Connection con = dbUtils.getConnection();
        if(entity == null)
            throw new IllegalArgumentException("entity must be not null");
        validator.validate(entity);
        try (PreparedStatement preStmt = con.prepareStatement(
                "insert into jucatori (firstname, lastname, total_puncte) values(?,?,?)")){
            preStmt.setString(1, entity.getFirstName());
            preStmt.setString(2, entity.getLastName());
            preStmt.setInt(3, entity.getTotalPuncte());
            preStmt.executeUpdate();
        } catch (SQLException throwables) {
            logger.error(throwables);
            System.err.println("Error" + throwables);
        }
        logger.traceExit();
        Jucator jucator = new Jucator(entity.getFirstName(), entity.getLastName(), entity.getTotalPuncte());
        entities.put(entity.getId(), jucator);

        return null;
    }

    @Override
    public Jucator delete(Long aLong) {
        logger.traceEntry("delete task {}", aLong);
        Connection con = dbUtils.getConnection();
        if(aLong == null)
            throw new IllegalArgumentException("deleted entity doesn't exist!");

        try(PreparedStatement preparedStatement = con.prepareStatement("delete from jucatori where id = ?")){
            preparedStatement.setLong(1, aLong);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error(throwables);
            System.err.println("Error" + throwables);
        }
        logger.traceExit();
        entities.remove(aLong);
        return null;
    }

    @Override
    public void update(Jucator entity) {
        logger.traceEntry("update task {}", entity);
        Connection con = dbUtils.getConnection();
        if(entity == null)
            throw new IllegalArgumentException("entity must be not null");
        validator.validate(entity);
        try(PreparedStatement preparedStatement = con.prepareStatement("update jucatori set firstname = ?, lastname = ?, total_puncte = ? where id = ?")){
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setInt(3, entity.getTotalPuncte());
            preparedStatement.setLong(4, entity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error(throwables);
            System.err.println("Error" + throwables);
        }
        entities.put(entity.getId(), entity);
        if(entities.get(entity.getId()) != null) {
            entities.put(entity.getId(), entity);
        }
    }

    @Override
    public Jucator getByUsername(String s) {
        return null;
    }
}

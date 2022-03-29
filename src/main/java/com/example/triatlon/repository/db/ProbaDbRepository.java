package com.example.triatlon.repository.db;

import com.example.triatlon.domain.Proba;
import com.example.triatlon.domain.validators.Validator;
import com.example.triatlon.repository.Repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ProbaDbRepository implements Repository<Long, Proba> {
    protected Map<Long, Proba> entities;

    private final Validator<Proba> validator;

    private final JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public ProbaDbRepository(Properties props, Validator<Proba> validator) {
        logger.info("Initializing ArbitruDBRepository with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
        this.validator = validator;

        entities = new HashMap<>();
    }

    @Override
    public Proba findOne(Long aLong) {
        logger.traceEntry("find all task ");
        Connection con = dbUtils.getConnection();
        if(aLong == null)
            throw new IllegalArgumentException("ID must be not null");
        try(PreparedStatement preparedStatement = con.prepareStatement("select * from probe where id = ?")){
            preparedStatement.setLong(1, aLong);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            String name = resultSet.getString("name");
            Long idArbitru = resultSet.getLong("id_arbitru");

            return new Proba(name,idArbitru);
        } catch (SQLException throwables) {
            logger.error(throwables);
            System.err.println("Error " + throwables);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public List<Proba> findAll() {
        logger.traceEntry("find all task ");
        Connection con = dbUtils.getConnection();
        List<Proba> probe = new ArrayList<>();
        try(PreparedStatement preparedStatement = con.prepareStatement("SELECT * from probe")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Long id =resultSet.getLong("id");
                String name =resultSet.getString("name");
                Long idArbitru = resultSet.getLong("id_arbitru");
                Proba proba = new Proba(name, idArbitru);
                proba.setId(id);
                probe.add(proba);
            }
            return probe;
        } catch (SQLException throwables) {
            logger.error(throwables);
            System.err.println("Error " + throwables);
        }
        logger.traceExit();
        return probe;
    }

    @Override
    public Proba save(Proba entity) {
        logger.traceEntry("saving task {}", entity);
        Connection con = dbUtils.getConnection();
        if(entity == null)
            throw new IllegalArgumentException("entity must be not null");
        validator.validate(entity);
        try(PreparedStatement preparedStatement = con.prepareStatement("insert into probe (name,id_arbitru) values(?,?)")) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setLong(2, entity.getIdArbitru());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error(throwables);
            System.err.println("Error " + throwables);
        }
        logger.traceExit();
        Proba proba = new Proba(entity.getName(), entity.getIdArbitru());
        entities.put(entity.getId(), proba);

        return null;
    }

    @Override
    public Proba delete(Long aLong) {
        logger.traceEntry("delete task {}", aLong);
        Connection con = dbUtils.getConnection();
        if(aLong == null)
            throw new IllegalArgumentException("deleted entity doesn't exist!");

        try(PreparedStatement preparedStatement = con.prepareStatement("delete from probe where id = ?")) {
            preparedStatement.setLong(1, aLong);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error(throwables);
            System.err.println("Error " + throwables);
        }
        logger.traceExit();
        entities.remove(aLong);
        return null;
    }

    @Override
    public void update(Proba entity) {
        logger.traceEntry("update task {}", entity);
        Connection con = dbUtils.getConnection();
        if(entity == null)
            throw new IllegalArgumentException("entity must be not null");
        validator.validate(entity);
        try(PreparedStatement preparedStatement = con.prepareStatement("update probe set name = ?, id_arbitru = ? where id = ?")) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setLong(2, entity.getIdArbitru());
            preparedStatement.setLong(3, entity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error(throwables);
            System.err.println("Error " + throwables);
        }
        logger.traceExit();
        entities.put(entity.getId(), entity);
        if(entities.get(entity.getId()) != null) {
            entities.put(entity.getId(), entity);
        }
    }

    @Override
    public Proba getByUsername(String s) {
        return null;
    }
}

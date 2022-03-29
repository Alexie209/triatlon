package com.example.triatlon.repository.db;

import com.example.triatlon.domain.ParticipantProba;
import com.example.triatlon.domain.validators.Validator;
import com.example.triatlon.repository.Repository;
import com.example.triatlon.repository.db.JdbcUtils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ParticipantProbaDbRepository implements Repository<Long, ParticipantProba> {
    private final Validator<ParticipantProba> validator;
    private final static String IDJUCATOR = "id_jucator";
    private final static String IDPROBA = "id_proba";
    private final static String PUNCTESTRANSE = "puncte_stranse";
    private final JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger();
    public ParticipantProbaDbRepository(Properties props, Validator<ParticipantProba> validator){
        logger.info("Initializing ParticipantProbaDBRepository with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
        this.validator = validator;
    }
    @Override
    public ParticipantProba findOne(Long id) {
        logger.traceEntry("find one task {}", id);
        Connection con = dbUtils.getConnection();
        if (id == null)
            throw new IllegalArgumentException("id must be not null");

        try(PreparedStatement preparedStatement = con.prepareStatement("select * from participant_proba where id = ? ")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            Long idParticipant = resultSet.getLong(IDJUCATOR);
            Long idProba = resultSet.getLong(IDPROBA);
            int puncteStranse = resultSet.getInt(PUNCTESTRANSE);

            ParticipantProba participantProba = new ParticipantProba(idParticipant, idProba, puncteStranse);
            participantProba.setId(id);
            return participantProba;
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error " + e);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public List<ParticipantProba> findAll() {
        logger.traceEntry("find all task ");
        Connection con = dbUtils.getConnection();
        List<ParticipantProba> participantProbas = new ArrayList<>();
        try(PreparedStatement preparedStatement = con.prepareStatement("SELECT * from participant_proba")){
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long id =resultSet.getLong("id");
                Long idParticipant = resultSet.getLong(IDJUCATOR);
                Long idProba = resultSet.getLong(IDPROBA);
                int puncteStranse = resultSet.getInt(PUNCTESTRANSE);

                ParticipantProba participantProba = new ParticipantProba(idParticipant, idProba, puncteStranse);
                participantProba.setId(id);
                participantProbas.add(participantProba);
            }
            return participantProbas;
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error " + e);
        }
        logger.traceExit();
        return participantProbas;
    }

    @Override
    public ParticipantProba save(ParticipantProba entity) {
        logger.traceEntry("saving task {}", entity);
        Connection con = dbUtils.getConnection();
        boolean exists = false;
        if (entity == null)
            throw new IllegalArgumentException("entity must be not null");

        validator.validate(entity);
        try (PreparedStatement preStmt = con.prepareStatement(
                "insert into participant_proba ( id_jucator, id_proba, puncte_stranse) values (?, ?, ?)")){
            preStmt.setLong(1, entity.getIdJucator());
            preStmt.setLong(2, entity.getIdProba());
            preStmt.setInt(3, entity.getPuncteStranse());

            preStmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error" + e);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public ParticipantProba delete(Long aLong) {
        logger.traceEntry("delete task {}", aLong);
        Connection con = dbUtils.getConnection();
        if (aLong == null)
            throw new IllegalArgumentException("id must be not null");
        try(PreparedStatement preparedStatement = con.prepareStatement("delete from participant_proba where id = ?")){

            preparedStatement.setLong(1, aLong);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error " + e);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public void update(ParticipantProba entity) {

    }

    @Override
    public ParticipantProba getByUsername(String s) {
        return null;
    }
}

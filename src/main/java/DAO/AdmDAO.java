package DAO;

import Child.AgeGroup;
import Child.Child;
import PlayRoom.PlayRoom;
import Toy.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class AdmDAO {
    private Connection connection;
    private PlayRoom playRoom;
    public AdmDAO(Connection connection) {
        setConnection(connection);
    }
    public void setConnection(Connection connection) {
        if(connection == null)
            throw new IllegalArgumentException("Connection cannot be null in AdmDAO");
        this.connection = connection;
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.config("AdmDAO встановлює підключення до БД");
    }
    public PlayRoom.ToyList getToyList(){
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.info("AdmDAO повертає об'єкт ToyList");
        return getPlayRoom().getToyList();
    }

    public PlayRoom createPlayRoom(){
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.config("Ініціалізація ігрової кімнати");
        return playRoom = PlayRoom.getPlayRoom(connection);
    }
    public PlayRoom getPlayRoom(){
        return playRoom;
    }


}

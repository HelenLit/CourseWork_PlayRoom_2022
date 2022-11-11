package org.example;


import Command.ExecCommand;
import DAO.AdmDAO;
import DAO.DAOFactory;
import DAO.DAOFactoryType;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.*;

public class Main {

    public static Logger logger;

    static public void setup() {
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("src/main/mylogging.properties"));
            logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
            logger.setUseParentHandlers(false);
            logger.addHandler(new FileHandler());
        } catch (IOException e) {
            System.err.println("Помилка з конфігурацією логгера.");
        }
    }
    public static void main(String[] args) throws Exception {
        setup();
        logger.log(Level.ALL,"Підключення");
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactoryType.SQLSERVER);
        assert factory != null;
        factory.setProperties("Course_Work_Play_Room","Student2022","2022");
        AdmDAO administrator = factory.getAdmDAO();
        administrator.createPlayRoom();
        ExecCommand execCommand = new ExecCommand();
        execCommand.menu(administrator);
    }
}

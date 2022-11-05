package org.example;

import Child.AgeGroup;
import Command.ExecCommand;
import DAO.AdmDAO;
import DAO.DAOFactory;
import DAO.DAOFactoryType;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactoryType.SQLSERVER);
        assert factory != null;
        factory.setProperties("Course_Work_Play_Room","Student2022","2022");
        AdmDAO administrator = factory.getAdmDAO();
        administrator.createPlayRoom();
        ExecCommand execCommand = new ExecCommand();
        execCommand.menu(administrator);
    }
}

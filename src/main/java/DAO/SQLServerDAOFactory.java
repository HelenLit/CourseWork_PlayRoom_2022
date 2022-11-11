package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Logger;

public class SQLServerDAOFactory extends DAOFactory {

    public SQLServerDAOFactory(String dataBaseName, String user, String password) throws SQLException {
          this();
        setProperties(dataBaseName,user,password);
    }
    public SQLServerDAOFactory() throws SQLException {
        DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
    }

    @Override
    protected String setURL() {
        return URL = "jdbc:sqlserver://localhost\\MSSQLSERVER:1433;databaseName="+dataBaseName+";encrypt=true;trustServerCertificate=true;";
    }

    @Override
    public void setProperties(String dataBaseName, String user, String password){
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.info("������������ ��������� ��� �'������ � ��");
        setDataBaseName(dataBaseName);
        setUser(user);
        setPassword(password);
    }
    @Override
    public AdmDAO getAdmDAO() {
        return new AdmDAO(getConnection());
    }

    @Override
    synchronized protected Connection getConnection() {
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        try {
            if (con == null) {
                logger.config("������ �������� �'������� � ��");
                con = DriverManager.getConnection(URL, user, password);
            }
        }catch (SQLException e){
            logger.severe("������ �������� �'������� � �� �������");
            System.err.println("Could not establish connection to database: " + Arrays.toString(e.getStackTrace()));
            System.exit(e.getErrorCode());
        }
        logger.fine("������ �������� �'������� � �� ������� ������");
        return con;
    }

}

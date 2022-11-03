package DAO;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DAOFactory {
    protected String URL;
    protected Connection con;
    protected String dataBaseName;
    protected String user;
    protected String password;
    public abstract AdmDAO getAdmDAO();

    public abstract void setProperties(String dataBaseName, String user, String password);
    protected abstract Connection getConnection();
    protected abstract String setURL();
    public void setDataBaseName(String dataBaseName) {
        if(dataBaseName == null)
            throw new IllegalArgumentException();
        this.dataBaseName = dataBaseName;
        setURL();
    }

    public void setUser(String user) {
        if(user == null)
            throw new IllegalArgumentException();
        this.user = user;
    }

    public void setPassword(String password) {
        if(password == null)
            throw new IllegalArgumentException();
        this.password = password;
    }

    public static DAOFactory getDAOFactory(DAOFactoryType whichFactory) throws SQLException {
        switch (whichFactory) {
            case SQLSERVER:
                return new SQLServerDAOFactory();
            default:
                return null;
        }
    }
}

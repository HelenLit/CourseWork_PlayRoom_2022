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
    }
    public PlayRoom.ToyList getToyList(){
        return getPlayRoom().getToyList();
    }

    public PlayRoom createPlayRoom(){
        return playRoom = PlayRoom.getPlayRoom(connection);
    }
    public PlayRoom getPlayRoom(){ return playRoom; }


}

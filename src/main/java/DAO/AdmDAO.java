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

    public PlayRoom createPlayRoom(){
        return playRoom = PlayRoom.getPlayRoom(connection);
    }
    public void deleteToy(int id) {
        try{
        Statement st = connection.createStatement();
        ResultSet result = st.executeQuery(
                "USE [Course_Work_Play_Room]\n" +
                        "DELETE FROM\n" +
                        "    [Toys].[ToysList]\n" +
                        "WHERE\n" +
                        "   [toy_ID] = "+ id);
        }catch (SQLException e){
            System.err.println(e.getSQLState());
        }
    }

    public PlayRoom getPlayRoom(){ return playRoom; }

    public void addToy(Toy toy) {
        try{
            Statement st = connection.createStatement();
            ResultSet result = st.executeQuery(
                    "USE [Course_Work_Play_Room]\n" +
                    "INSERT INTO\n" +
                    "[Toys].[ToysList]([t_name], [ageGroupID], [t_sizeID], [price])\n" +
                    "VALUES("+toy.getName()+","+(toy.getAgeGroup().ordinal()+1)+","+toy.getToySize().getOrd()+","+toy.getPrice()+")");
        }catch (SQLException e){
            System.err.println(e.getSQLState());
        }
    }
    public static Toy createToyObj(ResultSet result, AgeGroup ageGroup) {
        int id = 0;
        String name = null;
        int price = 0;
        ToySize toySize = null;
        try{
            if (result == null || ageGroup == null)
                throw new IllegalArgumentException();
            id = result.getInt("toy_ID");
            name = result.getString("t_name");
            price = result.getInt("price");
            toySize = ToySize.getSizeByOrd(result.getInt("t_sizeID"));
        }catch (SQLException e){
            System.err.println("Could not extract String from column name in ResultSet: " + Arrays.toString(e.getStackTrace()));
            System.exit(e.getErrorCode());
        }
        return switch (ageGroup){
            case TODDLER -> new ToddlerToy(id,name,price,toySize);
            case MIDDLECHILD -> new MiddleChildToy(id,name,price,toySize);
            case TEENAGER -> new TeenagerToy(id,name,price,toySize);
        };
    }
    public static Child createChildObj(ResultSet result){
        String fname = null;
        String lname = null;
        AgeGroup ageGroup = null;
        String pcontact = null;
        try{
            if (result == null)
                throw new IllegalArgumentException();
            fname = result.getString("fname");
            lname = result.getString("lname");
            ageGroup = AgeGroup.getAgeGroupByOrd(result.getInt("ageGroupID"));
            pcontact = result.getString("parent_contact");
        }catch (SQLException e){
            System.err.println("Could not extract data from column name in ResultSet: " + Arrays.toString(e.getStackTrace()));
            System.exit(e.getErrorCode());
        }
        return new Child(fname,lname,ageGroup,pcontact);
    }
    public static Toy createToyObj(ResultSet result){
        try{
        int ageGroup = result.getInt("ageGroupID");
        if(ageGroup == AgeGroup.TODDLER.ordinal()+1) return createToyObj(result,AgeGroup.TODDLER);
        else if (ageGroup == AgeGroup.MIDDLECHILD.ordinal()+1) return createToyObj(result,AgeGroup.MIDDLECHILD);
        else return createToyObj(result,AgeGroup.TEENAGER);
        }catch (SQLException e){
            System.err.println(Arrays.toString(e.getStackTrace()));
            System.exit(e.getErrorCode());
        }
        return null;
    }

    public List<Toy> allToysByAgeGroup(AgeGroup ageGroup) {
        List<Toy> toyList = new ArrayList<>();
        try{
        Statement st = connection.createStatement();
        ResultSet result = st.executeQuery(
                "USE [Course_Work_Play_Room]\n" +
                        "SELECT *\n" +
                        "FROM [Toys].[ToysList]\n" +
                        "WHERE [ageGroupID] = "+(ageGroup.ordinal()+1));


        while (result.next()) {
            toyList.add(createToyObj(result,ageGroup));
        }
        }catch (SQLException e){
            System.err.println(Arrays.toString(e.getStackTrace()));
            System.exit(e.getErrorCode());
        }
        return toyList;
    }

    public List<Child> ChildernList(){
        List<Child> childrenList = new ArrayList<>();
        try{
            Statement st = connection.createStatement();
            ResultSet result = st.executeQuery("""
                                        USE [Course_Work_Play_Room]
                                        SELECT * 
                                          FROM [Client].[Registered_Children]
                                           """);
            while (result.next()) {
                childrenList.add(createChildObj(result));
            }
        }catch (SQLException e){
            System.err.println(Arrays.toString(e.getStackTrace()));
            System.exit(e.getErrorCode());
        }
        return childrenList;
    }

}

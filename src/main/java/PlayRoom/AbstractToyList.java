package PlayRoom;

import Child.AgeGroup;
import Toy.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public abstract class AbstractToyList {
    protected int initialMoney;
    protected Map<Integer, ToyEntityInfo> toyEntityMap;
    public AbstractToyList() { }
    public AbstractToyList(int initialMoney) {
        setInitialMoney(initialMoney);
    }
    abstract Connection getConnection();
    abstract boolean ifPlaying();
    void check() {
        if(ifPlaying()){
            throw new IllegalStateException();
        }
    }
    protected int fromWhich(int money, List<Toy> toyList1){
        int i = 0;
        while(money<toyList1.get(i).getPrice() && i<(toyList1.size()-1))
            i++;
        return i;
    }

    public List<Toy> listFromMap(List<Toy> allToys) {
        check();
        List<Toy> result = new ArrayList<>();
        toyEntityMap.entrySet().forEach(e -> allToys.forEach(
                t -> {
                    if (t.getId() == e.getKey())
                        for (int i =0;i< e.getValue().getAmount();i++) {
                            result.add(t);
                        }
                }));
        return result;
    }
    abstract public Map<Integer, ToyEntityInfo> CreateToyMap(List<Toy>... ageGroupToys);
    public void freeList(){
        toyEntityMap.clear();
    }

    protected static Toy createToyObj(ResultSet result, AgeGroup ageGroup) {
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
        return new Toy(id,name,price,ageGroup,toySize);
    }
    protected static Toy createToyObj(ResultSet result){
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
    private void execVoidToyQuery(String query){
        try(Statement st = getConnection().createStatement()){
            ResultSet result = st.executeQuery(query);

        }catch (SQLException e){
            System.err.println(e.getSQLState());
        }
    }
    public void createToyList(){
        execVoidToyQuery(
                        """
                         CREATE TABLE #ToysInRoom(
                             [toy_ID] [int] PRIMARY KEY,
                             [amount] [int] NULL,
                             [total_price] [int] NULL,
                         );
                         """);
    }
    public void trunkToyList(){
        execVoidToyQuery(
                         """
                        TRUNCATE TABLE #ToysInRoom
                        """);
    }
    public void deleteToy(int id) {
        execVoidToyQuery(
                    "USE [Course_Work_Play_Room]\n" +
                            "DELETE FROM\n" +
                            "    [Toys].[ToysList]\n" +
                            "WHERE\n" +
                            "   [toy_ID] = "+ id);
    }
    public void addToy(Toy toy) {
        execVoidToyQuery(
                    "USE [Course_Work_Play_Room]\n" +
                            "INSERT INTO\n" +
                            "[Toys].[ToysList]([t_name], [ageGroupID], [t_sizeID], [price])\n" +
                            "VALUES("+toy.getName()+","+(toy.getAgeGroup().ordinal()+1)+","+toy.getToySize().getOrd()+","+toy.getPrice()+")");

    }
    private List<Toy> execToyQuery(String query){
        List<Toy> toyList = new ArrayList<>();
        try(Statement st = getConnection().createStatement()){
            ResultSet result = st.executeQuery(query);
            while (result.next()) {
                toyList.add(createToyObj(result));
            }
        }catch (SQLException e){
            System.err.println(e.getSQLState());
        }
        return toyList;
    }
    public List<Toy> allToysByAgeGroup(AgeGroup ageGroup) {
        return execToyQuery(
                    "USE [Course_Work_Play_Room]\n" +
                            "SELECT *\n" +
                            "FROM [Toys].[ToysList]\n" +
                            "WHERE [ageGroupID] = "+(ageGroup.ordinal()+1));

    }
    public List<Toy> allToys() {
        return execToyQuery(
                "USE [Course_Work_Play_Room]\n" +
                        "SELECT *\n" +
                        "FROM [Toys].[ToysList]\n");

    }
    public List<Toy> toysInRoom(){
        return execToyQuery(
                """
                    SELECT tl.[toy_ID]
                         ,[t_name]
                         ,[ageGroupID]
                         ,[t_sizeID]
                         ,[price]
                       FROM [Course_Work_Play_Room].[Toys].[ToysList] AS tl
                       JOIN #ToysInRoom AS tir
                       ON tl.toy_ID = tir.toy_ID
                    """);

    }
    public List<Toy> toySublistByAgeGroup(AgeGroup ageGroup){
        return execToyQuery(
                "SELECT tl.[toy_ID]\n" +
                        "      ,[t_name]\n" +
                        "      ,[ageGroupID]\n" +
                        "      ,[t_sizeID]\n" +
                        "      ,[price]\n" +
                        "FROM [Course_Work_Play_Room].[Toys].[ToysList] AS tl\n" +
                        "JOIN #ToysInRoom AS tir\n" +
                        "ON tl.toy_ID = tir.toy_ID\n" +
                        "WHERE tl.ageGroupID = "+(ageGroup.ordinal()+1));
    }
    public List<Toy> sortToysBySize(){
        return execToyQuery("""
                                SELECT tl.[toy_ID]
                                      ,[t_name]
                                      ,[ageGroupID]
                                      ,[t_sizeID]
                                      ,[price]
                                FROM [Course_Work_Play_Room].[Toys].[ToysList] AS tl
                                JOIN #ToysInRoom AS tir
                                ON tl.toy_ID = tir.toy_ID
                                ORDER BY tl.t_sizeID
                                """);
    }
    public List<Toy> sortToysByPrice(){
        return execToyQuery("""
                                SELECT tl.[toy_ID]
                                      ,[t_name]
                                      ,[ageGroupID]
                                      ,[t_sizeID]
                                      ,[price]
                                FROM [Course_Work_Play_Room].[Toys].[ToysList] AS tl
                                JOIN #ToysInRoom AS tir
                                ON tl.toy_ID = tir.toy_ID
                                ORDER BY tl.price
                                """);
    }
    public List<Toy> sortToysByAmount(){
        return execToyQuery("""
                                SELECT tl.[toy_ID]
                                        ,[t_name]
                                        ,[ageGroupID]
                                        ,[t_sizeID]
                                        ,[price]
                                FROM [Course_Work_Play_Room].[Toys].[ToysList] AS tl
                                JOIN #ToysInRoom AS tir
                                ON tl.toy_ID = tir.toy_ID
                                ORDER BY tir.[amount]
                                """);
    }
    public List<Toy> sortToysByAgeGroup(){
        return execToyQuery("""
                                                    SELECT tl.[toy_ID]
                                                          ,[t_name]
                                                          ,[ageGroupID]
                                                          ,[t_sizeID]
                                                          ,[price]
                                                    FROM [Course_Work_Play_Room].[Toys].[ToysList] AS tl
                                                    JOIN #ToysInRoom AS tir
                                                    ON tl.toy_ID = tir.toy_ID
                                                    ORDER BY tl.[ageGroupID]                                
                                                    """);
    }

    public int getInitialMoney() {
        return initialMoney;
    }
    public void setInitialMoney(int initialMoney) {
        if(initialMoney < 0) throw new IllegalArgumentException();
        this.initialMoney = initialMoney;
    }
    protected void addToys(){
        StringBuilder query = new StringBuilder("INSERT INTO #ToysInRoom([toy_ID],[amount],[total_price]) VALUES ");
        toyEntityMap.forEach((key, value) -> query.append("(" + key + "," + value.getAmount() + "," + value.getTotalPrice() + "),"));
        query.deleteCharAt(query.length()-1);
        try(Statement st = getConnection().createStatement()){
            st.execute(query.toString());
        }catch (SQLException e){
            System.err.println(e.getSQLState());
        }
    }
    protected void approveList(){
        if(!ifPlaying()){
            throw new IllegalStateException();
        }
        addToys();
    }
}



package PlayRoom;

import Child.Child;
import Child.AgeGroup;
import DAO.AdmDAO;
import Toy.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

//Singleton
public class PlayRoom {
    private static final int MAXCAPACITY = 20;
    private Connection connection;
    private List<Child> childrenInRoom = new ArrayList<>(MAXCAPACITY);
    ToyList toyList;
    private String name;

    private Set<AgeGroup> ageGroups;
    private boolean isPlaying;
    private static PlayRoom playRoom;
    public PlayRoom addAgeGroup(AgeGroup... groups){
        check();
        if(ageGroups == null)
            ageGroups = new HashSet<AgeGroup>();
        ageGroups.addAll(Arrays.asList(groups));
        return this;
    }
    private PlayRoom(Connection connection,int initialMoney,  AgeGroup... ageGroups) {
        addAgeGroup(ageGroups);
        this.toyList = new ToyList(initialMoney);
        this.connection = connection;
    }

    public static PlayRoom getPlayRoom(Connection connection){
       return getPlayRoom(connection,0);
    }
    public static PlayRoom getPlayRoom(Connection connection,int initialMoney, AgeGroup... ageGroups){
        if (playRoom == null) {
            playRoom = new PlayRoom(connection,initialMoney,ageGroups);
        }
        return playRoom.addAgeGroup(ageGroups);
    }

    public boolean registerChild(Child child){
        check();
        if(childrenInRoom.size() < MAXCAPACITY){
            return childrenInRoom.add(child);
        }else {
            return false;
        }
    }

    private void addChildren(){
        StringBuilder query = new StringBuilder("INSERT INTO [Client].[Registered_Children]" +
                "([fname],[lname],[ageGroupID],[parent_contact]) VALUES");
        childrenInRoom.forEach(ch -> query.append("('" + ch.getFname() + "','" + ch.getLname() + "'," + ch.getAgeGroup().getOrd() + ",'" + ch.getContact()+"'),"));
        query.deleteCharAt(query.length()-1);
        try{
            Statement st = connection.createStatement();
            st.execute(query.toString());
        }catch (SQLException e){
            System.err.println(e.getSQLState());
        }
    }
    //private outo
    public void startGroup() {
        isPlaying = true;
        if(ageGroups == null)
        try{
            Statement st = connection.createStatement();
            st.execute("""
                        CREATE TABLE #ToysInRoom(
                            [toy_ID] [int] PRIMARY KEY,
                            [amount] [int] NULL,
                            [total_price] [int] NULL,
                        );
                        """);
        }catch (SQLException e){
            System.err.println(e.getSQLState());
        }
        addChildren();
        toyList.approveList();
    }

    public void freeRoom(){
        try{
            Statement st = connection.createStatement();
            st.execute("""
                            TRUNCATE TABLE #ToysInRoom
                            """);
        }catch (SQLException e){
            System.err.println(e.getSQLState());
        }
        toyList.freeList();
        System.out.println("Кімнату звільнено.");
    }

    public ToyList getToyList() {

           return toyList;
    }
    private void check(){
        if(isPlaying){
            throw new IllegalStateException();
        }
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public class ToyList {
        private int initialMoney;
        private Map<Integer, ToyEntityInfo> toyEntityMap;

        public ToyList() { }
        public ToyList(int initialMoney) {
            setInitialMoney(initialMoney);
        }

        private int fromWhich(int money, List<Toy> toyList1){
            int i = 0;
            while(money<toyList1.get(i).getPrice() && i<(toyList1.size()-1))
                i++;
            return i;
        }

        public List<Toy> listFromMap(List<Toy> allToys) {
            check();
            List<Toy> result = new ArrayList<>();
            toyEntityMap.entrySet().stream().forEach(e -> allToys.stream().forEach(
                    t -> {
                        if (t.getId() == e.getKey())
                            for (int i =0;i< e.getValue().getAmount();i++) {
                                result.add(t);
                            }
                    }));
            return result;
        }
        public Map<Integer, ToyEntityInfo> CreateToyMap(List<Toy>... ageGroupToys){
            check();
            toyEntityMap = new HashMap<>();
            List<List<Toy>> sublists = new ArrayList<>(ageGroups.size());
            for (AgeGroup ageGr: ageGroups) {
                for (List<Toy> list: ageGroupToys) {
                    if(list.get(0).getAgeGroup()==ageGr){
                        sublists.add(list);
                        break;
                    }
                }
                sublists.forEach(s -> s.sort((Comparator.comparingInt((Toy t) -> t.getPrice()).reversed())));
            }
            int money = getInitialMoney();
            int added;
            Random rand = new Random();
           do{
               added=0;
                for (List<Toy> list: sublists ) {
                    int ind = fromWhich(money, list);
                    if(list.size() - ind -1 == 0)
                        continue;
                    int choose = rand.nextInt(ind,list.size());
                    int id = list.get(choose).getId();
                    int price = list.get(choose).getPrice();
                    if(!toyEntityMap.containsKey(id)){
                        toyEntityMap.put(id,new ToyEntityInfo());
                    toyEntityMap.get(id).addToAmount().addTotalPrice(price);
                    money -= price;
                    added++;
                }
             }
            }while (added>0 && money>0);
            return toyEntityMap;
        }
        public void freeList(){
            toyEntityMap.clear();
        }

        public List<Toy> toysInRoom(){
            List<Toy> toyList = new ArrayList<>();
            try{
                Statement st = connection.createStatement();
                ResultSet result = st.executeQuery(
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
                while (result.next()) {
                    toyList.add(AdmDAO.createToyObj(result));
                }
            }catch (SQLException e){
                System.err.println(e.getSQLState());
            }
            return toyList;
        }
        public List<Toy> toySublistByAgeGroup(AgeGroup ageGroup){
            List<Toy> toyList = new ArrayList<>();
            try{
                Statement st = connection.createStatement();
                ResultSet result = st.executeQuery(
                        "SELECT tl.[toy_ID]\n" +
                        "      ,[t_name]\n" +
                        "      ,[ageGroupID]\n" +
                        "      ,[t_sizeID]\n" +
                        "      ,[price]\n" +
                        "FROM [Course_Work_Play_Room].[Toys].[ToysList] AS tl\n" +
                        "JOIN #ToysInRoom AS tir\n" +
                        "ON tl.toy_ID = tir.toy_ID\n" +
                        "WHERE tl.ageGroupID = "+(ageGroup.ordinal()+1));
                while (result.next()) {
                    toyList.add(AdmDAO.createToyObj(result,AgeGroup.TODDLER));
                }
            }catch (SQLException e){
                System.err.println(e.getSQLState());
            }
            return toyList;
        }

        public List<Toy> sortToysByPrice(){
            List<Toy> toyList = new ArrayList<>();
            try{
                Statement st = connection.createStatement();
                ResultSet result = st.executeQuery("""
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
                while (result.next()) {
                    toyList.add(AdmDAO.createToyObj(result));
                }
            }catch (SQLException e){
                System.err.println(e.getSQLState());
            }
            return toyList;
        }
        public List<Toy> sortToysByAmount(){
            List<Toy> toyList = new ArrayList<>();
            try{
                Statement st = connection.createStatement();
                ResultSet result = st.executeQuery("""
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
                while (result.next()) {
                    toyList.add(AdmDAO.createToyObj(result));
                }
            }catch (SQLException e){
                System.err.println(e.getSQLState());
            }
            return toyList;
        }
        public List<Toy> sortToysByAgeGroup(){
            List<Toy> toyList = new ArrayList<>();
            try{
                Statement st = connection.createStatement();
                ResultSet result = st.executeQuery("""
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
                while (result.next()) {
                    toyList.add(AdmDAO.createToyObj(result));
                }
            }catch (SQLException e){
                System.err.println(e.getSQLState());
            }
            return toyList;
        }

        public int getInitialMoney() {
            return initialMoney;
        }
        public void setInitialMoney(int initialMoney) {
            if(initialMoney < 0) throw new IllegalArgumentException();
            this.initialMoney = initialMoney;
        }
        private void addToys(){
            StringBuilder query = new StringBuilder("INSERT INTO #ToysInRoom([toy_ID],[amount],[total_price]) VALUES ");
            toyEntityMap.forEach((key, value) -> query.append("(" + key + "," + value.getAmount() + "," + value.getTotalPrice() + "),"));
            query.deleteCharAt(query.length()-1);
            try{
                Statement st = connection.createStatement();
                st.execute(query.toString());
            }catch (SQLException e){
                System.err.println(e.getSQLState());
            }
        }

        private void approveList(){
            if(!isPlaying){
                throw new IllegalStateException();
            }
            addToys();
        }
    }

}

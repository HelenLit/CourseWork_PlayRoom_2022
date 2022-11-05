package PlayRoom;

import Child.AgeGroup;
import Child.Child;
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
            ageGroups = new HashSet<>();
        ageGroups.addAll(Arrays.asList(groups));
        return this;
    }
    private PlayRoom(Connection connection, int initialMoney, AgeGroup... ageGroups) {
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
    protected List<Child> execChildListQuery(String query){
        List<Child> childrenList = new ArrayList<>();
        try(Statement st = connection.createStatement();){
            ResultSet result = st.executeQuery(query);
            while (result.next()) {
                childrenList.add(Child.createChildObj(result));
            }
        }catch (SQLException e){
            System.err.println(Arrays.toString(e.getStackTrace()));
            System.exit(e.getErrorCode());
        }
        return childrenList;
    }
    public List<Child> ChildrenList() {
        return execChildListQuery("""
                USE [Course_Work_Play_Room]
                SELECT *
                FROM [Client].[Registered_Children]
                """);
    }
    protected void execChildVoidQuery(String query){
        try(Statement st = connection.createStatement();){
            ResultSet result = st.executeQuery(query);
        }catch (SQLException e){
            System.err.println(Arrays.toString(e.getStackTrace()));
            System.exit(e.getErrorCode());
        }
    }
    public void FreeChildrenList() {
       execChildVoidQuery("""
                TRUNCATE TABLE [Client].[Registered_Children]
                """);
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
    private int autoPrepRoom(){
        List<AgeGroup> ageGroupList = new ArrayList<>();
        for (Child ch: childrenInRoom) {
            if(!ageGroupList.contains(ch.getAgeGroup()))
                ageGroupList.add(ch.getAgeGroup());
        }
        ageGroups.addAll(ageGroupList);
        return ageGroupList.size();
    }
    public void startGroup() {
        isPlaying = true;
        if(toyList.toyEntityMap.size()==0){
            System.err.println("Перед вікриттям кімнати, має бути створений список іграшок");
            return;
        }
        toyList.createToyList();
        addChildren();
        toyList.approveList();
    }
    public void freeRoom(){
        toyList.trunkToyList();
        toyList.freeList();
        FreeChildrenList();
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
    public class ToyList extends AbstractToyList{
        @Override
        Connection getConnection() {
            return connection;
        }
        @Override
        boolean ifPlaying() {
            return isPlaying;
        }
        public ToyList() {}

        public ToyList(int initialMoney) {
            super(initialMoney);
        }

        @SafeVarargs
        @Override
        public final Map<Integer, ToyEntityInfo> CreateToyMap(List<Toy>... ageGroupToys) {
            check();
            if(autoPrepRoom() == 0){
                System.err.println("Перед вікриттям кімнати, має зареєструватись хоча б 1 дитина");
                return null;
            }
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
    }

}

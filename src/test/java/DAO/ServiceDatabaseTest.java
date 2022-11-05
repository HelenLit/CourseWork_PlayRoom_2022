package DAO;
import Child.*;
import PlayRoom.PlayRoom;
import Toy.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ServiceDatabaseTest {
    @Mock
    AdmDAO adm;
    @Mock
    PlayRoom playRoom;
    @Mock
    PlayRoom.ToyList toyList;
    int initialMoney = 10000;
    String name = "Кімната ігор для діток";
    Child[] children = {new Child("Богдан","Крамаренко", AgeGroup.TEENAGER,"192(82)623-13-41"),
                        new Child("Тетяна","Пономарчук", AgeGroup.MIDDLECHILD,"3(8464)345-61-39"),
                        new Child("Любов","Середа", AgeGroup.MIDDLECHILD,"116(63)369-83-02"),
                        new Child("Денис","Броварчук", AgeGroup.MIDDLECHILD,"3(8464)345-61-39")};

    Toy[] toys = {new Toy(16,"Paints",540,AgeGroup.TEENAGER, ToySize.TINY),
            new Toy(19,"Munchkin",650,AgeGroup.TEENAGER, ToySize.MEDIUM),
            new Toy(20,"Jenga",500,AgeGroup.TEENAGER, ToySize.SMALL),
            new Toy(7,"Plasticine",120,AgeGroup.MIDDLECHILD, ToySize.TINY),
            new Toy(24,"Game consol X7",1500,AgeGroup.TEENAGER, ToySize.SMALL),
            new Toy(12,"Car",250,AgeGroup.MIDDLECHILD, ToySize.TINY),
            new Toy(13,"LEGO",900,AgeGroup.MIDDLECHILD, ToySize.MEDIUM),
            new Toy(14,"Puzzle",160,AgeGroup.MIDDLECHILD, ToySize.SMALL)};

    @BeforeEach
    void setup() {
        when(toyList.getInitialMoney()).thenReturn(initialMoney);
        when(toyList.toysInRoom()).thenReturn(Arrays.asList(toys));
        when(toyList.sortToysByPrice()).thenReturn(Arrays.stream(toys).sorted(Comparator.comparingInt(Toy::getPrice)).collect(Collectors.toList()));
        when(toyList.sortToysBySize()).thenReturn(Arrays.stream(toys).sorted(Comparator.comparingInt((Toy t) -> t.getToySize().getOrd())).collect(Collectors.toList()));
        when(toyList.sortToysByAgeGroup()).thenReturn(Arrays.stream(toys).sorted(Comparator.comparingInt((Toy t) -> t.getAgeGroup().getOrd())).collect(Collectors.toList()));
        when(toyList.allToysByAgeGroup(AgeGroup.TODDLER)).thenReturn(Arrays.stream(toys).filter(t->t.getAgeGroup().equals(AgeGroup.TODDLER)).toList());
        when(toyList.allToysByAgeGroup(AgeGroup.MIDDLECHILD)).thenReturn(Arrays.stream(toys).filter(t->t.getAgeGroup().equals(AgeGroup.MIDDLECHILD)).toList());
        when(toyList.allToysByAgeGroup(AgeGroup.TEENAGER)).thenReturn(Arrays.stream(toys).filter(t->t.getAgeGroup().equals(AgeGroup.TEENAGER)).toList());
        when(playRoom.ChildrenList()).thenReturn(Arrays.asList(children));
        when(playRoom.getToyList()).thenReturn(toyList);
        when(playRoom.getName()).thenReturn(name);
        when(adm.getToyList()).thenReturn(toyList);
        when(adm.createPlayRoom()).thenReturn(playRoom);
        when(adm.getPlayRoom()).thenReturn(playRoom);
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void getInitialMoneyTest() {
        assertEquals(adm.getToyList().getInitialMoney(),10000);
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void getToyListTest() {
        assertEquals(adm.getToyList(),playRoom.getToyList());
    }
    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void createPlayRoomTest() {
        assertEquals(adm.createPlayRoom(),playRoom);
    }
    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void getPlayRoomTest() {
        assertEquals(adm.getPlayRoom(),playRoom);
    }
    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void childrenList() {
        assertIterableEquals(playRoom.ChildrenList(), Arrays.asList(
                new Child("Богдан","Крамаренко", AgeGroup.TEENAGER,"192(82)623-13-41"),
                new Child("Тетяна","Пономарчук", AgeGroup.MIDDLECHILD,"3(8464)345-61-39"),
                new Child("Любов","Середа", AgeGroup.MIDDLECHILD,"116(63)369-83-02"),
                new Child("Денис","Броварчук", AgeGroup.MIDDLECHILD,"3(8464)345-61-39")));
    }
    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void getToyList() {
        assertEquals(playRoom.getToyList(),adm.getToyList());
    }
    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void getName() {
        assertTrue(playRoom.getName().equals(name));
    }
    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void toysInRoom() {
        assertIterableEquals(adm.getToyList().toysInRoom(), Arrays.asList(new Toy(16,"Paints",540,AgeGroup.TEENAGER, ToySize.TINY),
                new Toy(19,"Munchkin",650,AgeGroup.TEENAGER, ToySize.MEDIUM),
                new Toy(20,"Jenga",500,AgeGroup.TEENAGER, ToySize.SMALL),
                new Toy(7,"Plasticine",120,AgeGroup.MIDDLECHILD, ToySize.TINY),
                new Toy(24,"Game consol X7",1500,AgeGroup.TEENAGER, ToySize.SMALL),
                new Toy(12,"Car",250,AgeGroup.MIDDLECHILD, ToySize.TINY),
                new Toy(13,"LEGO",900,AgeGroup.MIDDLECHILD, ToySize.MEDIUM),
                new Toy(14,"Puzzle",160,AgeGroup.MIDDLECHILD, ToySize.SMALL)));
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void toySublistByAgeGroup() {
        assertIterableEquals(adm.getToyList().allToysByAgeGroup(AgeGroup.TODDLER), Stream.of(new Toy(16,"Paints",540,AgeGroup.TEENAGER, ToySize.TINY),
                new Toy(19,"Munchkin",650,AgeGroup.TEENAGER, ToySize.MEDIUM),
                new Toy(20,"Jenga",500,AgeGroup.TEENAGER, ToySize.SMALL),
                new Toy(7,"Plasticine",120,AgeGroup.MIDDLECHILD, ToySize.TINY),
                new Toy(24,"Game consol X7",1500,AgeGroup.TEENAGER, ToySize.SMALL),
                new Toy(12,"Car",250,AgeGroup.MIDDLECHILD, ToySize.TINY),
                new Toy(13,"LEGO",900,AgeGroup.MIDDLECHILD, ToySize.MEDIUM),
                new Toy(14,"Puzzle",160,AgeGroup.MIDDLECHILD, ToySize.SMALL))
                .filter(t->t.getAgeGroup()
                .equals(AgeGroup.TODDLER))
                .toList());

        assertIterableEquals(adm.getToyList().allToysByAgeGroup(AgeGroup.MIDDLECHILD), Stream.of(new Toy(16,"Paints",540,AgeGroup.TEENAGER, ToySize.TINY),
                        new Toy(19,"Munchkin",650,AgeGroup.TEENAGER, ToySize.MEDIUM),
                        new Toy(20,"Jenga",500,AgeGroup.TEENAGER, ToySize.SMALL),
                        new Toy(7,"Plasticine",120,AgeGroup.MIDDLECHILD, ToySize.TINY),
                        new Toy(24,"Game consol X7",1500,AgeGroup.TEENAGER, ToySize.SMALL),
                        new Toy(12,"Car",250,AgeGroup.MIDDLECHILD, ToySize.TINY),
                        new Toy(13,"LEGO",900,AgeGroup.MIDDLECHILD, ToySize.MEDIUM),
                        new Toy(14,"Puzzle",160,AgeGroup.MIDDLECHILD, ToySize.SMALL))
                .filter(t->t.getAgeGroup()
                        .equals(AgeGroup.MIDDLECHILD))
                .toList());

        assertIterableEquals(adm.getToyList().allToysByAgeGroup(AgeGroup.TEENAGER), Stream.of(new Toy(16,"Paints",540,AgeGroup.TEENAGER, ToySize.TINY),
                        new Toy(19,"Munchkin",650,AgeGroup.TEENAGER, ToySize.MEDIUM),
                        new Toy(20,"Jenga",500,AgeGroup.TEENAGER, ToySize.SMALL),
                        new Toy(7,"Plasticine",120,AgeGroup.MIDDLECHILD, ToySize.TINY),
                        new Toy(24,"Game consol X7",1500,AgeGroup.TEENAGER, ToySize.SMALL),
                        new Toy(12,"Car",250,AgeGroup.MIDDLECHILD, ToySize.TINY),
                        new Toy(13,"LEGO",900,AgeGroup.MIDDLECHILD, ToySize.MEDIUM),
                        new Toy(14,"Puzzle",160,AgeGroup.MIDDLECHILD, ToySize.SMALL))
                .filter(t->t.getAgeGroup()
                        .equals(AgeGroup.TEENAGER))
                .toList());
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void sortToysBySize() {
        assertIterableEquals(adm.getToyList().sortToysBySize(),Stream.of(new Toy(16,"Paints",540,AgeGroup.TEENAGER, ToySize.TINY),
                        new Toy(19,"Munchkin",650,AgeGroup.TEENAGER, ToySize.MEDIUM),
                        new Toy(20,"Jenga",500,AgeGroup.TEENAGER, ToySize.SMALL),
                        new Toy(7,"Plasticine",120,AgeGroup.MIDDLECHILD, ToySize.TINY),
                        new Toy(24,"Game consol X7",1500,AgeGroup.TEENAGER, ToySize.SMALL),
                        new Toy(12,"Car",250,AgeGroup.MIDDLECHILD, ToySize.TINY),
                        new Toy(13,"LEGO",900,AgeGroup.MIDDLECHILD, ToySize.MEDIUM),
                        new Toy(14,"Puzzle",160,AgeGroup.MIDDLECHILD, ToySize.SMALL))
                .sorted(Comparator
                        .comparingInt((Toy t) -> t.getToySize().getOrd()))
                .collect(Collectors.toList()));
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void sortToysByPrice() {
        assertIterableEquals(adm.getToyList().sortToysByPrice(),Stream.of(new Toy(16,"Paints",540,AgeGroup.TEENAGER, ToySize.TINY),
                        new Toy(19,"Munchkin",650,AgeGroup.TEENAGER, ToySize.MEDIUM),
                        new Toy(20,"Jenga",500,AgeGroup.TEENAGER, ToySize.SMALL),
                        new Toy(7,"Plasticine",120,AgeGroup.MIDDLECHILD, ToySize.TINY),
                        new Toy(24,"Game consol X7",1500,AgeGroup.TEENAGER, ToySize.SMALL),
                        new Toy(12,"Car",250,AgeGroup.MIDDLECHILD, ToySize.TINY),
                        new Toy(13,"LEGO",900,AgeGroup.MIDDLECHILD, ToySize.MEDIUM),
                        new Toy(14,"Puzzle",160,AgeGroup.MIDDLECHILD, ToySize.SMALL))
                .sorted(Comparator
                        .comparingInt(Toy::getPrice))
                .collect(Collectors.toList()));
    }


    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void sortToysByAgeGroup() {
        assertIterableEquals(adm.getToyList().sortToysByAgeGroup(),Stream.of(new Toy(16,"Paints",540,AgeGroup.TEENAGER, ToySize.TINY),
                        new Toy(19,"Munchkin",650,AgeGroup.TEENAGER, ToySize.MEDIUM),
                        new Toy(20,"Jenga",500,AgeGroup.TEENAGER, ToySize.SMALL),
                        new Toy(7,"Plasticine",120,AgeGroup.MIDDLECHILD, ToySize.TINY),
                        new Toy(24,"Game consol X7",1500,AgeGroup.TEENAGER, ToySize.SMALL),
                        new Toy(12,"Car",250,AgeGroup.MIDDLECHILD, ToySize.TINY),
                        new Toy(13,"LEGO",900,AgeGroup.MIDDLECHILD, ToySize.MEDIUM),
                        new Toy(14,"Puzzle",160,AgeGroup.MIDDLECHILD, ToySize.SMALL))
                .sorted(Comparator
                        .comparingInt((Toy t) -> t.getAgeGroup().getOrd()))
                .collect(Collectors.toList()));
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void getInitialMoney() {
        assertEquals(adm.getToyList().getInitialMoney(),toyList.getInitialMoney());
    }
}

package Child;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChildTest {
    Child ch;
    @BeforeEach
    void setup() {
        ch = new Child();
    }
    @Test
    void setFnameTest() {
        assertThrows(IllegalArgumentException.class,()->ch.setFname(null));
    }
    @Test
    void setLnameTest() {
        assertThrows(IllegalArgumentException.class,()->ch.setLname(null));
    }
    @Test
    void setAgeGroupTest1() {
        assertThrows(IllegalArgumentException.class,()->ch.setAgeGroup(null));
    }
    @Test
    void setAgeGroupTest2() {
        assertThrows(IllegalArgumentException.class,()->ch.setAgeGroup(0));
    }
    @Test
    void setContactTest() {
        assertThrows(IllegalArgumentException.class,()->ch.setContact(null));
    }




}
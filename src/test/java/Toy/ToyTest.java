package Toy;

import Child.AgeGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ToyTest {

    @Test
    void createToyTest1() {
        assertEquals(new Toy(12,"Car",250, AgeGroup.MIDDLECHILD, ToySize.TINY),Toy.createToy(12,"Car",250,AgeGroup.MIDDLECHILD, ToySize.TINY));
    }

    @Test
    void CreateToyTest2() {
        assertEquals(new Toy(12,"Car",250, AgeGroup.MIDDLECHILD, ToySize.TINY),Toy.createToy(12,"Car",250,2, ToySize.TINY));

    }
}
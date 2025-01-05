import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

class HorseTest {

    @Test
    void nameNullException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()-> new Horse(null, 10, 0));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n"})
    void nameInvalidException(String invalid) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()-> new Horse(invalid, 10, 0));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-0.1, -1.0, -100})
    void speedInvalidException(double invalid){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()-> new Horse("Moose", invalid, 0));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-0.1, -1.0, -100})
    void distanceInvalidException(double invalid){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()-> new Horse("Moose", 10, invalid));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void expectedName(){
        String expected = "Moose";
        Horse horse = new Horse(expected, 10, 0);
        assertEquals(expected, horse.getName());
    }

    @Test
    void expectedSpeed(){
        double expected = 10;
        Horse horse = new Horse("Moose", expected, 0);
        assertEquals(expected, horse.getSpeed());
    }

    @Test
    void expectedDistance(){
        double expected = 1.5;
        Horse horse = new Horse("Moose", 10, expected);
        assertEquals(expected, horse.getDistance());
    }

    @Test
    void expectedNullDistance(){
        Horse horse = new Horse("Moose", 10);
        assertEquals(0.0, horse.getDistance());
    }

    @Test
    void moveRandomNumber(){

       try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)){
           Horse horse = new Horse("Moose", 10, 15);
           horse.move();
           mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.4, 0.5, 0.8})
    void moveDistance(double fakeValue) {

        double min = 0.2;
        double max = 0.9;
        double distance = 10;
        double speed = 5;
        String name = "Moose";

        Horse horse = new Horse(name, speed, distance);

        double excepted = distance + speed * fakeValue;

        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {

            mockedStatic.when(() -> Horse.getRandomDouble(min, max)).thenReturn(fakeValue);

            horse.move();

        }

        assertEquals(excepted, horse.getDistance());
    }

}
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;


class HippodromeTest {

    @Test
    void horsesIsNull(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()-> new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    void horsesIsEmpty(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()-> new Hippodrome(new ArrayList<>()));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    void getHorsesSubsequence(){

        List<Horse> horses = new ArrayList<>();
        for (int i = 1; i <= 30; i++){
            horses.add(new Horse("Moos " + i, 1 * i, 10 * i));
        }

        Hippodrome hippodrome = new Hippodrome(horses);

        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    void moveAll(){
        List<Horse> horses = new ArrayList<>();

        for (int j = 1; j <= 50; j++){
            Horse horseMock = mock(Horse.class);
            horses.add(horseMock);
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();
        for (Horse horse: horses){
            verify(horse).move();
        }
    }

    @Test
    void getWinner(){
        List<Horse> horses = new ArrayList<>();
        horses.add(new Horse("Moose 1", 10, 10));
        horses.add(new Horse("Moose 2", 10, 13));
        horses.add(new Horse("Moose 3", 10, 15));

        Hippodrome hippodrome = new Hippodrome(horses);
        Horse winner = hippodrome.getWinner();

        assertEquals(winner, horses.get(2));
    }
}
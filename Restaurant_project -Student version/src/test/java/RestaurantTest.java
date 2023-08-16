import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    LocalTime openingTime;
    LocalTime closingTime;

    @BeforeEach
    public void beforeEach(){
        openingTime = LocalTime.parse("10:30:00");
        closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
    }
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){

        Restaurant resSpy = Mockito.spy(restaurant);
        Mockito.doReturn(openingTime.plusHours(4)).when(resSpy).getCurrentTime();
        assertEquals(true, resSpy.isRestaurantOpen());
        Mockito.doReturn(closingTime.minusHours(4)).when(resSpy).getCurrentTime();
        assertEquals(true, resSpy.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){

        Restaurant resSpy = Mockito.spy(restaurant);
        Mockito.doReturn(openingTime.minusHours(4)).when(resSpy).getCurrentTime();
        assertEquals(false, resSpy.isRestaurantOpen());
        Mockito.doReturn(closingTime.plusHours(4)).when(resSpy).getCurrentTime();
        assertEquals(false, resSpy.isRestaurantOpen());

    }

    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    @Test
    public void adding_item_will_show_the_total_price() {

        restaurant.addToMenu("Sweet corn soup",20);
        restaurant.addToMenu("Vegetable lasagne", 30);
        List itemList = restaurant.getMenu();
        assertEquals(50,restaurant.calculateTotalPrice(itemList));
    }
}
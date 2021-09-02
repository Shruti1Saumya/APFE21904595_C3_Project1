import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

class RestaurantTest {
    Restaurant restaurant;

    //Searching for restaurant if its open//
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        Restaurant restaurant=Mockito.mock(Restaurant.class);
        LocalTime TwoHoursBeforeClosing= LocalTime.now().minusHours(2);
        Mockito.when(restaurant.getCurrentTime()).thenReturn(TwoHoursBeforeClosing);
        assertTrue(restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        Restaurant restaurant=Mockito.mock(Restaurant.class);
        LocalTime TwoHoursAfterClosing= LocalTime.now().plusHours(2);
        Mockito.when(restaurant.getCurrentTime()).thenReturn(TwoHoursAfterClosing);
        assertFalse(restaurant.isRestaurantOpen());

    }

    //Navigating through the menu of food items//

    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }

    //TDD approach for calculating the sum of food items added
    @Test
    public void when_sum_of_foodItems_should_be_returned(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",110);
        restaurant.addToMenu("French Fries", 250);

        Item item1= new Item("Sweet corn soup",110);
        Item item2= new Item("French Fries",250);
        List<Item> itemList= new ArrayList<>();
        itemList.add(0,item1);
        itemList.add(1,item2);

        int totalValue= restaurant.calculateSum(itemList);
        assertNotEquals(0,360);
        assertEquals(260,totalValue);
    }

}
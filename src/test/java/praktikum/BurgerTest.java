package praktikum;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BurgerTest {

    private Burger burger;

    @Before
    public void setUp() {
        burger = new Burger();
    }

    // --- setBuns ---

    @Test
    public void setBunsSetsBunOnBurger() {
        Bun bun = mock(Bun.class);
        when(bun.getName()).thenReturn("Флюоресцентная булка");
        when(bun.getPrice()).thenReturn(50.0f);

        burger.setBuns(bun);

        String receipt = burger.getReceipt();
        assertTrue(receipt.startsWith("(==== Флюоресцентная булка ====)"));
    }

    // --- addIngredient ---

    @Test
    public void addIngredientIncreasesSizeToTwo() {
        Ingredient firstIngredient = mock(Ingredient.class);
        Ingredient secondIngredient = mock(Ingredient.class);

        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);

        assertEquals(2, burger.ingredients.size());
    }

    @Test
    public void addIngredientKeepsOrderFirstAtIndex0() {
        Ingredient firstIngredient = mock(Ingredient.class);
        Ingredient secondIngredient = mock(Ingredient.class);

        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);

        assertSame(firstIngredient, burger.ingredients.get(0));
    }

    @Test
    public void addIngredientKeepsOrderSecondAtIndex1() {
        Ingredient firstIngredient = mock(Ingredient.class);
        Ingredient secondIngredient = mock(Ingredient.class);

        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);

        assertSame(secondIngredient, burger.ingredients.get(1));
    }

    // --- removeIngredient ---

    @Test
    public void removeIngredientReducesSizeToTwo() {
        Ingredient firstIngredient = mock(Ingredient.class);
        Ingredient secondIngredient = mock(Ingredient.class);
        Ingredient thirdIngredient = mock(Ingredient.class);

        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);
        burger.addIngredient(thirdIngredient);

        burger.removeIngredient(1);

        assertEquals(2, burger.ingredients.size());
    }

    @Test
    public void removeIngredientKeepsFirstElement() {
        Ingredient firstIngredient = mock(Ingredient.class);
        Ingredient secondIngredient = mock(Ingredient.class);
        Ingredient thirdIngredient = mock(Ingredient.class);

        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);
        burger.addIngredient(thirdIngredient);

        burger.removeIngredient(1);

        assertSame(firstIngredient, burger.ingredients.get(0));
    }

    @Test
    public void removeIngredientMovesThirdToSecondPosition() {
        Ingredient firstIngredient = mock(Ingredient.class);
        Ingredient secondIngredient = mock(Ingredient.class);
        Ingredient thirdIngredient = mock(Ingredient.class);

        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);
        burger.addIngredient(thirdIngredient);

        burger.removeIngredient(1);

        assertSame(thirdIngredient, burger.ingredients.get(1));
    }

    @Test
    public void removeIngredientRemovesSecondElement() {
        Ingredient firstIngredient = mock(Ingredient.class);
        Ingredient secondIngredient = mock(Ingredient.class);
        Ingredient thirdIngredient = mock(Ingredient.class);

        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);
        burger.addIngredient(thirdIngredient);

        burger.removeIngredient(1);

        assertFalse(burger.ingredients.contains(secondIngredient));
    }

    // --- moveIngredient ---

    @Test
    public void moveIngredientUpdatesIndex0ToSecondIngredient() {
        Ingredient firstIngredient = mock(Ingredient.class);
        Ingredient secondIngredient = mock(Ingredient.class);
        Ingredient thirdIngredient = mock(Ingredient.class);

        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);
        burger.addIngredient(thirdIngredient);

        burger.moveIngredient(0, 2);

        assertSame(secondIngredient, burger.ingredients.get(0));
    }

    @Test
    public void moveIngredientUpdatesIndex1ToThirdIngredient() {
        Ingredient firstIngredient = mock(Ingredient.class);
        Ingredient secondIngredient = mock(Ingredient.class);
        Ingredient thirdIngredient = mock(Ingredient.class);

        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);
        burger.addIngredient(thirdIngredient);

        burger.moveIngredient(0, 2);

        assertSame(thirdIngredient, burger.ingredients.get(1));
    }

    @Test
    public void moveIngredientUpdatesIndex2ToFirstIngredient() {
        Ingredient firstIngredient = mock(Ingredient.class);
        Ingredient secondIngredient = mock(Ingredient.class);
        Ingredient thirdIngredient = mock(Ingredient.class);

        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);
        burger.addIngredient(thirdIngredient);

        burger.moveIngredient(0, 2);

        assertSame(firstIngredient, burger.ingredients.get(2));
    }

    // --- getPrice ---

    @Test
    public void getPriceReturnsSumOfBunsTwicePlusIngredients() {
        Bun bun = mock(Bun.class);
        when(bun.getPrice()).thenReturn(100.0f);
        burger.setBuns(bun);

        Ingredient firstIngredient = mock(Ingredient.class);
        Ingredient secondIngredient = mock(Ingredient.class);
        when(firstIngredient.getPrice()).thenReturn(15.5f);
        when(secondIngredient.getPrice()).thenReturn(24.5f);
        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);

        float actual = burger.getPrice();
        float expected = 100.0f * 2 + 15.5f + 24.5f;

        assertEquals(expected, actual, 0.0001f);
    }

    // --- getReceipt ---

    @Test
    public void getReceiptContainsTopBunLine() {
        Bun bun = mock(Bun.class);
        when(bun.getName()).thenReturn("Люминесцентная булка");
        when(bun.getPrice()).thenReturn(50.0f);
        burger.setBuns(bun);

        Ingredient sauce = mock(Ingredient.class);
        when(sauce.getType()).thenReturn(IngredientType.SAUCE);
        when(sauce.getName()).thenReturn("Space Sauce");
        when(sauce.getPrice()).thenReturn(10.0f);

        Ingredient filling = mock(Ingredient.class);
        when(filling.getType()).thenReturn(IngredientType.FILLING);
        when(filling.getName()).thenReturn("Метеоритное мясо");
        when(filling.getPrice()).thenReturn(25.0f);

        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("(==== Люминесцентная булка ====)"));
    }

    @Test
    public void getReceiptContainsSauceLine() {
        Bun bun = mock(Bun.class);
        when(bun.getName()).thenReturn("Люминесцентная булка");
        when(bun.getPrice()).thenReturn(50.0f);
        burger.setBuns(bun);

        Ingredient sauce = mock(Ingredient.class);
        when(sauce.getType()).thenReturn(IngredientType.SAUCE);
        when(sauce.getName()).thenReturn("Space Sauce");
        when(sauce.getPrice()).thenReturn(10.0f);

        Ingredient filling = mock(Ingredient.class);
        when(filling.getType()).thenReturn(IngredientType.FILLING);
        when(filling.getName()).thenReturn("Метеоритное мясо");
        when(filling.getPrice()).thenReturn(25.0f);

        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("= sauce Space Sauce ="));
    }

    @Test
    public void getReceiptContainsFillingLine() {
        Bun bun = mock(Bun.class);
        when(bun.getName()).thenReturn("Люминесцентная булка");
        when(bun.getPrice()).thenReturn(50.0f);
        burger.setBuns(bun);

        Ingredient sauce = mock(Ingredient.class);
        when(sauce.getType()).thenReturn(IngredientType.SAUCE);
        when(sauce.getName()).thenReturn("Space Sauce");
        when(sauce.getPrice()).thenReturn(10.0f);

        Ingredient filling = mock(Ingredient.class);
        when(filling.getType()).thenReturn(IngredientType.FILLING);
        when(filling.getName()).thenReturn("Метеоритное мясо");
        when(filling.getPrice()).thenReturn(25.0f);

        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("= filling Метеоритное мясо ="));
    }

    @Test
    public void getReceiptContainsPriceLine() {
        Bun bun = mock(Bun.class);
        when(bun.getName()).thenReturn("Люминесцентная булка");
        when(bun.getPrice()).thenReturn(50.0f);
        burger.setBuns(bun);

        Ingredient sauce = mock(Ingredient.class);
        when(sauce.getType()).thenReturn(IngredientType.SAUCE);
        when(sauce.getName()).thenReturn("Space Sauce");
        when(sauce.getPrice()).thenReturn(10.0f);

        Ingredient filling = mock(Ingredient.class);
        when(filling.getType()).thenReturn(IngredientType.FILLING);
        when(filling.getName()).thenReturn("Метеоритное мясо");
        when(filling.getPrice()).thenReturn(25.0f);

        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("Price: "));
    }
}

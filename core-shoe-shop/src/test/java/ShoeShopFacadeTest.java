import com.example.demo.core.DatabaseAdapter;
import com.example.demo.core.ShoeEntity;
import com.example.demo.core.ShoeShopCore;
import com.example.demo.dto.in.ShoeFilter;
import com.example.demo.dto.in.StockUpdate;
import com.example.demo.dto.out.Stock;
import com.example.demo.facade.ShoeShopFacade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.NotSupportedException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class ShoeShopFacadeTest {

    @Autowired
    private DatabaseAdapter databaseAdapter;

    @Autowired
    private ShoeShopFacade shoeShopFacade;

    private final Integer apiVersion = 3;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    public void cleanUp() { reset(databaseAdapter); }

    private ShoeShopCore getShoeShopCore(Integer apiVersion) throws NotSupportedException {
        return shoeShopFacade.get(apiVersion);
    }

    @Test
    void unsupportedVersion() {
        assertThrows(NotSupportedException.class, () -> getShoeShopCore(1));
        assertThrows(NotSupportedException.class, () -> getShoeShopCore(2));
    }

    @Test
    void getEmptyStock() throws NotSupportedException {
        // Arrange
        when(databaseAdapter.getAllShoes()).thenReturn(Collections.emptyList());

        // Act
        var stock = getShoeShopCore(apiVersion).getStock();

        // Assert
        assertEquals(Stock.State.EMPTY, stock.getState());
        assertEquals(Collections.emptyList(), stock.getShoes());
    }

    @Test
    void getFullStock_OneCategory() throws NotSupportedException {
        // Arrange
        var shoeBuilder = ShoeEntity.builder().color("BLACK").size(BigInteger.valueOf(40));
        when(databaseAdapter.getAllShoes()).thenReturn(Collections.nCopies(30, shoeBuilder.build()));

        // Act
        var stock = getShoeShopCore(apiVersion).getStock();

        // Assert
        assertEquals(Stock.State.FULL, stock.getState());
        assertTrue(stock.getShoes().stream().findFirst().isPresent());
        var category = stock.getShoes().stream().findFirst().get();
        assertEquals(30, category.getQuantity());
        assertEquals(ShoeFilter.Color.BLACK, category.getColor());
        assertEquals(BigInteger.valueOf(40), category.getSize());
    }

    @Test
    void getFullStock_MultipleCategories() throws NotSupportedException {
        // Arrange
        var shoeBuilder1 = ShoeEntity.builder().color("BLACK").size(BigInteger.valueOf(40));
        var shoeBuilder2 = ShoeEntity.builder().color("BLUE").size(BigInteger.valueOf(40));
        var shoeBuilder3 = ShoeEntity.builder().color("BLACK").size(BigInteger.valueOf(41));

        List<ShoeEntity> shoeEntities = new ArrayList<>();
        shoeEntities.addAll(Collections.nCopies(10, shoeBuilder1.build()));
        shoeEntities.addAll(Collections.nCopies(10, shoeBuilder2.build()));
        shoeEntities.addAll(Collections.nCopies(10, shoeBuilder3.build()));

        when(databaseAdapter.getAllShoes()).thenReturn(shoeEntities);

        // Act
        var stock = getShoeShopCore(apiVersion).getStock();

        // Assert
        assertEquals(Stock.State.FULL, stock.getState());
        assertEquals(3, stock.getShoes().size());
        assertEquals(10, stock.getShoes().stream().filter(q -> q.getColor().equals(ShoeFilter.Color.BLUE)).findFirst().get().getQuantity());
    }

    @Test
    void getSomeStock() throws NotSupportedException {
        // Arrange
        var shoeBuilder1 = ShoeEntity.builder().color("BLACK").size(BigInteger.valueOf(40));
        var shoeBuilder2 = ShoeEntity.builder().color("BLUE").size(BigInteger.valueOf(43));

        List<ShoeEntity> shoeEntities = new ArrayList<>();
        shoeEntities.addAll(Collections.nCopies(5, shoeBuilder1.build()));
        shoeEntities.addAll(Collections.nCopies(24, shoeBuilder2.build()));

        when(databaseAdapter.getAllShoes()).thenReturn(shoeEntities);

        // Act
        var stock = getShoeShopCore(apiVersion).getStock();

        // Assert
        assertEquals(Stock.State.SOME, stock.getState());
        assertEquals(2, stock.getShoes().size());
        assertEquals(5, stock.getShoes().stream().filter(q -> q.getColor().equals(ShoeFilter.Color.BLACK)).findFirst().get().getQuantity());
        assertEquals(24, stock.getShoes().stream().filter(q -> q.getColor().equals(ShoeFilter.Color.BLUE)).findFirst().get().getQuantity());
    }

    @Test
    void updateStock_AddOneModel() throws NotSupportedException {
        // Arrange
        var stockUpdate = StockUpdate.builder().size(BigInteger.valueOf(42)).color(ShoeFilter.Color.BLACK).quantity(1).build();

        // Act
        getShoeShopCore(apiVersion).updateStock(stockUpdate);

        // Assert
        verify(databaseAdapter, times(1)).saveShoe(ShoeFilter.Color.BLACK, BigInteger.valueOf(42));
    }

    @Test
    void updateStock_AddMultipleModels() throws NotSupportedException {
        // Arrange
        var stockUpdate = StockUpdate.builder().size(BigInteger.valueOf(42)).color(ShoeFilter.Color.BLACK).quantity(2).build();

        // Act
        getShoeShopCore(apiVersion).updateStock(stockUpdate);

        // Assert
        verify(databaseAdapter, times(2)).saveShoe(ShoeFilter.Color.BLACK, BigInteger.valueOf(42));
    }

    @Test
    void updateStock_ShopLimitReached() throws NotSupportedException {
        // Arrange
        when(databaseAdapter.countShoes()).thenReturn(30);
        var stockUpdate = StockUpdate.builder().size(BigInteger.valueOf(42)).color(ShoeFilter.Color.BLACK).quantity(1).build();

        // Act
        getShoeShopCore(apiVersion).updateStock(stockUpdate);

        // Assert
        verify(databaseAdapter, never()).saveShoe(any(), any());
    }

    @Test
    void updateStock_RemoveTooMuch() throws NotSupportedException {
        // Arrange
        when(databaseAdapter.countShoes()).thenReturn(0);
        when(databaseAdapter.countShoes(any(), any())).thenReturn(0);

        var stockUpdate = StockUpdate.builder().size(BigInteger.valueOf(42)).color(ShoeFilter.Color.BLACK).quantity(-1).build();

        // Act
        getShoeShopCore(apiVersion).updateStock(stockUpdate);

        // Assert
        verify(databaseAdapter, never()).removeShoe(any(), any());
        verify(databaseAdapter, never()).saveShoe(any(), any());
    }

    @Test
    void updateStock_RemoveOneModel() throws NotSupportedException {
        // Arrange
        var color = ShoeFilter.Color.BLACK;
        var size = BigInteger.valueOf(42);
        when(databaseAdapter.countShoes(color, size)).thenReturn(1);
        var stockUpdate = StockUpdate.builder().size(size).color(color).quantity(-1).build();

        // Act
        getShoeShopCore(apiVersion).updateStock(stockUpdate);

        // Assert
        verify(databaseAdapter, never()).saveShoe(any(), any());
        verify(databaseAdapter, times(1)).removeShoe(color, size);
    }

    @Test
    void updateStock_RemoveMultipleModels() throws NotSupportedException {
        // Arrange
        var color = ShoeFilter.Color.BLACK;
        var size = BigInteger.valueOf(42);
        when(databaseAdapter.countShoes(color, size)).thenReturn(2);
        var stockUpdate = StockUpdate.builder().size(size).color(color).quantity(-2).build();
        // Act
        getShoeShopCore(apiVersion).updateStock(stockUpdate);

        // Assert
        verify(databaseAdapter, never()).saveShoe(any(), any());
        verify(databaseAdapter, times(2)).removeShoe(color, size);
    }

    @Test
    void updateStock_NoQuantity() throws NotSupportedException {
        // Arrange
        var stockUpdate = StockUpdate.builder().size(BigInteger.valueOf(42)).color(ShoeFilter.Color.BLACK).quantity(0).build();

        // Act
        getShoeShopCore(apiVersion).updateStock(stockUpdate);

        // Assert
        verify(databaseAdapter, never()).saveShoe(any(), any());
        verify(databaseAdapter, never()).removeShoe(any(), any());
    }

}

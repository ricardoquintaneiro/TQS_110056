package stocks;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StocksPortfolioTest {

    @InjectMocks
    private StocksPortfolio stocksPortfolio;

    @Mock
    private IStockMarketService stockMarketService;

    @BeforeEach
    public void setUp() {
        stocksPortfolio = new StocksPortfolio(stockMarketService);
    }

    @Test
    public void totalValueTest() {
        when(stockMarketService.lookUpPrice("MSFT")).thenReturn(10.0);
        when(stockMarketService.lookUpPrice("AMZN")).thenReturn(20.0);
        stocksPortfolio.addStock(new Stock("MSFT", 2));
        stocksPortfolio.addStock(new Stock("AMZN", 1));
        assertTrue(stocksPortfolio.totalValue() == 40.0);
        verify(stockMarketService, times(1)).lookUpPrice("MSFT");
        verify(stockMarketService, times(1)).lookUpPrice("AMZN");
    }
}

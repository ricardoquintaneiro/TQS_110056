package stocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StocksPortfolioTest {

    @Mock
    private IStockMarketService stockMarketService;

    @InjectMocks
    private StocksPortfolio stocksPortfolio;

    @BeforeEach
    public void setUp() {
        stocksPortfolio = new StocksPortfolio(stockMarketService);
    }
}

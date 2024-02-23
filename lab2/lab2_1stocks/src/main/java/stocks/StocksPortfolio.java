package stocks;

import java.util.List;

public class StocksPortfolio {
    private IStockMarketService stockmarket;
    private List<Stock> stocks;

    public StocksPortfolio(IStockMarketService stockmarket) {
        this.stockmarket = stockmarket;
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }    

    public double totalValue() {
        double total = 0;
        for (Stock stock : stocks) {
            total += stockmarket.lookUpPrice(stock.getLabel()) * stock.getQuantity();
        }
        return total;
    }
}

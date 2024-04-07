package pt.ua.tqs110056.busticketbackend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class CustomTtlCacheManager extends ConcurrentMapCacheManager {

    private static final Logger logger = LoggerFactory.getLogger(CustomTtlCacheManager.class);

    public CustomTtlCacheManager(final String... cacheNames) {
        super(cacheNames);
    }

    @Override
    protected @NonNull Cache createConcurrentMapCache(final @NonNull String name) {
        return new ConcurrentMapCache(name) {
            @Override
            public ValueWrapper get(@NonNull Object key) {
                ValueWrapper valueWrapper = super.get(key);
                if (valueWrapper != null) {
                    logger.debug("Cache hit for key '{}' in '{}'", key, name);
                } else {
                    logger.debug("Cache miss for key '{}' in '{}'", key, name);
                }
                return valueWrapper;
            }

            @Override
            public void put(@NonNull Object key, @Nullable Object value) {
                super.put(key, value);
                logger.debug("Cache save for key '{}' in '{}'", key, name);
            }
        };
    }

    @Scheduled(cron = "0 0 16 * * MON-FRI", zone = "CET")
    public void clearCurrencyRatesAtSpecificTime() {
        // Clears currency rates cache at 16:00 CET (Central European Time) every working
        // day
        //
        // This is to ensure that the currency conversion rates are up-to-date per
        // Frankfurter API
        logger.info("Clearing currency rates cache at 16:00 CET every working day.");
        Cache currencyRatesCache = this.getCache("currencyRates");
        if (currencyRatesCache != null) {
            currencyRatesCache.clear();
            logger.debug("Currency rates cache cleared.");
        } else {
            logger.warn("Currency rates cache not found.");
        }
    }

}

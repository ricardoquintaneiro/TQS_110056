package pt.ua.tqs110056.busticketbackend.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableCaching
@EnableScheduling
public class CacheConfig {

    @Bean
    @Primary
    public CacheManager cacheManager() {
        return new CustomTtlCacheManager("currencyRates");
    }
    
}

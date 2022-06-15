package com.acme.mytrader.strategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import com.acme.mytrader.execution.ExecutionService;

import com.acme.mytrader.price.PriceListener;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TradingStrategyTest {

    @Mock
    ExecutionService tradeExecutionService;
    @Mock
    PriceListener priceListener;

    @SneakyThrows
    @Test
    public void testAutoBuyForSuccessfulBuy() {
        ArgumentCaptor<String> securityCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Double> priceCaptor = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Integer> volumeCaptor = ArgumentCaptor.forClass(Integer.class);
        TradingStrategy tradingStrategy = new TradingStrategy(tradeExecutionService);
        SecurityDTO input = new SecurityDTO("IBM", 50.00, 10);
        tradingStrategy.autoBuy(input);
        Mockito.verify(tradeExecutionService, times(1))
                .buy(securityCaptor.capture(), priceCaptor.capture(), volumeCaptor.capture());

    }

    @SneakyThrows
    @Test
    public void testAutoBuyForNotSuccessfulBuy() {
        TradingStrategy tradingStrategy = new TradingStrategy(tradeExecutionService);
        SecurityDTO input = new SecurityDTO("APPLE", 50.00, 10);
        tradingStrategy.autoBuy(input);
        verifyZeroInteractions(tradeExecutionService);
    }

}

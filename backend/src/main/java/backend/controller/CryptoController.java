package backend.controller;

import backend.data.InMemoryDataRepository;
import backend.data.DataRepository;
import backend.data.Crypto;
import backend.exception.InvalidArgumentException;
import backend.logger.ExceptionLogger;
import backend.model.Transaction;
import backend.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class CryptoController {

    private static final String NO_AVAILABLE_CRYPTO_SYMBOL = "No available cryptocurrency with this symbol!";

    private final UserService userService;
    private final DataRepository dataRepository;

    public CryptoController() {
        userService = new UserService();
        dataRepository = new InMemoryDataRepository();
        dataRepository.startScheduleAtFixedRate();
    }

    @GetMapping("/buy/{symbol}/{quantity}")
    public String buy(@PathVariable String symbol, @PathVariable double quantity) {
        try {
            if (this.prices().containsKey(symbol)) {
                return userService.buyCryptocurrency(symbol, quantity, this.prices().get(symbol));
            } else {
                return NO_AVAILABLE_CRYPTO_SYMBOL;
            }
        } catch (InvalidArgumentException e) {
            try {
                ExceptionLogger.log(e.getMessage(), Arrays.toString(e.getStackTrace()), LocalDateTime.now());
            } catch (IOException e1) {
                throw new UncheckedIOException(e1.getMessage(), e1);
            }
            return e.getMessage();
        }
    }

    @GetMapping("/sell/{symbol}/{quantity}")
    public String sell(@PathVariable String symbol, @PathVariable double quantity) {
        try {
            if (this.prices().containsKey(symbol)) {
                return userService.sellCryptocurrency(symbol, quantity, this.prices().get(symbol));
            } else {
                return NO_AVAILABLE_CRYPTO_SYMBOL;
            }
        } catch (InvalidArgumentException e) {
            try {
                ExceptionLogger.log(e.getMessage(), Arrays.toString(e.getStackTrace()), LocalDateTime.now());
            } catch (IOException e1) {
                throw new UncheckedIOException(e1.getMessage(), e1);
            }
            return e.getMessage();
        }
    }

    @GetMapping("/balance")
    public Double getBalanceOfUser() {
        return userService.getBalanceOfUser();
    }

    @GetMapping("/transactions")
    public List<Transaction> transactions() {
        return userService.getTransactionsOfUser();
    }

    @GetMapping("/reset")
    public String reset() {
        return userService.resetUserAccount();
    }

    @GetMapping("/cryptos")
    public List<Crypto> cryptos() {
        return dataRepository.getCacheData().getDataForDisplaying();
    }

    private Map<String, Double> prices() {
        return dataRepository.getCacheData().getPrices();
    }

}

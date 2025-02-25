package backend.service;

import backend.exception.InsufficientBalanceException;
import backend.exception.InvalidArgumentException;
import backend.exception.InvalidCurrentPriceException;
import backend.exception.InvalidQuantityException;
import backend.exception.NotEnoughCryptoQuantityException;
import backend.model.Transaction;
import backend.model.TransactionType;
import backend.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class UserService {

    private static final double STARTING_AMOUNT = 10_000.0;

    private static final String MESSAGE_FOR_SUCCESSFUL_RESET = "User account is reset successfully!";
    private static final String MESSAGE_FOR_SUCCESSFUL_BUY = "Buy command is successfully make!";
    private static final String MESSAGE_FOR_SUCCESSFUL_SELL = "Sell command is successfully make!";
    private static final String MESSAGE_FOR_INVALID_QUANTITY = "Quantity is invalid number!";
    private static final String MESSAGE_FOR_INVALID_CURRENT_PRICE = "Current price is invalid number!";
    private static final String MESSAGE_FOR_INSUFFICIENT_BALANCE = "Insufficient balance!";
    private static final String NOT_ENOUGH_QUANTITY = "You do not have enough quantity from this crypto!";

    private final User user;

    public UserService() {
        user = new User(STARTING_AMOUNT, new ArrayList<>(), new ConcurrentHashMap<>());
    }

    public String buyCryptocurrency(String cryptocurrency, double quantity, double currentPrice)
        throws InvalidArgumentException {
        validateQuantity(quantity);
        validateCurrentPrice(currentPrice);

        double balance = user.getBalance();
        double costForBuy = quantity * currentPrice;
        if (costForBuy > balance) {
            throw new InsufficientBalanceException(MESSAGE_FOR_INSUFFICIENT_BALANCE);
        }

        balance -= costForBuy;
        user.setBalance(balance);
        user.getTransactions().add(
            Transaction.of(TransactionType.BUY, cryptocurrency, LocalDateTime.now(), quantity, currentPrice));
        user.getCryptos().put(cryptocurrency, user.getCryptos().getOrDefault(cryptocurrency, 0.0) + quantity);

        return MESSAGE_FOR_SUCCESSFUL_BUY;
    }

    public String sellCryptocurrency(String cryptocurrency, double quantity, double currentPrice)
        throws InvalidArgumentException {
        validateQuantity(quantity);
        validateCurrentPrice(currentPrice);

        if (!user.getCryptos().containsKey(cryptocurrency)
            || user.getCryptos().get(cryptocurrency) < quantity) {
            throw new NotEnoughCryptoQuantityException(NOT_ENOUGH_QUANTITY);
        }

        double sellPrice = quantity * currentPrice;

        double balance = user.getBalance();
        balance += sellPrice;
        user.setBalance(balance);
        user.getTransactions().add(
            Transaction.of(TransactionType.SELL, cryptocurrency, LocalDateTime.now(), quantity, currentPrice));
        user.getCryptos().put(cryptocurrency, user.getCryptos().get(cryptocurrency) - quantity);

        return MESSAGE_FOR_SUCCESSFUL_SELL;
    }

    public String resetUserAccount() {
        user.setBalance(STARTING_AMOUNT);
        user.getTransactions().clear();

        return MESSAGE_FOR_SUCCESSFUL_RESET;
    }

    public double getBalanceOfUser() {
        return user.getBalance();
    }

    public List<Transaction> getTransactionsOfUser() {
        return user.getTransactions();
    }

    private void validateQuantity(double quantity) throws InvalidQuantityException {
        if (quantity <= 0) {
            throw new InvalidQuantityException(MESSAGE_FOR_INVALID_QUANTITY);
        }
    }

    private void validateCurrentPrice(double currentPrice) throws InvalidCurrentPriceException {
        if (currentPrice <= 0) {
            throw new InvalidCurrentPriceException(MESSAGE_FOR_INVALID_CURRENT_PRICE);
        }
    }

}

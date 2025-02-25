package backend.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class User {

    private double balance;
    private final Map<String, Double> cryptos;
    private final List<Transaction> transactions;

    public User(double balance, List<Transaction> transactions, Map<String, Double> cryptos) {
        this.balance = balance;
        if (transactions == null) {
            this.transactions = new ArrayList<>();
        } else {
            this.transactions = transactions;
        }
        if (cryptos == null) {
            this.cryptos = new ConcurrentHashMap<>();
        } else {
            this.cryptos = cryptos;
        }
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Map<String, Double> getCryptos() {
        return cryptos;
    }

}

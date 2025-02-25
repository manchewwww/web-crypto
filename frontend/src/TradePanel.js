import React, { useState, useEffect } from "react";
import axios from "axios";
import "./TradePanel.css";

const TradePanel = () => {
    const [cryptos, setCryptos] = useState([]);
    const [balance, setBalance] = useState(0);
    const [transactions, setTransactions] = useState([]);
    const [symbol, setSymbol] = useState("");
    const [quantity, setQuantity] = useState("");
    const [message, setMessage] = useState("");

    useEffect(() => {
        fetchCryptos();
        fetchBalance();
        fetchTransactions();
    }, []);

    useEffect(() => {
        const intervalId = setInterval(fetchCryptos, 30000);

        return () => clearInterval(intervalId);
    })

    const fetchCryptos = async () => {
        try {
            const response = await axios.get("http://localhost:8080/cryptos");
            setCryptos(response.data);
        } catch (error) {
            console.error("Error fetching cryptos:", error);
        }
    };

    const fetchBalance = async () => {
        try {
            const response = await axios.get("http://localhost:8080/balance");
            setBalance(response.data);
        } catch (error) {
            console.error("Error fetching balance:", error);
        }
    };

    const fetchTransactions = async () => {
        try {
            const response = await axios.get("http://localhost:8080/transactions");
            setTransactions(response.data);
        } catch (error) {
            console.error("Error fetching transactions:", error);
        }
    };

    const writeMessage = (responceMessage) => {
        setMessage(responceMessage);
    }

    const cleanTextInBoxes = () => {
        setSymbol("")
        setQuantity("");
    }

    const isNumber = (quantityInput) => {
        const num = Number(quantityInput);
        return !isNaN(num) && num > 0;
    }

    const handleBuy = async () => {
        try {
            if (isNumber(quantity)) {
                const response = await axios.get(`http://localhost:8080/buy/${symbol}/${quantity}`);
                writeMessage(response.data);
                cleanTextInBoxes();
                fetchBalance();
                fetchTransactions();
            }
            else {
                writeMessage("Invalid quantity type! Enter valid number!");
                cleanTextInBoxes();
            }
        } catch (error) {
            console.error("Error buying crypto:", error);
        }
    };

    const handleSell = async () => {
        try {
            if (isNumber(quantity)) {
                const response = await axios.get(`http://localhost:8080/sell/${symbol}/${quantity}`);
                writeMessage(response.data);
                cleanTextInBoxes();
                fetchBalance();
                fetchTransactions();
            }
            else {
                writeMessage("Invalid quantity type! Enter valid number!");
                cleanTextInBoxes();
            }
        } catch (error) {
            console.error("Error selling crypto:", error);
        }
    };

    const handleReset = async () => {
        try {
            const response = await axios.get("http://localhost:8080/reset");
            writeMessage(response.data);
            cleanTextInBoxes();
            fetchBalance();
            fetchTransactions();
        } catch (error) {
            console.error("Error resetting account:", error);
        }
    };

    return (
        <div className="trade-container">
            <div className="crypto-list">
                <h2>CryptoCurrencies</h2>
                <ul>
                    <li>
                        <span>Name (Symbol)</span>
                        <span>Price</span>
                    </li>
                    {cryptos.map((crypto, index) => (
                        <li key={index}>
                            <span>{crypto.name} ({crypto.symbol})</span>
                            <span>${crypto.currentPrice}</span>
                        </li>
                    ))}
                </ul>
            </div>

            <div className="trade-panel">
                <h3>Balance: ${balance}</h3>
                <h3>Transactions:</h3>
                <ol className="transaction-list">
                    {transactions.map((transaction, index) => (
                        <li key={index} className="transaction-item">
                            {transaction.type} - {transaction.cryptocurrency} - {transaction.date} -
                            {transaction.quantity} - {transaction.cryptocurrencyPrice}
                        </li>
                    ))}
                </ol>

                <div className="trade-inputs">
                    <input
                        type="text"
                        placeholder="Symbol"
                        value={symbol}
                        onChange={(e) => setSymbol(e.target.value.toUpperCase())}
                    />

                    <input
                        type="text"
                        placeholder="Quantity"
                        value={quantity}
                        onChange={(e) => setQuantity(e.target.value)}
                    />
                </div>

                <div className="trade-buttons">
                    <button className="buy-button" onClick={handleBuy}>
                        Buy
                    </button>
                    <button className="sell-button" onClick={handleSell}>
                        Sell
                    </button>
                    <button className="reset-button" onClick={handleReset}>
                        Reset
                    </button>
                </div>

                <div className="message-output">
                    <p>{message}</p>
                </div>
            </div>
        </div>
    );
};

export default TradePanel;

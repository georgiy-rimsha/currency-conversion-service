import React, { useEffect, useState } from "react";
import {
  CurrencyConversionService,
  ConvertResponse,
} from "../../services/CurrencyConversionService";
import "./CurrencyConverter.css";

const CurrencyConverter = () => {
  const [isLoading, setIsLoading] = useState(false);
  const [currencies, setCurrencies] = useState<string[]>([]);
  const [amount, setAmount] = useState<number>(100);
  const [fromCurrency, setFromCurrency] = useState<string>("");
  const [toCurrency, setToCurrency] = useState<string>("");
  const [latestConvertedAmount, setLatestConvertedAmount] = useState<
    number | null
  >(null);
  const [latestConversionRate, setLatestConversionRate] = useState<
    number | null
  >(null);

  const currencyService = new CurrencyConversionService();

  useEffect(() => {
    const fetchCurrencies = async () => {
      const availableCurrencies =
        await currencyService.getAvailableCurrencies();
      setCurrencies(availableCurrencies);
      setFromCurrency(availableCurrencies[0]);
      setToCurrency(availableCurrencies[1]);
    };

    fetchCurrencies();
  }, []);

  useEffect(() => {
    if (latestConversionRate !== null) {
      const newConvertedAmount = amount * latestConversionRate;
      setLatestConvertedAmount(newConvertedAmount);
    } else {
      setLatestConvertedAmount(null);
    }
  }, [amount]);

  useEffect(() => {
    if (latestConvertedAmount !== null && latestConversionRate !== null) {
      handleConversion();
    }
  }, [fromCurrency, toCurrency]);

  const handleConversion = async () => {
    if (fromCurrency === "" || toCurrency === "") return;
    setIsLoading(true);
    try {
      const response: ConvertResponse = await currencyService.convertCurrency(
        fromCurrency,
        toCurrency,
        amount
      );
      setLatestConvertedAmount(response.convertedAmount);
      setLatestConversionRate(response.conversionRate);
    } catch (error) {
      console.error("Error during currency conversion:", error);
    } finally {
      setIsLoading(false);
    }
  };

  const formatCurrency = (value: number, currency: string) => {
    return new Intl.NumberFormat("en-US", {
      style: "currency",
      currency: currency,
    }).format(value);
  };

  return (
    <div className="currency-converter">
      <h1>Currency Converter</h1>
      <div>
        <label>Amount: </label>
        <input
          type="number"
          value={amount}
          onChange={(e) => setAmount(Number(e.target.value))}
          style={{ boxSizing: "border-box" }}
          min="0"
        />
      </div>

      <div>
        <label>From: </label>
        <select
          value={fromCurrency}
          onChange={(e) => setFromCurrency(e.target.value)}
        >
          <option value="-" disabled>
            Select a currency
          </option>
          {currencies.map((currency) => (
            <option key={currency} value={currency}>
              {currency}
            </option>
          ))}
        </select>
      </div>

      <div>
        <label>To: </label>
        <select
          value={toCurrency}
          onChange={(e) => setToCurrency(e.target.value)}
        >
          <option value="-" disabled>
            Select a currency
          </option>
          {currencies.map((currency) => (
            <option key={currency} value={currency}>
              {currency}
            </option>
          ))}
        </select>
      </div>
      <button onClick={handleConversion}>
        {isLoading ? "Loading..." : "Convert"}
      </button>
      {latestConvertedAmount !== null && latestConversionRate !== null && (
        <div className="result">
          <h3
            style={{
              width: "100%",
              overflow: "hidden",
              textOverflow: "ellipsis",
              whiteSpace: "nowrap",
            }}
          >
            {formatCurrency(amount, fromCurrency)} is{" "}
            {formatCurrency(latestConvertedAmount, toCurrency)}
          </h3>
          <p>
            1 {fromCurrency} equals{" "}
            {formatCurrency(latestConversionRate, toCurrency)}
          </p>
        </div>
      )}
    </div>
  );
};

export default CurrencyConverter;

import { useEffect } from "react";
import "./App.css";
import CurrencyConverter from "./components/currency_converter/CurrencyConverter";
import axios from "axios";
import { post as doPost } from "./services/csrfService";

function App() {
  useEffect(() => {
    doPost();
  });

  return (
    <div style={{ width: "300px", margin: "20px auto" }}>
      <CurrencyConverter />
    </div>
  );
}

export default App;

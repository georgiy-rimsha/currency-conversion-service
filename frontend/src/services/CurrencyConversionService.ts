import axios from "axios";

export class CurrencyConversionService {
  private readonly baseUrl: string = `${process.env.REACT_APP_API_BASE_URL}`;

  public async getAvailableCurrencies(): Promise<string[]> {
    const response = await axios.get(`${this.baseUrl}/api/v1/currencies`);
    return response.data;
  }

  public async convertCurrency(
    sourceCurrency: string,
    targetCurrency: string,
    amount: number
  ): Promise<ConvertResponse> {
    const response = await axios.get<ConvertResponse>(
      `${this.baseUrl}/api/v1/conversions/${sourceCurrency}/${targetCurrency}`,
      { params: { amount } }
    );
    return response.data;
  }
}

export interface ConvertResponse {
  convertedAmount: number;
  conversionRate: number;
}

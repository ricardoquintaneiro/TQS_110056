export const CURRENCIES_URL = "http://localhost:8080/api/currency"

export const convertCurrency = async (amount: number, from: string, to: string) => {
    const url = `${CURRENCIES_URL}/convert?amount=${amount}&from=${from}&to=${to}`

    const response = await fetch(url)

    if (!response.ok) {
        const errorMessage = await response.text()
        throw new Error(errorMessage ? errorMessage : response.statusText)
    }

    const data = await response.json()
    return data
}

export const fetchCurrencyConversionRate = async (from: string, to: string) => {
    const url = `${CURRENCIES_URL}/rate?from=${from}&to=${to}`

    const response = await fetch(url)

    if (!response.ok) {
        const errorMessage = await response.text()
        throw new Error(errorMessage ? errorMessage : response.statusText)
    }

    const data = await response.json()
    return data
}
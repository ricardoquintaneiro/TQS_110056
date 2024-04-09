export const CREDIT_CARDS_URL = "http://localhost:8080/api/credit-cards"

export type CreditCard = {
    type: CreditCardType
    number: string
    cvv: string
    expirationDate: string
}

export enum CreditCardType {
    VISA = "VISA",
    MASTERCARD = "MASTERCARD",
    AMERICAN_EXPRESS = "AMERICAN_EXPRESS",
}

export const fetchCreditCard = async (id: number) => {
  const response = await fetch(`${CREDIT_CARDS_URL}/${id}`)

  if (!response.ok) {
    const errorMessage = await response.text()
    throw new Error(errorMessage ? errorMessage : response.statusText)
  }

  const data = await response.json()
  return data
}

export const saveCreditCard = async (creditCard: CreditCard) => {
    const isValidDate = /^\d{4}-\d{2}-\d{2}$/.test(creditCard.expirationDate)    

    if (!isValidDate) {
        throw new Error("Invalid date format. Date must be in YYYY-MM-DD format.")
    }

    const response = await fetch(CREDIT_CARDS_URL, {
        method: "POST",
        headers: {
        "Content-Type": "application/json",
        },
        body: JSON.stringify(creditCard),
    })
    
    if (!response.ok) {
        const errorMessage = await response.text()
        throw new Error(errorMessage ? errorMessage : response.statusText)
    }
    
    const savedCreditCard = await response.json()
    return savedCreditCard
}

export const deleteCreditCard = async (id: number) => {
    const url = `${CREDIT_CARDS_URL}/${id}`
    const response = await fetch(url, {
        method: "DELETE",
    })
    
    if (!response.ok) {
        const errorMessage = await response.text()
        throw new Error(errorMessage ? errorMessage : response.statusText)
    }
    
    const message = await response.text()
    return message
}

export const validateCreditCardNumber = async (number: string) => {
    const url = `${CREDIT_CARDS_URL}/number/${number}/validate`

    const response = await fetch(url)

    if (!response.ok) {
        const errorMessage = await response.text()
        throw new Error(errorMessage ? errorMessage : response.statusText)
    }

    const data = await response.text()
    return data
}

export const validateCreditCardCvv = async (cvv: string) => {
    const url = `${CREDIT_CARDS_URL}/cvv/${cvv}/validate`

    const response = await fetch(url)

    if (!response.ok) {
        const errorMessage = await response.text()
        throw new Error(errorMessage ? errorMessage : response.statusText)
    }

    const data = await response.text()
    return data
}
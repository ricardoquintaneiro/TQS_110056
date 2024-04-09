export const PASSENGERS_URL = "http://localhost:8080/api/passengers"

export type Passenger = {
    name: string
    email: string
    phoneNumber: string
}

export const fetchPassenger = async (id: number) => {
    const url = `${PASSENGERS_URL}/${id}`

    const response = await fetch(url)

    if (!response.ok) {
        const errorMessage = await response.text()
        throw new Error(errorMessage ? errorMessage : response.statusText)
    }

    const data = await response.json()
    return data
}

export const savePassenger = async (passenger: Passenger) => {
    const response = await fetch(PASSENGERS_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(passenger),
    })

    if (!response.ok) {
        const errorMessage = await response.text()
        throw new Error(errorMessage ? errorMessage : response.statusText)
    }

    const savedPassenger = await response.json()
    return savedPassenger
}

export const deletePassenger = async (id: number) => {
    const url = `${PASSENGERS_URL}/${id}`

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
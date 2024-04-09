export const BUSES_URL = "http://localhost:8080/api/buses"

export type Bus = {
  plate: string
  model: string
  seats: BusSeat[]
  availableSeats: boolean[]
}

export type BusSeat = {
  type: BusSeatType
  number: string
}

export enum BusSeatType {
  REGULAR = "REGULAR",
  PREMIUM = "PREMIUM",
  PRIORITY = "PRIORITY",
}

export const fetchBus = async (id: number) => {
  const response = await fetch(`${BUSES_URL}/${id}`)
  if (!response.ok) {
    const errorMessage = await response.text()
    throw new Error(errorMessage ? errorMessage : response.statusText)
  }
  const data = await response.json()
  return data
}

export const fetchAllBuses = async () => {
  const response = await fetch(BUSES_URL)
  const data = await response.json()
  return data
}

export const getBusSeats = async (id: number, type?: string) => {
  let url = `${BUSES_URL}/${id}/seats`

  if (type) {
    url += `?type=${type}`
  }

  const response = await fetch(url)
  if (!response.ok) {
    const errorMessage = await response.text()
    throw new Error(errorMessage ? errorMessage : response.statusText)
  }
  const data = await response.json()
  return data
}

export const updateSeatAvailability = async (
  id: number,
  seatNumber: number,
  reserve: boolean
) => {
  const url = `${BUSES_URL}/${id}/seats/${seatNumber}?reserve=${reserve}`

  const response = await fetch(url, { method: "PUT" })

  if (!response.ok) {
    const errorMessage = await response.text()
    throw new Error(errorMessage ? errorMessage : response.statusText)
  }

  const message = await response.text()
  return message
}

export const makeAllSeatsAvailable = async (id: number) => {
  const url = `${BUSES_URL}/${id}/seats/all/available`

  const response = await fetch(url, { method: "PUT" })

  if (!response.ok) {
    const errorMessage = await response.text()
    throw new Error(errorMessage ? errorMessage : response.statusText)
  }

  const message = await response.text()
  return message
}

export const isSeatAvailable = async (id: number, seatNumber: string) => {
  const url = `${BUSES_URL}/${id}/seats/${seatNumber}/available`

  const response = await fetch(url)

  if (!response.ok) {
    const errorMessage = await response.text()
    throw new Error(errorMessage ? errorMessage : response.statusText)
  }

  const data = await response.json()
  return data
}

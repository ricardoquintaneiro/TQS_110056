export const TRIPS_URL = "http://localhost:8080/api/trips"

export type Trip = {
    origin: City
    destination: City
    bus: Bus
    departureTime: string
    arrivalTime: string
    ticketPriceInEuro: number // equivalent to BigDecimal in Java
}

export const findTrips = async (from?: number, to?: number, date?: string) => {
  let queryParams = ""

  if (date) {
    const isValidDate = /^\d{4}-\d{2}-\d{2}$/.test(date)
    if (!isValidDate) {
      throw new Error("Invalid date format. Date must be in YYYY-MM-DD format.")
    }
    queryParams = `&date=${date}`
  }

  if (from) {
    queryParams += `&from=${from}`
  }
  if (to) {
    queryParams += `&to=${to}`
  }

  const response = await fetch(
    `${TRIPS_URL}?${queryParams}`
  )
  const data = await response.json()
  return data
}

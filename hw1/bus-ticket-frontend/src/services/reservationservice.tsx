import { BusSeat } from "./busservice"
import { CreditCard } from "./creditcardservice"
import { Passenger } from "./passengerservice"
import { Trip } from "./tripservice"

export const RESERVATIONS_URL = "http://localhost:8080/api/passengers"

export type Reservation = {
  passenger: Passenger
  trip: Trip
  seat: BusSeat
  creditCard: CreditCard
  status: ReservationStatus
  reservationTime: string // equivalent to LocalDateTime in Java
}

export enum ReservationStatus {
  PENDING = "PENDING",
  CONFIRMED = "CONFIRMED",
  CANCELLED = "CANCELLED",
}

export const saveReservation = async (reservation: Reservation) => {
  const response = await fetch(RESERVATIONS_URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(reservation),
  })

  if (!response.ok) {
    const errorMessage = await response.text()
    throw new Error(errorMessage ? errorMessage : response.statusText)
  }

  const savedReservation = await response.json()
  return savedReservation
}

export const fetchReservation = async (id: number) => {
    const url = `${RESERVATIONS_URL}/${id}`
    
    const response = await fetch(url)
    
    if (!response.ok) {
        const errorMessage = await response.text()
        throw new Error(errorMessage ? errorMessage : response.statusText)
    }
    
    const data = await response.json()
    return data
}

export const deleteReservation = async (id: number) => {
    const url = `${RESERVATIONS_URL}/${id}`
    
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

export const updateReservationStatus = async (id: number, status: string) => {
    const url = `${RESERVATIONS_URL}/${id}/?action=${status}`
    
    const response = await fetch(url, { method: "PUT" })
    
    if (!response.ok) {
        const errorMessage = await response.text()
        throw new Error(errorMessage ? errorMessage : response.statusText)
    }
    
    const message = await response.text()
    return message
}

export const fetchAllReservations = async () => {
    const response = await fetch(RESERVATIONS_URL)
    
    if (!response.ok) {
        const errorMessage = await response.text()
        throw new Error(errorMessage ? errorMessage : response.statusText)
    }
    
    const data = await response.json()
    return data
}

export const fetchReservationsByPassenger = async (passengerId: number) => {
    const url = `${RESERVATIONS_URL}/passenger/${passengerId}`
    
    const response = await fetch(url)
    
    if (!response.ok) {
        const errorMessage = await response.text()
        throw new Error(errorMessage ? errorMessage : response.statusText)
    }
    
    const data = await response.json()
    return data
}

export const fetchReservationsByTrip = async (tripId: number) => {
    const url = `${RESERVATIONS_URL}/trip/${tripId}`
    
    const response = await fetch(url)
    
    if (!response.ok) {
        const errorMessage = await response.text()
        throw new Error(errorMessage ? errorMessage : response.statusText)
    }
    
    const data = await response.json()
    return data
}
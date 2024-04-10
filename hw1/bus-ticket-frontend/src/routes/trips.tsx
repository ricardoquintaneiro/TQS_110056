import { useEffect, useState } from "react"
import { useLocation } from "react-router-dom"
import { TripsTable } from "../components/tripstable"
import { Trip, findTrips } from "../services/tripservice"

export const TripsPage = () => {
  const [trips, setTrips] = useState<Trip[]>([])

  const { state } = useLocation()

  const selectedDepartureCity = state.selectedDepartureCity
  const selectedDestinationCity = state.selectedDestinationCity
  const selectedDate = state.selectedDate
  const selectedCurrency = state.selectedCurrency

  useEffect(() => {
    const fetchTrips = async () => {
      return await findTrips(selectedDepartureCity.id, selectedDestinationCity.id, selectedDate? selectedDate.toISOString().split("T")[0]: undefined)
    }

    fetchTrips().then((data) => setTrips(data))
  }, [selectedDepartureCity, selectedDestinationCity, selectedDate])

  return (
    <div className="max-w-7xl mx-auto mt-10 p-6 bg-white rounded-lg shadow-xl">
      <h1 className="text-xl font-semibold mb-4">Trips from {selectedDepartureCity.name} to {selectedDestinationCity.name} on {selectedDate ? selectedDate.toLocaleDateString() : "all dates"}</h1>
      <TripsTable trips={trips} currency={selectedCurrency} />
    </div>
  )
}

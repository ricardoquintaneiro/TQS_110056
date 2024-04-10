import { Link, useLocation } from "react-router-dom"
import { TripsTable } from "../components/tripstable"
import { Trip, findTrips } from "../services/tripservice"
import { useEffect, useState } from "react"

export const TripsPage = () => {
  const [trips, setTrips] = useState<Trip[]>([])

  const { state } = useLocation()

  const selectedDepartureCity = state.selectedDepartureCity
  const selectedDestinationCity = state.selectedDestinationCity
  const selectedDate = state.selectedDate

  useEffect(() => {
    const fetchTrips = async () => {
      return await findTrips(selectedDepartureCity.id, selectedDestinationCity.id, selectedDate)
    }

    fetchTrips().then((data) => setTrips(data))
  }, [selectedDepartureCity, selectedDestinationCity, selectedDate])

  return (
    <div className="max-w-md mx-auto mt-10 p-6 bg-white rounded-lg shadow-xl">
      <h1 className="text-xl font-semibold mb-4">Trips from {selectedDepartureCity} to {selectedDestinationCity} on {selectedDate}</h1>
      <TripsTable trips={trips} />
      <Link
        to="/purchase"
        state={{
          selectedDepartureCity: selectedDepartureCity,
          selectedDestinationCity: selectedDestinationCity,
          selectedDate: selectedDate,
        }}
        className="block text-center py-2 px-4 mt-5 bg-blue-500 text-white rounded hover:bg-blue-600"
      >
        Find trips
      </Link>
    </div>
  )
}

import { useEffect, useState } from "react"
import { convertCurrency } from "../services/currencyconversionservice"
import { Trip } from "../services/tripservice"
import { Link, useLocation } from "react-router-dom"

export const TripsTable = ({ trips, currency }: { trips: Trip[], currency: string }) => {

  const { state } = useLocation()

  const selectedDepartureCity = state.selectedDepartureCity
  const selectedDestinationCity = state.selectedDestinationCity
  const selectedDate = state.selectedDate

  const [renderedTrips, setRenderedTrips] = useState<Trip[]>([])

  const convertToCurrency = async (price: number) => {
    if (currency === "EUR") {
      return price
    }
    return await convertCurrency(price, "EUR", currency)
  }

  const renderTrips = async () => {
    return Promise.all(trips.map(async (trip) => ({
      ...trip,
      ticketPrice: await convertToCurrency(trip.ticketPriceInEuro),})))
  }

  useEffect(() => {
    renderTrips().then(setRenderedTrips)
  }, [trips, currency])

  return (
    <div className="relative overflow-x-auto shadow-md sm:rounded-lg">
      <table className="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
        <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
          <tr>
            <th scope="col" className="px-6 py-3">
              Origin
            </th>
            <th scope="col" className="px-6 py-3">
              Destination
            </th>
            <th scope="col" className="px-6 py-3">
              Bus
            </th>
            <th scope="col" className="px-6 py-3">
              Departure Time
            </th>
            <th scope="col" className="px-6 py-3">
              Arrival Time
            </th>
            <th scope="col" className="px-6 py-3">
              Ticket Price ({currency})
            </th>
            <th scope="col" className="px-6 py-3"></th>
          </tr>
        </thead>
        <tbody>
          {renderedTrips.map((trip, index) => (
            <tr key={index} className={index % 2 === 0 ? 'bg-white' : 'bg-gray-50'}>
              <td className="px-6 py-4">{trip.origin.name}</td>
              <td className="px-6 py-4">{trip.destination.name}</td>
              <td className="px-6 py-4">{trip.bus.plate}</td>
              <td className="px-6 py-4">{trip.departureTime}</td>
              <td className="px-6 py-4">{trip.arrivalTime}</td>
              <td className="px-6 py-4">{trip.ticketPrice}</td>
              <td className="px-6 py-4">
                <Link to="/purchase"
                  state={{
                    selectedDepartureCity: selectedDepartureCity,
                    selectedDestinationCity: selectedDestinationCity,
                    selectedDate: selectedDate,
                    selectedTrip: {
                      id: trip.id,
                      origin: trip.origin,
                      destination: trip.destination,
                      bus: trip.bus,
                      departureTime: trip.departureTime,
                      arrivalTime: trip.arrivalTime,
                      ticketPrice: trip.ticketPriceInEuro,
                    },
                    selectedCurrency: currency,
                    selectedTicketPrice: trip.ticketPrice,
                  }}
                  className="text-blue-500 font-medium hover:underline">
                  Purchase
                </Link>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}

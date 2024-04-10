import { Trip } from "../services/tripservice"

export const TripsTable = ({ trips }: { trips: Trip[] }) => {
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
              Ticket Price (Euro)
            </th>
          </tr>
        </thead>
        <tbody>
          {trips.map((trip, index) => (
            <tr key={index} className={index % 2 === 0 ? 'bg-white' : 'bg-gray-50'}>
              <td className="px-6 py-4">{trip.origin.name}</td>
              <td className="px-6 py-4">{trip.destination.name}</td>
              <td className="px-6 py-4">{trip.bus.name}</td>
              <td className="px-6 py-4">{trip.departureTime}</td>
              <td className="px-6 py-4">{trip.arrivalTime}</td>
              <td className="px-6 py-4">{trip.ticketPriceInEuro}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}

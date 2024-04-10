import { Form, useLocation, useNavigate } from "react-router-dom"
import { Reservation, ReservationStatus, saveReservation } from "../services/reservationservice"
import { Passenger } from "../services/passengerservice"
import { CreditCard } from "../services/creditcardservice"
import { BusSeat } from "../services/busservice"
import { useEffect, useState } from "react"

export const PurchaseForm = () => {
  const navigate = useNavigate()
  const [busSeats, setBusSeats] = useState<BusSeat[]>([])

  const { state } = useLocation()

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault()
    const formData = new FormData(event.currentTarget)

    const busSeatId = parseInt(formData.get("bus_seat").split(" - ")[0])
    const busSeatType = formData.get("bus_seat").split(" - ")[1]
    const busSeatNumber = formData.get("bus_seat").split(" - ")[2]

    const name: string = formData.get("floating_first_name") + " " + formData.get("floating_last_name")
    const email = formData.get("floating_email")
    const phoneNumber = formData.get("floating_phone")

    const creditCardType = formData.get("credit_card_type")
    const creditCardNumber = formData.get("credit_card_number")
    const cvv = formData.get("cvv")
    const expirationDate = formData.get("expiration_date")
    const expirationDateValid = new Date(expirationDate.split("/")[1], expirationDate.split("/")[0], 1).toISOString()

    const busSeat: BusSeat = {
      id: busSeatId,
      type: busSeatType,
      number: busSeatNumber,
    }

    const creditCard: CreditCard = {
      creditCardType: creditCardType,
      number: creditCardNumber,
      cvv: cvv,
      expirationDate: expirationDateValid
    }

    const passenger: Passenger = {
      name: name,
      email: email,
      phoneNumber: phoneNumber
    }

    const reservation: Reservation = {
      passenger: passenger,
      trip: state.selectedTrip,
      busSeat: busSeat,
      creditCard: creditCard,
      status: ReservationStatus.PENDING,
      reservationTime: new Date().toISOString()
    }

    console.log(reservation)

    const response = await saveReservation(reservation)

    if (response) {
      navigate("/confirmation", { state: { reservation: response } })
    } else {
      alert("Failed to save reservation")
    }
  }

  useEffect(() => {
    if (state.selectedTrip.bus.id) {
      setBusSeats(state.selectedTrip.bus.seats)
    }
  }, [])

  return (
    <Form method="post" className="max-w-md mx-auto" onSubmit={handleSubmit}>
      <div className="relative z-0 w-full mb-5 group">
        <select
          name="bus_seat"
          id="bus_seat"
          className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
          required
        >
          <option value="">Select Bus Seat</option>
          {busSeats.map((busSeat, index) => (
            <option key={index} value={`${busSeat.id} - ${busSeat.type} - ${busSeat.number}`}>
              {busSeat.type} - {busSeat.number}
            </option>
          ))}
        </select>
        <label
          htmlFor="bus_seat"
          className="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
        >
          Credit Card Type
        </label>
      </div>
      <div className="grid md:grid-cols-2 md:gap-6">
        <div className="relative z-0 w-full mb-5 group">
          <input
            type="text"
            name="floating_first_name"
            id="floating_first_name"
            className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
            placeholder=" "
            required
          />
          <label
            htmlFor="floating_first_name"
            className="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
          >
            First name
          </label>
        </div>
        <div className="relative z-0 w-full mb-5 group">
          <input
            type="text"
            name="floating_last_name"
            id="floating_last_name"
            className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
            placeholder=" "
            required
          />
          <label
            htmlFor="floating_last_name"
            className="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
          >
            Last name
          </label>
        </div>
      </div>
      <div className="relative z-0 w-full mb-5 group">
        <input
          type="email"
          name="floating_email"
          id="floating_email"
          className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
          placeholder=" "
          required
        />
        <label
          htmlFor="floating_email"
          className="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 rtl:peer-focus:left-auto peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
        >
          Email address
        </label>
      </div>
        <div className="relative z-0 w-full mb-5 group">
          <input
            type="tel"
            name="floating_phone"
            id="floating_phone"
            className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
            placeholder=" "
            required
          />
          <label
            htmlFor="floating_phone"
            className="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
          >
            Phone number (+351123456789)
          </label>
      </div>
      {/* Credit card type */}
      <div className="relative z-0 w-full mb-5 group">
        <select
          name="credit_card_type"
          id="credit_card_type"
          className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
          required
        >
          <option value="">Select Card Type</option>
          <option value="VISA">VISA</option>
          <option value="MASTERCARD">MASTERCARD</option>
          <option value="AMERICAN_EXPRESS">AMERICAN EXPRESS</option>
        </select>
        <label
          htmlFor="credit_card_type"
          className="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
        >
          Credit Card Type
        </label>
      </div>
      {/* Credit card number */}
      <div className="relative z-0 w-full mb-5 group">
        <input
          type="text"
          name="credit_card_number"
          id="credit_card_number"
          className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
          placeholder=" "
          required
        />
        <label
          htmlFor="credit_card_number"
          className="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
        >
          Credit Card Number
        </label>
      </div>

      {/* CVV */}
      <div className="relative z-0 w-full mb-5 group">
        <input
          type="text"
          name="cvv"
          id="cvv"
          className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
          placeholder=" "
          required
        />
        <label
          htmlFor="cvv"
          className="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
        >
          CVV
        </label>
      </div>

      {/* Expiration Date */}
      <div className="relative z-0 w-full mb-5 group">
        <input
          type="text"
          name="expiration_date"
          id="expiration_date"
          className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
          placeholder=" "
          required
        />
        <label
          htmlFor="expiration_date"
          className="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
        >
          Expiration Month (MM/YYYY)
        </label>
      </div>
      <button
        type="submit"
        className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
      >
        Submit
      </button>
    </Form>
  )
}

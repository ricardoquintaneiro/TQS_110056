import { Link, useLoaderData } from "react-router-dom"
import { CitySelectMenu } from "../components/cityselectmenu"
import { CalendarPicker } from "../components/calendarpicker"
import { useState } from "react"
import { City, fetchAllCities } from "../services/cityservice"
import { CurrencySelectMenu } from "../components/currencyselectmenu"

export const citiesLoader = async () => {
  const cities: City[] = await fetchAllCities()
  return { cities }
}

export const currencies = ["EUR", "USD", "GBP", "JPY", "CNY"]

export const HomePage = () => {
  const [selectedDepartureCity, setSelectedDepartureCity] = useState<City>()
  const [selectedDestinationCity, setSelectedDestinationCity] = useState<City>()
  const [selectedCurrency, setSelectedCurrency] = useState<string>("EUR")
  const [selectedDate, setSelectedDate] = useState<Date | null>(null)

  const { cities } = useLoaderData() as { cities: City[] }

  const handleDateSelect = (date: Date | null) => {
    setSelectedDate(date)
  }

  const handleDepartureCitySelect = (city: City) => {
    setSelectedDepartureCity(city)
  }

  const handleDestinationCitySelect = (city: City) => {
    setSelectedDestinationCity(city)
  }

  const handleCurrencySelect = (currency: string) => {
    setSelectedCurrency(currency)
  }

  return (
    <div className="max-w-md mx-auto mt-10 p-6 bg-white rounded-lg shadow-xl">
      <h1 className="text-xl font-semibold mb-4">
        Choose your departure city:
      </h1>
      <CitySelectMenu cities={cities} onSelect={handleDepartureCitySelect} />
      <h1 className="text-xl pt-6 font-semibold mb-4">
        Choose your destination city:
      </h1>
      <CitySelectMenu cities={cities} onSelect={handleDestinationCitySelect} />
      <h1 className="text-xl pt-6 font-semibold mb-4">
        Choose your destination city:
      </h1>
      <CurrencySelectMenu currencies={currencies} onSelect={handleCurrencySelect} />
      <h1 className="text-xl pt-6 font-semibold mb-4">Choose your date:</h1>
      <CalendarPicker onSelect={handleDateSelect} />
      <Link
        to="/trips"
        state={{
          selectedDepartureCity: selectedDepartureCity,
          selectedDestinationCity: selectedDestinationCity,
          selectedDate: selectedDate,
          selectedCurrency: selectedCurrency,
        }}
        className="block text-center py-2 px-4 mt-5 bg-blue-500 text-white rounded hover:bg-blue-600"
      >
        Find trips
      </Link>
    </div>
  )
}

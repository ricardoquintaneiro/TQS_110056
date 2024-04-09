import { Link } from "react-router-dom"
import { SelectMenu } from "../components/selectmenu"
import { DatePicker } from "../components/datepicker"

export const HomePage = () => {
  return (
    <div className="max-w-md mx-auto mt-10 p-6 bg-white rounded-lg shadow-xl">
      <h1 className="text-xl font-semibold mb-4">Choose your departure city:</h1>
      <SelectMenu />
      <h1 className="text-xl pt-4 font-semibold mb-4">Choose your destination city:</h1>
      <SelectMenu />
      <DatePicker />
      <Link to="/trips" className="block text-center py-2 px-4 mt-5 bg-blue-500 text-white rounded hover:bg-blue-600">
        Trips
      </Link>
    </div>

  )
}

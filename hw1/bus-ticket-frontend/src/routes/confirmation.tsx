import { useLocation } from "react-router-dom"
import { ConfirmationObject } from "../components/confirmationobject"

export const ConfirmationPage = () => {
  
  const { state } = useLocation()
  const object = state ? state.reservation : null

  return (
    <div className="max-w-7xl mx-auto mt-10 p-6 bg-white rounded-lg shadow-xl h-dvh">
      <h1 className="text-xl font-semibold mb-4">Confirmation</h1>
      <ConfirmationObject object={object}/>
    </div>
  )
}

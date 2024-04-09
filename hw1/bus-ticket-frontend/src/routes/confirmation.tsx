import { ConfirmationObject } from "../components/confirmationobject"
import { ConfirmationTable } from "../components/confirmationtable"

export const ConfirmationPage = () => {
  return (
    <>
      <div>
        <h1>Confirmation</h1>
      </div>
      <ConfirmationTable />
      <ConfirmationObject />
    </>
  )
}

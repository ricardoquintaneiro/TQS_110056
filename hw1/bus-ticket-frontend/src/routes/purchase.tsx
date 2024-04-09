import { Link } from "react-router-dom"
import { PurchaseForm } from "../components/purchaseform"

export const PurchasePage = () => {
  return (
    <>
      <div>
        <h1>Purchase</h1>
      </div>
      <div>
        <PurchaseForm />
        <Link to={"/trips"}>Trips</Link>
      </div>
    </>
  )
}

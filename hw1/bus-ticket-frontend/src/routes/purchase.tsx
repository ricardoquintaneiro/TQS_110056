import { Link } from "react-router-dom"

export const PurchasePage = () => {
  return (
    <>
      <div>
        <h1>Purchase</h1>
      </div>
      <div>
        <Link to={"/trips"}>Trips</Link>
      </div>
    </>
  )
}

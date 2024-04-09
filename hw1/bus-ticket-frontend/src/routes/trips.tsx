import { Link } from "react-router-dom"
import { TripsTable } from "../components/tripstable"

export const TripsPage = () => {
  return (
    <>
      <div>
        <h1>Trips</h1>
      </div>
      <div>
        <TripsTable />
        <Link to={"/purchase"}>Purchase</Link>
      </div>
    </>
  )
}

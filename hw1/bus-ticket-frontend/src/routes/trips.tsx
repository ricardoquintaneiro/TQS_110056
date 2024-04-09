import { Link } from "react-router-dom"

export const TripsPage = () => {
  return (
    <>
      <div>
        <h1>Trips</h1>
      </div>
      <div>
        <Link to={"/purchase"}>Purchase</Link>
      </div>
    </>
  )
}

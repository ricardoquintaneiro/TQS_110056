import { Link } from "react-router-dom"

export const HomePage = () => {
  return (
    <>
      <div>
        <Link to={"/trips"}>Trips</Link>
      </div>
    </>
  )
}

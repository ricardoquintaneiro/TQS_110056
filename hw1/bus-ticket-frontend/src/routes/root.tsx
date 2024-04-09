import { Outlet } from "react-router-dom"
import { Navbar } from "../components/navbar"

export const Root = () => {
  return (
    <>
      <Navbar />
      <div id="detail">
        <Outlet />
      </div>
    </>
  )
}

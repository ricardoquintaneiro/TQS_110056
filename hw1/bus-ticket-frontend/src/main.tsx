import React from "react"
import ReactDOM from "react-dom/client"
import { createBrowserRouter, RouterProvider } from "react-router-dom"
import "./index.css"

import { HomePage, citiesLoader } from "./routes/home"
import { TripsPage } from "./routes/trips"
import { PurchasePage } from "./routes/purchase"
import { ConfirmationPage } from "./routes/confirmation"
import { Root } from "./routes/root"
import { ReservationsPage } from "./routes/reservations"

const router = createBrowserRouter([
  {
    path: "/",
    element: <Root />,
    children: [
      { path: "/", element: <HomePage />, loader: citiesLoader },
      { path: "/trips", element: <TripsPage /> },
      { path: "/purchase", element: <PurchasePage /> },
      { path: "/confirmation", element: <ConfirmationPage /> },
      { path: "/reservations", element: <ReservationsPage /> },
    ],
  },
])

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
)

import React from "react"
import ReactDOM from "react-dom/client"
import { createBrowserRouter, RouterProvider } from "react-router-dom"
import "./index.css"

import { HomePage } from "./routes/home"
import { TripsPage } from "./routes/trips"
import { PurchasePage } from "./routes/purchase"
import { ConfirmationPage } from "./routes/confirmation"
import { Root } from "./routes/root"

const router = createBrowserRouter([
  {
    path: "/",
    element: <Root />,
    children: [
      { path: "/", element: <HomePage /> },
      { path: "/trips", element: <TripsPage /> },
      { path: "/purchase", element: <PurchasePage /> },
      { path: "/confirmation", element: <ConfirmationPage /> },
    ],
  },
])

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
)

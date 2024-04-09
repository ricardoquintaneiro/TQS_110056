export const CITIES_URL = "http://localhost:8080/api/cities"

export type City = {
    name: string
    country: string
}

export const fetchAllCities = async () => {
  const response = await fetch(CITIES_URL)
  const data = await response.json()
  return data
}

export const fetchCity = async (
  id?: number,
  name?: string,
  country?: string
) => {
  let url = CITIES_URL

  if (id) {
    url += `/${id}`
  } else if (name && country) {
    url += `?name=${name}&country=${country}`
  } else {
    throw new Error("Invalid arguments. Either provide id or name and country.")
  }

  const response = await fetch(url)

  if (!response.ok) {
    const errorMessage = await response.text()
    throw new Error(errorMessage ? errorMessage : response.statusText)
  }

  const data = await response.json()
  return data
}

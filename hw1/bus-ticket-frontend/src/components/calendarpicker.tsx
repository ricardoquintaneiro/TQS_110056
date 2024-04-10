import { useState } from "react"
import Datepicker from "tailwind-datepicker-react"

export const CalendarPicker = ({ onSelect }: { onSelect: (selectedDate: Date) => void }) => {
  const [show, setShow] = useState<boolean>(false)

  const handleClose = (state: boolean) => {
    setShow(state)
  }

  const handleChange = (selectedDate: Date) => {
    onSelect(selectedDate)
  }

  const options = {
    minDate: new Date(),
    defaultDate: null
  }

  return (
    <div>
      <Datepicker options={options} onChange={handleChange} show={show} setShow={handleClose} />
    </div>
  )
}

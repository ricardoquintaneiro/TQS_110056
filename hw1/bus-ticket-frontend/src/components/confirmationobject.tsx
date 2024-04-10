export const ConfirmationObject = (object) => {
  return (
    <div className="w-full h-full">
      <div className="mb-2 flex justify-between items-center">
        <p className="text-sm font-medium text-gray-900 dark:text-white">Your reservation details:</p>
      </div>
      <div className="relative bg-gray-50 rounded-lg dark:bg-gray-700 p-4 h-full">
        <div className="overflow-scroll max-h-full">
          <pre>
            <code
              id="code-block"
              className="text-sm text-gray-500 dark:text-gray-400 whitespace-pre"
            >{JSON.stringify(object, null, 2)}</code>
          </pre>
        </div>
      </div>
    </div>
  )
}

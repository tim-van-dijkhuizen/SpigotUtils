# Helpers
Helpers are classes that *help* you do common tasks faster by doing less.

### ConsoleHelper
For console related things like logging.
##### setDevMode(boolean devMode)
Allows you to enable/disable development mode. When devmode is enabled stacktraces will be logged and debug messages are shown.
##### printInfo
Prints an info message to the console.
##### printError
Prints an error message to the console. This method has an optional parameter to pass a Throwable.
##### printDebug(String message)
Prints a debug message to the console.

### ExceptionHelper
For error related things like stacktraces.
##### getStackTrace
Converts a Throwable stacktrace into a String array.

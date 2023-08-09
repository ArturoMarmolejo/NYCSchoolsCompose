# New York City Schools App

Last updated: 2023-08-09

This is an Application made in Kotlin using the mvvm architecture, with the following list of technologies used:

- Data binding for views.
- View Model from Jetpack for handling business for the UI
- Hilt for dependency Injection
- Retrofit for data retrieval and handling the proper network calls and responses.


## Installation

Download the zip file on this page or clone the project via SSH key using the following command on your terminal, in the location of your preference:

```bash
git clone git@github.com:ArturoMarmolejo/AcronymsAppAMM.git
```

## Usage

Open the Project using Android Studio, and run the App on the emulator of your preference (A Pixel 4 API 30-33 is recommended). Afterwards, you will be prompted to a screen with a single text field for searching. Type any kind of acronym you would like to find its meaning, and type enter, or click on the magnifying key of the device keyboard - only then, the network call will be made and the results will be retrieved from the API database.

The App is fully compatible with night mode.
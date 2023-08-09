# New York City Schools App

Last updated: 2023-08-09

This is an Application made in Kotlin using the mvvm architecture, with the following list of technologies used:

- Jetpack Compose for UI
- View Model from Jetpack for handling business for the UI
- Hilt for dependency Injection
- Retrofit for data retrieval and handling the proper network calls and responses.
- Coroutines for the process of performing the network calls asynchronously and bind them to their corresponding UI components.
- JUnit and Mock for Unit Testing


## Installation

Download the zip file on this page or clone the project via SSH key using the following command on your terminal, in the location of your preference:

```bash
git clone git@gitlab.com:ArturoMarmolejo/20230809-arturomarmolejo-nycschools.git
```

## Usage

Open the Project using Android Studio, and run the App on the emulator of your preference (A Pixel 4 API 30-33 is recommended). Afterwards, you will be prompted to a screen with a single text field for searching. Type any kind of acronym you would like to find its meaning, and type enter, or click on the magnifying key of the device keyboard - only then, the network call will be made and the results will be retrieved from the API database.

The App is fully compatible with night mode.


![Search from a list of schools in the display](https://cdn.discordapp.com/attachments/1045041684205023382/1138935515022889030/sc1.png "Main Screen")


![Check the information and SAT Scores for a particular school](https://cdn.discordapp.com/attachments/1045041684205023382/1138935515421364324/sc2.png "Details Screen")
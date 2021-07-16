##  :beginner: About
BookLibrary is an app in which users can order books from a virtual library. 
The app offers a searchable book catalogue, allowing the user to search books by text or by category. 
Once a book is ordered by a user, no other use can order it until it is returned to the library. 
Additionally, users can see their order history.
Admin users can return books for other users, can see a list of all the currently lent books, and can add new books to the catalogue.

This project is implemented with the MVVM design pattern:
Each activity is build using an "Activity" class (The view) and a ViewModel.
The ViewModel of each activity is responsible for the data and state of the Activity.
The actual Activity class only handles displaying the UI that matches the state of the ViewModel, 
and handles user interaction by calling the appropriate methods in the ViewModel.

All data loading\saving is handled using a Repository, that delegates the tasks to the appropriate handlers (SharedPreferences for persistent storage, Retrofit2 for network requests)

Dependency injection is used to allow quick swapping between components. 
Currently, the only swappable component is the Repository - for a MockRepository.

###  :file_folder: File Structure
Below is the file structure and all the important classes and folders

```
.
├── java/com/openu/sadna/booklibrary
│   ├── common
│   │   └── OptionsMenuHandler.java
│   ├── data
│   │   ├── MockRepository.java
│   │   ├── RepositoryImpl.java
│   │   └── SharedPrefs.java
│   ├── network 
│   │   ├── pojo/
│   │   ├── APIInterface.java
│   │   └── RestClient.java
│   ├── ui 
│   │   ├── addBookActivity/
│   │   ├── adminLentBooksTrackingActivity/
│   │   ├── bookDetailsActivity/
│   │   ├── booksCatalogActivity/
│   │   ├── lendingHistoryActivity/
│   │   ├── loginActivity/
│   │   └── BaseActivity.java
│   └── util 
│       └── InjectorUtils.java
├── res/
├── AndroidManifest.xml
└── README.md
```

| No  | File Name                       | Details 
|-----|----------------------           |-----------------------------------------------------------------------------------------------------------------------------------------|
| 1   |OptionsMenuHandler.java          | Controller for the action bar (options menu), used by BaseActivity and any class that extends it
| 2   |MockRepository.java              | A mock repository to test app behaviour
| 3   |RepositoryImpl.java              | The real repository of the app
| 4   |SharedPrefs.java                 | Implementation of Shared Preferences storage
| 5   |pojo/                            | Model objects for data returned from the repository (Also used as model for Retrofit)
| 6   |APIInterface.java                | API interface implementation for Retrofit
| 7   |RestClient.java                  | Rest API client configuration
| 8   |addBookActivity/                 | Activity for adding new books (only available for Admin users)
| 9   |adminLentBooksTrackingActivity/  | Activity for viewing all the books currently lent to users (only available for Admin users)
| 10  |bookDetailsActivity/             | Activity for viewing a single book's details and reviews
| 11  |booksCatalogActivity/            | Activity for book catalog and searching books
| 12  |lendingHistoryActivity/          | Activity for the lending history of the user
| 13  |loginActivity/                   | Entry point - Launcher activity for login/registration
| 14  |BaseActivity.java                | Base class for all activities - implements the action bar (options menu)
| 15  |InjectorUtils.java               | Dependency injection helper - Useful for switching dependencies (e.g using MockRepository instead of real one)
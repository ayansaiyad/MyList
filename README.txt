Prerequisites

    Android Studio: Iguana or latest stable version (recommended: Arctic Fox or later)

    Java: Version 11 or higher (configured in project build.gradle)

    Android SDK: API level 21 (Android 5.0 Lollipop) or higher

    Internet Access: (only needed to download dependencies and Material icons at build time)

    Emulator or physical Android device: API 21+
How to Build and Run
1. Download the Project

    Download ZIP:

        Download the folder

        Unzip

        Open the MyList directory in Android Studio

2. Open with Android Studio

    Launch Android Studio

    Choose “Open an existing project”

    Select the folder containing this README

3. Sync Dependencies

    Android Studio will auto-detect missing libraries.

    If prompted, click “Sync Now” and wait for Gradle to finish.

    If you see red underlines for Room/Material/RecyclerView, check the project’s app/build.gradle for the following dependencies:

text
implementation "androidx.room:room-runtime:2.6.1"
annotationProcessor "androidx.room:room-compiler:2.6.1"
implementation "androidx.recyclerview:recyclerview:1.4.0"
implementation "androidx.lifecycle:lifecycle-viewmodel:2.8.0"
implementation "androidx.lifecycle:lifecycle-livedata:2.8.0"
implementation "com.google.android.material:material:1.12.0"
implementation "androidx.appcompat:appcompat:1.7.0"

Sync again if you add/fix dependencies.
4. Run the App

    Connect an emulator or Android device.

    Click the green “Run” arrow or use Shift+F10.

    Select your target device in the run options.


Usage Instructions
1. Login

    The app starts on a login screen.

    Username: ayan

    Password: ayan123

    Correct credentials open the main task list. Incorrect credentials will show an error message and require retry.

2. Main Screen

    The main screen displays all your pending tasks.

    Use the plus (FAB) button (bottom right) to add a new task:

        Enter a title (required), description (optional), due date, and mark as complete if desired.

    Tap a task to edit; long-press or swipe to delete.

    Mark tasks complete with the checkbox.

3. Logout

    Click the Αποσύνδεση (“Logout”) button (bottom left) to end your session.

    The app returns to the login screen; tasks remain until deleted.

Data & Persistence

    All tasks are saved on the device using Room (SQLite local storage).

    Data persists after app closure or device restart.

    Logout does not delete tasks; only explicit delete actions remove them.


# TOEIC Vocabulary Learning App

A simple and interactive Android application built with Java in Android Studio to help users prepare for the TOEIC exam by learning vocabulary through topic-based flashcards, daily learning limits, and quizzes. The app promotes consistent learning with engaging features like input-based exercises and progress tracking.

## Table of Contents
- [Project Overview](#project-overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Setup Instructions](#setup-instructions)
- [Usage Guide](#usage-guide)
- [Future Improvements](#future-improvements)
- [Contributing](#contributing)
- [Troubleshooting](#troubleshooting)
- [License](#license)
- [Contact](#contact)

## Project Overview
The TOEIC Vocabulary Learning App is designed to assist users in mastering TOEIC vocabulary by organizing words into topics (e.g., Business, Travel). It offers a flashcard-based learning system with multiple modes, enforces a daily limit of 10 words to encourage steady progress, and includes quizzes to test retention. Built using Java and the MVVM architecture, the app ensures clean code and scalability.

**Target Audience**: TOEIC candidates, students, professionals, and Android development learners looking for a practical project example.

**Date Created**: May 2025

## Features
1. **Topic-Based Vocabulary Learning**:
   - Pre-loaded topics with English-Vietnamese word pairs.
   - Browse and select topics to study.
2. **Customizable Content**:
   - Add or edit topics.
   - Add or edit vocabulary within a topic.
3. **Flashcard Learning Modes**:
   - **Mode 1**: View English word, guess Vietnamese meaning, flip to check.
   - **Mode 2**: View Vietnamese word, guess English word, flip to check.
   - **Mode 3**: View English word, type Vietnamese meaning (or vice versa), and verify.
4. **Daily Learning Limit**:
   - Learn up to 10 words per day to foster consistency.
   - Tracks progress and locks learning after the limit is reached.
5. **Quiz Mode**:
   - Test 10 previously learned words with multiple-choice questions.
   - Provides score and feedback.
6. **Progress Tracking**:
   - Saves learned words and quiz results for review.

## Tech Stack
- **Programming Language**: Java
- **IDE**: Android Studio (2023.3.1 or later recommended)
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: SQLite (local storage for topics, vocabulary, and progress)
- **Libraries**:
  - `androidx.recyclerview:recyclerview:1.3.2` (for dynamic lists)
  - `androidx.lifecycle:lifecycle-viewmodel:2.8.0` (for ViewModel)
  - `androidx.lifecycle:lifecycle-livedata:2.8.0` (for LiveData)
- **Minimum SDK**: API 21 (Android 5.0 Lollipop)
- **Target SDK**: API 35 (Android 15)

## Project Structure
The project is organized for modularity and ease of maintenance:

```
TOEICVocabularyApp/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/toeicvocab/
│   │   │   │   ├── model/
│   │   │   │   │   ├── Topic.java
│   │   │   │   │   ├── Vocabulary.java
│   │   │   │   │   ├── LearningProgress.java
│   │   │   │   ├── view/
│   │   │   │   │   ├── MainActivity.java
│   │   │   │   │   ├── VocabularyListActivity.java
│   │   │   │   │   ├── AddTopicActivity.java
│   │   │   │   │   ├── AddVocabularyActivity.java
│   │   │   │   │   ├── FlashcardActivity.java
│   │   │   │   │   ├── TestActivity.java
│   │   │   │   ├── viewmodel/
│   │   │   │   │   ├── TopicViewModel.java
│   │   │   │   │   ├── VocabularyViewModel.java
│   │   │   │   ├── db/
│   │   │   │   │   ├── DatabaseHelper.java
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_main.xml
│   │   │   │   │   ├── activity_vocabulary_list.xml
│   │   │   │   │   ├── activity_add_topic.xml
│   │   │   │   │   ├── activity_add_vocabulary.xml
│   │   │   │   │   ├── activity_flashcard.xml
│   │   │   │   │   ├── activity_test.xml
│   │   │   ├── AndroidManifest.xml
│   ├── build.gradle
├── README.md
```

- **model/**: Defines data classes (`Topic`, `Vocabulary`, `LearningProgress`) for database entities.
- **view/**: Contains Activities for UI (e.g., `MainActivity` for topic list, `FlashcardActivity` for learning).
- **viewmodel/**: Manages data and logic (`TopicViewModel`, `VocabularyViewModel`) using LiveData.
- **db/**: Handles SQLite operations via `DatabaseHelper`.
- **res/layout/**: XML layouts for UI components.

## Prerequisites
- **Android Studio**: Version 2023.3.1 or later.
- **JDK**: Version 17 or later.
- **Android Device/Emulator**: Android 5.0 (API 21) or higher.
- **Git**: For cloning the repository.
- **Knowledge**: Basic understanding of Java, Android Activities, Intents, and SQLite.

## Setup Instructions
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/hungtabe/toeic-vocabulary-app.git
   cd toeic-vocabulary-app
   ```
2. **Open in Android Studio**:
   - Launch Android Studio.
   - Choose `Open an existing project` and select the cloned folder.
3. **Sync Dependencies**:
   - Open `app/build.gradle` and ensure the following:
     ```gradle
     dependencies {
         implementation "androidx.recyclerview:recyclerview:1.3.2"
         implementation "androidx.lifecycle:lifecycle-viewmodel:2.8.0"
         implementation "androidx.lifecycle:lifecycle-livedata:2.8.0"
     }
     ```
   - Click `Sync Project with Gradle Files`.
4. **Configure Emulator or Device**:
   - Set up an emulator in AVD Manager (API 21 or higher).
   - Or enable USB debugging on an Android device.
5. **Build and Run**:
   - Click `Run > Run 'app'` to launch the app on the emulator or device.

## Usage Guide
1. **Launch the App**:
   - `MainActivity` displays a list of topics (e.g., "Business", "Travel").
2. **Browse Topics**:
   - Tap a topic to open `VocabularyListActivity` and view its words.
3. **Learn Vocabulary**:
   - Select "Learn" to enter `FlashcardActivity`.
   - Choose a mode:
     - **Flip Mode**: View English/Vietnamese word, tap "Flip" to see translation.
     - **Input Mode**: View word, type translation, tap "Check" for feedback.
   - Limited to 10 words/day; a message appears when the limit is reached.
4. **Add Content**:
   - In `MainActivity`, tap "Add Topic" to create a new topic (`AddTopicActivity`).
   - In `VocabularyListActivity`, tap "Add Vocabulary" to add words (`AddVocabularyActivity`).
5. **Take a Quiz**:
   - Select "Test" to enter `TestActivity`.
   - Answer 10 multiple-choice questions based on learned words.
   - View score and review incorrect answers.
6. **Track Progress**:
   - Progress is saved in SQLite and can be reviewed in future updates.


## Future Improvements
- **Database Upgrade**: Use Room Database for better data management.
- **Modern UI**: Implement Data Binding or Jetpack Compose.
- **User Accounts**: Add authentication for cloud-based progress syncing.
- **Multimedia**: Include audio pronunciation or images for vocabulary.
- **Analytics**: Track learning streaks and detailed statistics.
- **Offline Support**: Cache data for offline access.
- **Localization**: Support additional languages beyond Vietnamese.

## Contributing
We welcome contributions to enhance the app! To contribute:
1. **Fork the Repository**:
   - Click "Fork" on the GitHub repository page.
2. **Create a Branch**:
   ```bash
   git checkout -b feature/your-feature-name
   ```
3. **Make Changes**:
   - Add new features, fix bugs, or improve documentation.
   - Adhere to MVVM architecture and consistent naming.
4. **Commit and Push**:
   ```bash
   git commit -m "Add your feature description"
   git push origin feature/your-feature-name
   ```
5. **Open a Pull Request**:
   - Describe your changes and link to any related issues.
6. **Code Review**:
   - Address feedback from maintainers.

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for detailed guidelines.

## Troubleshooting
- **Gradle Sync Fails**:
  - Ensure internet connection and correct `build.gradle` dependencies.
  - Try `File > Invalidate Caches / Restart` in Android Studio.
- **App Crashes on Launch**:
  - Check Logcat for errors (e.g., NullPointerException).
  - Verify SQLite table creation in `DatabaseHelper`.
- **Emulator Issues**:
  - Confirm emulator uses API 21 or higher.
  - Increase emulator RAM in AVD Manager if slow.
- **Daily Limit Not Working**:
  - Check `LearningProgress` table updates in SQLite.
  - Verify date comparison logic in `FlashcardActivity`.

For additional help, open an issue on the GitHub repository.

## License
This project is licensed under the [MIT License](LICENSE). See the [LICENSE](LICENSE) file for details.

## Contact
For questions, feedback, or support:
- **Email**: [trandinhhung717@gmail.com]
- **GitHub Issues**: Open an issue at [github.com/hungtabe/toeic-vocabulary-app/issues]


Thank you for using the TOEIC Vocabulary Learning App!

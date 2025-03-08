# Task Management App

Welcome to the **Task Management App**! 🚀 This app helps you manage tasks efficiently with a clean, maintainable architecture.

## 📌 Features
- Create, update, and delete tasks
- Set task priority levels
- Sort tasks by different criteria
- Persistent storage with Room database
- Fully reactive UI with Jetpack Compose
- Uses **Koin** for dependency injection

---

## 🏗️ Architecture Overview
This project follows **Clean Architecture** with an **MVVM** pattern for better separation of concerns.

### 1️⃣ Clean Architecture Layers

#### **Presentation Layer** (UI + ViewModels)
Contains UI components, ViewModels, and navigation logic.
- `presentation/screens/` - Jetpack Compose screens
- `presentation/viewmodels/` - ViewModels handling state & business logic
- `presentation/theme/` - App theme, colors, typography
- `presentation/navigation/` - Navigation components

#### **Domain Layer** (Business Logic)
Defines the core business logic and entities.
- `domain/data/` - Data models like `Task.kt`
- `domain/repository/` - Repository interfaces
- `domain/enums/` - Enums like `TaskPriority.kt`, `SortOrder.kt`

#### **Data Layer** (Data Persistence & APIs)
Handles database interactions and data persistence.
- `datasource/` - Room database, DAOs, repository implementations
- `utils/` - Utility functions like data converters

---

## 🏛️ MVVM Pattern
The app follows **MVVM** to structure UI and business logic:
- **View**: Jetpack Compose UI screens (`presentation/screens/`)
- **ViewModel**: `TaskViewModel.kt` manages UI state and interacts with repositories
- **Model**: Repository interfaces and data entities (`domain/repository/` & `domain/data/`)

---

## 🛠️ Dependency Injection with Koin
We use **Koin** for dependency injection. Modules include:
- `CoroutineDispatcherModule.kt` - Provides coroutine dispatchers
- `DatabaseModules.kt` - Provides Room database & DAOs
- `RepositoryModule.kt` - Provides repository implementations
- `ViewModelModule.kt` - Provides ViewModels

---

## 💾 Local Persistence
This app uses **Room Database** for local data storage.
- `TaskDatabase.kt` - Database definition
- `TaskDao.kt` - Data Access Object (DAO) for handling queries
- `TaskRepositoryImpl.kt` - Repository implementation

---

## 🔄 Reactive Programming
- **Kotlin Flows** handle real-time data updates
- Repository methods return `Flow<List<Task>>` to observe task changes
- ViewModels expose `StateFlow<TaskState>` for UI state management

---

## 🎨 UI with Jetpack Compose
- Fully **declarative UI** using Jetpack Compose
- Material 3 components for a modern look & feel
- Animations for smooth transitions

---

## 🚀 Getting Started
### Prerequisites
- Android Studio Giraffe (or newer)
- Kotlin 1.8+
- Gradle 8+

### Setup
1. Clone this repository:
   ```sh
   git clone https://github.com/your-repo/task-management-app.git
   cd task-management-app
   ```
2. Open in Android Studio
3. Sync Gradle and run the app 🎉


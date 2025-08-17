# Movio - Your Ultimate Entertainment Companion

<div align="center">
  <img src="https://img.shields.io/badge/Platform-Android-green.svg" alt="Platform">
  <img src="https://img.shields.io/badge/Language-Kotlin-blue.svg" alt="Language">
  <img src="https://img.shields.io/badge/UI-Jetpack%20Compose-orange.svg" alt="UI">
  <img src="https://img.shields.io/badge/Architecture-MVVM-red.svg" alt="Architecture">
  <img src="https://img.shields.io/badge/DI-Hilt-yellow.svg" alt="DI">
</div>

A modern Android app built with Jetpack Compose for browsing and watching series. It includes detailed series information, seasons, and episodes.

## 🚀 Features

### 🎯 **Smart Movie Discovery**
- **AI-Powered Content Detection**: Advanced image analysis to identify movies from screenshots or posters
- **Personalized Recommendations**: Intelligent suggestion engine based on viewing history and preferences
- **Episode Trailers**: Seamless trailer integration for TV shows and movie series

### 🔍 **Enhanced Search & Browse**
- **Real-time Search**: Lightning-fast movie and TV show search with auto-suggestions
- **Trending Content**: Stay updated with what's popular worldwide

### 📱 **Modern User Experience**
- **Jetpack Compose UI**: Fluid, responsive interface
- **Dark/Light Theme**: Automatic theme switching based on system preferences
- **Offline Support**: Browse previously viewed content without internet connection
- **Localization Support**: Multi-language experience with language selection based on user preferences

### 🔐 **Robust Backend Integration**
- **Firebase Analytics**: Comprehensive user behavior tracking
- **Crash Reporting**: Real-time crash monitoring with Firebase Crashlytics

### 🏗️ **Enterprise-Grade Architecture**
- **Clean Architecture**: Separation of concerns with Domain-Data-Presentation layers
- **Dependency Injection**: Hilt-powered DI for testable, maintainable code
- **Modular Design**: Feature-based modules for scalability
## 🤖 AI/ML Features

The app includes intelligent content detection capabilities:

### Image Content Analysis
- **Scene Detection**: Automatically detect and categorize scene types
- **Content Rating**: AI-powered content appropriateness detection
- **Image Quality Assessment**: Analyze image quality for optimal display
- **Auto-tagging**: Generate relevant tags for series posters and scenes

## 🛠️ Tech Stack

- **Language**: Kotlin
- **Android SDK**
- **Work Manager**
- **Paging 3**
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM + Clean Architecture
- **Dependency Injection**: Hilt (Dagger)
- **Build System**: Gradle
- **Navigation**: Compose Navigation
- **Networking**: Retrofit + OkHttp
- **Image Loading**: Coil
- **Async Programming**: Coroutines + Flow
- **Database**: Room
- **Testing**: JUnit, MockK, Truth
- **Content Detection**: ML Kit / TensorFlow Lite
## 🏗️ App Architecture
The app follows **Clean Architecture** principles with **MVVM** pattern and **Modularization** for robust, scalable, and testable code:

### Modular Architecture (By Presentation/Data/Domain)
<img width="1028" height="495" alt="simple" src="https://github.com/user-attachments/assets/7ef5ce50-0298-4800-a56f-905272f5c789" />


### 📦 Module Overview

The app is structured using a **multi-module architecture** to promote separation of concerns, improve build performance, and enable better team collaboration. Each module has specific responsibilities:

### 🎯 Core Modules

#### `:app` - Main Application Module
**Responsibilities:**
- Application entry point and initialization
- Hilt dependency injection setup
- Activity hosting for navigation
- App-level configuration and permissions
#### `:presentation` - UI Layer Module
**Responsibilities:**
- Jetpack Compose UI screens and components
- MVVM ViewModels for state management
- Navigation between screens
- UI state handling and user interactions
- Screen-specific business logic
#### `:domain` - Business Logic Module
**Responsibilities:**
- Pure business logic implementation
- Use cases for complex operations
- Domain models and entities
- Repository interfaces (contracts)
- Business rules and validations
#### `:data`
**Responsibilities:**
- Repository pattern implementation
- API integration with Retrofit
- Local database with Room
- Data transformation and mapping
- Caching strategies and offline support
#### `:designSystem` - UI Design System Module
**Responsibilities:**
- Consistent UI component library
- Reusable Compose components
- Design tokens and guidelines
- Cross-module UI consistency
#### `:detectImageContent` - AI/ML Content Analysis Module
**Responsibilities:**
- **Image Content Analysis**: Automatically detect haram or inappropriate elements in series posters and episode images  
- **Scene Content Detection**: Identify and flag scenes containing haram content for automatic blurring  
- **Content Filtering**: AI-powered detection to blur inappropriate content for compliance with family-friendly viewing standards  
## 📋 Prerequisites

- Android Studio Narwhal (2025.1.2) or later
- JDK 11 or higher
- Android SDK 24 or higher
- Git
## 🔧 Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/MadridSquad/Movio.git
cd movio-android
```
### 2. Create Configuration Files
Create a secrets.properties file in the root directory:
```bash
# API Configuration
BASE_URL="your_api_base_url"
API_KEY="your_api_key"
```
### 3. Add Local Properties (if needed)
Update local.properties with your Android SDK path:
```bash
sdk.dir=/path/to/your/Android/Sdk
```
### 4. Build and Run
1. Open the project in Android Studio  
2. Sync the project with Gradle files  
3. Build the project (**Build > Make Project**)  
4. Run on device or emulator (**Run > Run 'app'**)
### 5.📱 Running the App
1. Enable USB debugging and connect your physical device  
2. Create an Android Virtual Device (AVD) with API level 24 or higher  
3. Choose between `debug` and `release` builds from the Build Variants panel
## 📸 Screenshots
<table style="width: 100%; border-collapse: collapse;"><tbody><tr><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Onboarding</th><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Splash</th><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Login</th><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Home</th><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Search</th></tr><tr><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Onboarding" src="https://github.com/user-attachments/assets/b8ff1da0-4bb0-456e-a9a4-6c160c509d1e"></td><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Splash" src="https://github.com/user-attachments/assets/e04b11e4-decc-4db0-a4d6-1b29c9a4b463"></td><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Login" src="https://github.com/user-attachments/assets/de75db68-6dda-4f7d-b7dc-26312a7e26bb"></td><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Home" src="https://github.com/user-attachments/assets/abe069f2-7205-4489-ba30-ee5ac8be66ed"></td><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Search" src="https://github.com/user-attachments/assets/1e784e30-7718-49d1-9455-be9cb870769d"></td></tr><tr><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Library</th><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Profile</th><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Movie details</th><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Submit rating</th><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Sharing</th></tr><tr><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Library" src="https://github.com/user-attachments/assets/5a4abf47-a9d4-4017-a66c-87e1a3262de8"></td><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Profile" src="https://github.com/user-attachments/assets/e5dd57cd-8b00-4c37-8d15-1c762a1a822b"></td><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Movie details" src="https://github.com/user-attachments/assets/ad50ce2c-cafc-4f10-9206-2bd45acb7348"></td><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Submit rating" src="https://github.com/user-attachments/assets/6f1f8b33-0e87-4a79-90a6-8f4e1776abe2"></td><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Sharing" src="https://github.com/user-attachments/assets/9e7d1f85-7719-4593-b897-d03d6a467d5c"></td></tr><tr><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Actor details</th><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">loading data</th><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Current seasons</th><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">My ratings</th><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Reviews</th></tr><tr><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Actor details" src="https://github.com/user-attachments/assets/7f0b3b17-fdee-429c-a695-b724e1aa813e"></td><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="loading data" src="https://github.com/user-attachments/assets/a68e54b4-c51f-49f6-bfdb-6598e67935bb"></td><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Current seasons" src="https://github.com/user-attachments/assets/7f91e31c-0c62-4bf6-aefd-e722abbfab52"></td><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="My ratings" src="https://github.com/user-attachments/assets/549f3700-ce83-462d-a01a-4222d1da74b5"></td><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Reviews" src="https://github.com/user-attachments/assets/7329f0bb-b674-4958-9edf-51642749e60c"></td></tr><tr><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Series details</th><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Similer series</th><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Top cast</th><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Supporting arabic language</th><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Light theme</th></tr><tr><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Series details" src="https://github.com/user-attachments/assets/d6a7655e-8149-418f-bf62-1291540f8e8e"></td><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Similer series" src="https://github.com/user-attachments/assets/99d0bd96-503d-44e7-99f5-a69995be89f4"></td><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Top cast" src="https://github.com/user-attachments/assets/0e8807e5-7ca1-40e4-ab7a-59b6307e8221"></td><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Supporting arabic language" src="https://github.com/user-attachments/assets/51e18152-8441-4208-bb4b-cfa5bc4b2ee9"></td><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Light theme" src="https://github.com/user-attachments/assets/a35bc3e9-8b77-45eb-8e5e-224a43a94b1d"></td></tr></tbody></table>

## 👥 Contributors

Thanks goes to these amazing people for their contributions 💖

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/fatimafaisalghazi">
        <img src="https://avatars.githubusercontent.com/fatimafaisalghazi" width="80" alt="fatimafaisalghazi" />
        <br />
        <sub><b>fatimafaisalghazi</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/baraa0abd">
        <img src="https://avatars.githubusercontent.com/baraa0abd" width="80" alt="baraa0abd" />
        <br />
        <sub><b>baraa0abd</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/ahmedhgabr">
        <img src="https://avatars.githubusercontent.com/ahmedhgabr" width="80" alt="ahmedhgabr" />
        <br />
        <sub><b>ahmedhgabr</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/yousef-osama11">
        <img src="https://avatars.githubusercontent.com/yousef-osama11" width="80" alt="yousef-osama11" />
        <br />
        <sub><b>yousef-osama11</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Mohamedragabali">
        <img src="https://avatars.githubusercontent.com/Mohamedragabali" width="80" alt="Mohamedragabali" />
        <br />
        <sub><b>Mohamedragabali</b></sub>
      </a>
    </td>
  </tr>
  <tr>
    <td align="center">
      <a href="https://github.com/tasneem-hakeem">
        <img src="https://avatars.githubusercontent.com/tasneem-hakeem" width="80" alt="tasneem-hakeem" />
        <br />
        <sub><b>tasneem-hakeem</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/yasserahmed10">
        <img src="https://avatars.githubusercontent.com/yasserahmed10" width="80" alt="yasserahmed10" />
        <br />
        <sub><b>yasserahmed10</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Alaakhaled101">
        <img src="https://avatars.githubusercontent.com/Alaakhaled101" width="80" alt="Alaakhaled101" />
        <br />
        <sub><b>Alaakhaled101</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/karrar-abbas">
        <img src="https://avatars.githubusercontent.com/karrar-abbas" width="80" alt="karrar-abbas" />
        <br />
        <sub><b>karrar-abbas</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/AmrNasserSaad">
        <img src="https://avatars.githubusercontent.com/AmrNasserSaad" width="80" alt="AmrNasserSaad" />
        <br />
        <sub><b>AmrNasserSaad</b></sub>
      </a>
    </td>
  </tr>
  <tr>
    <td align="center">
      <a href="https://github.com/Ahmedsayed0895">
        <img src="https://avatars.githubusercontent.com/Ahmedsayed0895" width="80" alt="Ahmedsayed0895" />
        <br />
        <sub><b>Ahmedsayed0895</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Mohammed-qmr">
        <img src="https://avatars.githubusercontent.com/Mohammed-qmr" width="80" alt="Mohammed-qmr" />
        <br />
        <sub><b>Mohammed-qmr</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/MostafaMohamed2002">
        <img src="https://avatars.githubusercontent.com/MostafaMohamed2002" width="80" alt="MostafaMohamed2002" />
        <br />
        <sub><b>MostafaMohamed2002</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/mahmoudkhai">
        <img src="https://avatars.githubusercontent.com/mahmoudkhai" width="80" alt="mahmoudkhai" />
        <br />
        <sub><b>mahmoudkhai</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Mousaber00">
        <img src="https://avatars.githubusercontent.com/Mousaber00" width="80" alt="Mousaber00" />
        <br />
        <sub><b>Mousaber00</b></sub>
      </a>
    </td>
  </tr>
  <tr>
    <td align="center">
      <a href="https://github.com/YaseenOmar">
        <img src="https://avatars.githubusercontent.com/YaseenOmar" width="80" alt="YaseenOmar" />
        <br />
        <sub><b>YaseenOmar</b></sub>
      </a>
    </td>
  </tr>
</table>

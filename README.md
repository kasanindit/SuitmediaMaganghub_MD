# Suitmedia Magang Hub - Mobile Developer Technical Test

A simple Android application developed as part of a Mobile Developer technical test.  
The application consists of three main screens and demonstrates form validation, palindrome checking, navigation between screens, API integration, pagination, pull-to-refresh, and user selection.

## Features

### 1. Login / First Screen
- Input user name.
- Input text to check whether it is a palindrome.
- Palindrome validation.
- Toast validation when the name field is empty.
- Dialog / message result for palindrome checking.
- Navigate to the Home screen while passing the entered name.

### 2. Home Screen
- Displays the name entered on the first screen.
- Displays the currently selected user.
- Button to navigate to the User List screen.
- Selected user name is updated after choosing a user from the third screen.
- Back navigation support.

### 3. User List Screen
- Fetches user data from ReqRes API.
- Displays:
  - Profile image
  - First name
  - Last name
  - Email
- Pagination using `page` and `per_page`.
- Automatically loads the next page when the user scrolls near the bottom of the list.
- Pull-to-refresh support.
- Loading state.
- Error state.
- Empty state when no user data is available.
- Select a user and return the selected user name to the Home screen.

## Application Flow
```text
Login Page
    │
    │ Enter Name
    │ Check Palindrome
    ▼
Home Page
    │
    │ Choose User
    ▼
User List Page
    │
    │ Select User
    ▼
Home Page
```

---

## Technical Specifications

| Specification | Details |
| --- | --- |
| Platform | Android |
| Language | Kotlin |
| UI Toolkit | Jetpack Compose |
| Architecture | MVVM |
| Minimum SDK | 24 |
| Target SDK | 34 |
| Networking | Retrofit |
| Image Loading | Glide |
| API | ReqRes API |
| Navigation | Navigation Compose |
| State Management | StateFlow |
| Async Processing | Kotlin Coroutines |

---

## Architecture

The application follows the **MVVM (Model-View-ViewModel)** architecture to separate UI, application state, and data access responsibilities.

```text
UI / Composable
      │
      ▼
ViewModel
      │
      ▼
Repository
      │
      ▼
API Service
      │
      ▼
ReqRes API
```

---

## Data Layer

The data layer contains API services, API configuration, response models, and repositories.

Example structure:

```text
data/
├── API/
│   ├── ApiConfig.kt
│   └── ApiService.kt
│
├── model/
│   ├── DataItem.kt
│   └── ListUserResponse.kt
│
└── repository/
    └── ListUserRepository.kt
```

---

# API Integration

The application uses the ReqRes API to retrieve user data.

Base URL:

```text
https://reqres.in/
```

Example endpoint:

```http
GET /api/users?page=1&per_page=10
```

Example Retrofit interface:

```kotlin
@GET("api/users")
suspend fun getUsers(
    @Header("x-api-key") apiKey: String,
    @Query("page") page: Int,
    @Query("per_page") perPage: Int
): ListUserResponse
```

Example API response:

```json
{
  "page": 1,
  "per_page": 10,
  "total": 12,
  "total_pages": 2,
  "data": [
    {
      "id": 1,
      "email": "example@email.com",
      "first_name": "First",
      "last_name": "User",
      "avatar": "https://example.com/avatar.jpg"
    }
  ]
}
```
---

# Author
**Kasamira Anindita Qairia**  
Developed as part of the **Suitmedia Mobile Developer Technical Test**  
Linkedin: [Kasamira Anindita Qairia](https://www.linkedin.com/in/kasamira-anindita-qairia-9aa88524b/)

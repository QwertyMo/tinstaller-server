# Документация API для TInstaller Server

## Эндпоинты

### 1. Получение репозитория
**URL:** `/repo/{name}`  
**Метод:** `GET`  
**Описание:** Возвращает информацию о конкретном репозитории.  
**Параметры:**
- `name` (Path Variable): Имя репозитория.

**Пример запроса:**
```
GET /repo/sample-repo
```

**Пример ответа:**
```json
{
  "apps": [
    {
      "title": "Название приложения",
      "description": "Описание приложения",
      "url": "http://example.com/repo/sample-repo/download/app.apk",
      "appReview": "Обзор",
      "category": "Категория"
    }
  ]
}
```

---

### 2. Получение всех репозиториев
**URL:** `/repo`  
**Метод:** `GET`  
**Описание:** Возвращает список всех репозиториев.  
**Заголовки:**
- `Authorization` (Обязательный): Токен для аутентификации.

**Пример запроса:**
```
GET /repo
Authorization: Bearer <token>
```

**Пример ответа:**
```json
[
  "repo1",
  "repo2",
  "repo3"
]
```

**Ошибки:**
- `401 Unauthorized`: Если токен недействителен или отсутствует.

---

### 3. Добавление приложения в репозиторий
**URL:** `/repo/{name}`  
**Метод:** `POST`  
**Описание:** Добавляет новое приложение в указанный репозиторий.  
**Заголовки:**
- `Authorization` (Обязательный): Токен для аутентификации.

**Параметры:**
- `name` (Path Variable): Имя репозитория.
- `app` (Form Data): Детали приложения, включая файл, категорию и название.

**Пример запроса:**
```
POST /repo/sample-repo
Authorization: Bearer <token>
Content-Type: multipart/form-data

Form Data:
- file: app.apk
- category: Utilities
- title: Sample App
```

**Примеры ответов:**
- `200 OK`: Приложение успешно добавлено.
- `400 Bad Request`: Отсутствуют обязательные поля (например, файл, категория, название).
- `401 Unauthorized`: Если токен недействителен или отсутствует.
- `409 Conflict`: Если приложение уже существует в репозитории.

---

### 4. Авторизация пользователя
**URL:** `/auth/login`  
**Метод:** `POST`  
**Описание:** Авторизует пользователя и возвращает токен.  
**Параметры:**
- `user` (Form Data): Логин и пароль.

**Пример запроса:**
```
POST /auth/login
Content-Type: multipart/form-data

Form Data:
- login: user_login
- password: user_password
```

**Примеры ответов:**
- `200 OK`: Успешная авторизация.
- `401 Unauthorized`: Неверные данные.

---

### 5. Изменение пароля
**URL:** `/auth/changepass`  
**Метод:** `PUT`  
**Описание:** Изменяет пароль пользователя.  
**Параметры:**
- `Authorization` (Header): Токен.
- `user` (Form Data): Новый пароль.

**Пример запроса:**
```
PUT /auth/changepass
Authorization: Bearer <token>
Content-Type: multipart/form-data

Form Data:
- password: new_password
```

**Примеры ответов:**
- `200 OK`: Пароль успешно изменен.
- `401 Unauthorized`: Неверный токен.

---

### 6. Получение информации о пользователе
**URL:** `/auth/me`  
**Метод:** `GET`  
**Описание:** Возвращает информацию о текущем пользователе.  
**Параметры:**
- `Authorization` (Header): Токен.

**Пример запроса:**
```
GET /auth/me
Authorization: Bearer <token>
```

**Примеры ответов:**
- `200 OK`: Информация о пользователе.
- `401 Unauthorized`: Неверный токен.

---

### 7. Создание репозитория
**URL:** `/repo/{name}/create`  
**Метод:** `POST`  
**Описание:** Создает новый репозиторий.  
**Параметры:**
- `Authorization` (Header): Токен.
- `name` (Path Variable): Имя репозитория.

**Пример запроса:**
```
POST /repo/sample-repo/create
Authorization: Bearer <token>
```

**Примеры ответов:**
- `200 OK`: Репозиторий создан.
- `401 Unauthorized`: Неверный токен.
- `409 Conflict`: Репозиторий уже существует.

---

### 8. Удаление репозитория
**URL:** `/repo/{name}/remove`  
**Метод:** `DELETE`  
**Описание:** Удаляет указанный репозиторий.  
**Параметры:**
- `Authorization` (Header): Токен.
- `name` (Path Variable): Имя репозитория.

**Пример запроса:**
```
DELETE /repo/sample-repo/remove
Authorization: Bearer <token>
```

**Примеры ответов:**
- `200 OK`: Репозиторий удален.
- `401 Unauthorized`: Неверный токен.
- `409 Conflict`: Репозиторий не существует.

---

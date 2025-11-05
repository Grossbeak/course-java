# Food Delivery Platform

Проект для доставки еды на Spring Boot. Три микросервиса: User, Restaurant, Order.

## Что нужно установить

- Java 17 или выше
- Docker и Docker Compose
- Gradle

## Как запустить

### Вариант 1: Через Docker

Просто запустить все одной командой:

```bash
cd course-java
docker-compose up -d --build
```

Проверка работы:

```bash
docker-compose ps
```

### Вариант 2:

Сначала запустить базы данных через Docker:

```bash
docker-compose up -d user-db restaurant-db order-db
```

Потом запускать сервисы по отдельности:

```bash
# User Service
./gradlew :user-service:bootRun

# Restaurant Service  
./gradlew :restaurant-service:bootRun

# Order Service
./gradlew :order-service:bootRun
```

## Порты

- User Service: http://localhost:8081
- Restaurant Service: http://localhost:8082  
- Order Service: http://localhost:8083

## Swagger UI

После запуска в браузере:

- User Service: http://localhost:8081/swagger-ui/index.html
- Restaurant Service: http://localhost:8082/swagger-ui/index.html
- Order Service: http://localhost:8083/swagger-ui/index.html

## Тестирование

### Тестирование через Postman


#### 1. Импорт коллекции

В корне проекта есть файл `User-Service.postman_collection.json`. В Postman:

1. `Import` (кнопка слева вверху)
2. Файл `User-Service.postman_collection.json`
3. Коллекция "User Service API" появится в списке

#### 2. Настройка переменных

1. В коллекции нажать на "User Service API"
2. Перейти на вкладку `Variables`
3. Добавь переменную:
   - `base_url` = `http://localhost:8081`
   - Сохранить


#### 3. Тестирование API

**Шаг 1: Регистрация пользователя**

1. Открыть `Authentication` → `Register User`
2. Нажать `Send`
3. В ответе будет токен

**Шаг 2: Логин (опционально)**

Если хочешь проверить логин:
1. Открыть `Authentication` → `Login`
2. В Body можно изменить email/password
3. Нажать `Send`
4. Токен обновится

**Шаг 3: Получить профиль**

1. `Users` → `Get Current User`
2. Нажать `Send`
3. Должен вернуться профиль пользователя

**Шаг 4: Обновить профиль**

1. `Users` → `Update Current User`
2. Изменить поля в Body (например, имя)
3. Нажать `Send`



### Тестирование через Swagger (кратко)

1. Открыть http://localhost:8081/swagger-ui/index.html
2. Нажать `POST /auth/register` → `Try it out`
3. Вставить JSON:
```json
{
  "email": "test@example.com",
  "password": "password123",
  "name": "Test User"
}
```
4. Нажать `Execute`, скопируй токен из ответа
5. Нажать кнопку `Authorize` (справа вверху)
6. Вставить `Bearer <токен>` → `Authorize`


## Структура проекта

```
course-java/
├── build.gradle           # Общие настройки для всех модулей
├── settings.gradle        # Какие модули есть
├── docker-compose.yml     # Настройка Docker
├── user-service/          # Сервис пользователей
├── restaurant-service/    # Сервис ресторанов
└── order-service/         # Сервис заказов
```

Каждый сервис имеет свою структуру:
- `src/main/java/` - код
- `src/main/resources/` - конфиги и миграции БД
- `build.gradle` - зависимости модуля

## Базы данных

У каждого сервиса своя база:
- `user_db` - пользователи, адреса, роли
- `restaurant_db` - рестораны и блюда
- `order_db` - заказы

Миграции БД применяются автоматически при запуске через Liquibase.

## Остановить все

```bash
docker-compose down
```

## Полезные команды

Посмотреть логи:
```bash
docker-compose logs -f user-service
```

Пересобрать и перезапустить:
```bash
docker-compose up -d --build
```

Собрать проект локально:
```bash
./gradlew build
```


## Текущая стадия

Пока работает только User Service полностью. Restaurant и Order Service - это пока каркас, нужно будет доработать.

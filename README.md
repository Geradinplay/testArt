# MessageService OData API

## Базовый путь
```
/odata/v4/messages/
```

## Доступные сущности и адреса

### 1. Messages
- **Описание:** Сообщения с ассоциацией к embeddings
- **GET все:**
  - `/odata/v4/messages/Messages`
- **GET c embeddings:**
  - `/odata/v4/messages/Messages?$expand=embeddings`
- **GET по ID:**
  - `/odata/v4/messages/Messages(<ID>)`
- **GET по ID c embeddings:**
  - `/odata/v4/messages/Messages(<ID>)?$expand=embeddings`

### 2. MessageEmbeddings
- **Описание:** Вектора (embeddings) для сообщений
- **GET все:**
  - `/odata/v4/messages/MessageEmbeddings`
- **GET по ID:**
  - `/odata/v4/messages/MessageEmbeddings(<ID>)`

## Примеры запросов

- Получить все сообщения:
  ```
  GET /odata/v4/messages/Messages
  ```
- Получить все сообщения с embeddings:
  ```
  GET /odata/v4/messages/Messages?$expand=embeddings
  ```
- Получить одно сообщение с embeddings:
  ```
  GET /odata/v4/messages/Messages(<ID>)?$expand=embeddings
  ```
- Получить все embeddings:
  ```
  GET /odata/v4/messages/MessageEmbeddings
  ```

## Примечания
- Для получения связанных embeddings всегда используйте параметр `$expand=embeddings`.
- <ID> — это UUID сообщения или embedding.
- Все адреса доступны через OData V4.

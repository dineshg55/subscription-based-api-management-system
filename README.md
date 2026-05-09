# Subscription Based API Access Management System

## About
A backend REST API built with Spring Boot that manages API access
through subscription plans. Users register, login, subscribe to a
plan and the system automatically tracks and limits their API usage.

> 🚧 Project is currently in progress. README will be updated upon completion.

---

## Tech Stack
- Java 21, Spring Boot 3.5
- Spring Security + JWT
- PostgreSQL, Spring Data JPA
- Lombok, Maven

---

## Subscription Plans

| Plan | Monthly API Limit |
|------|------------------|
| FREE | 100 requests |
| PRO | 1000 requests |
| PREMIUM | Unlimited |

---

## API Endpoints (In Progress)

### Authentication

| Method | Endpoint | Auth |
|--------|----------|------|
| POST | `/auth/register` | No |
| POST | `/auth/login` | No |

### Subscription

| Method | Endpoint | Auth |
|--------|----------|------|
| POST | `/subscription/subscribe` | Yes |
| GET | `/subscription/status` | Yes |

### Protected API

| Method | Endpoint | Auth |
|--------|----------|------|
| GET | `/api/data` | Yes |

---

## Author
- GitHub: dineshg55(https://github.com/dineshg55)
- LinkedIn: DINESH G(www.linkedin.com/in/dineshg55)
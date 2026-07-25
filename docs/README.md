## PriceSentinel

# 🛡 PriceSentinel

> Never miss the right price.

O **PriceSentinel** é uma aplicação desenvolvida em **Java + Spring Boot** para monitoramento inteligente de preços em lojas online.

O objetivo é permitir que o usuário configure produtos e preços-alvo para receber notificações automaticamente quando uma oferta for encontrada.

---

#  Objetivos

- Monitorar preços automaticamente
- Comparar com preços-alvo
- Enviar notificações
- Arquitetura Hexagonal
- Código limpo
- Fácil adição de novas lojas

---

#  Arquitetura

O projeto utiliza princípios de:

- Hexagonal Architecture (Ports & Adapters)
- Domain Driven Design (DDD)
- SOLID
- Clean Code

Estrutura:

```text
application
│
├── dto
├── mapper
├── service
└── usecase

domain
│
├── enums
├── exception
├── model
├── port
├── service
└── valueobject

infrastructure
│
├── config
├── notification
├── persistence
├── provider
├── scheduler
└── scraper

shared
└── util
```

---

# Tecnologias

- Java 21
- Spring Boot
- Maven
- Lombok
- Jsoup
- Spring Scheduler
- JUnit 5
- Mockito

---

# Tecnologias planejadas

- Spring Data JPA
- PostgreSQL
- Docker
- Testcontainers
- GitHub Actions

# Roadmap

## Sprint 1 — Fluxo completo (Offline)

- [x] Projeto Spring Boot
- [x] Estrutura de pacotes
- [x] Domain Models
- [x] Value Objects
- [x] Ports
- [x] Configuration Properties
- [x] FakeStoreScraperAdapter
- [x] PropertiesPriceTargetProvider
- [x] ConsoleNotificationChannel
- [x] PriceSearchUseCase
- [x] Scheduler
- [ ] Testes Unitários

---

## Sprint 2 — Primeiros Scrapers

- [ ] AmazonScraper
- [ ] KaBuMScraper
- [ ] PichauScraper
- [ ] Mercado LivreScraper
- [ ] TerabyteScraper
- [ ] GlaconScraper

---

## Sprint 3 — Notificações

- [ ] Push Notification
- [ ] Discord
- [ ] Telegram
- [ ] Email

---

## Sprint 4 — Dashboard

- [ ] API REST
- [ ] Histórico de preços
- [ ] Banco de dados
- [ ] Dashboard Web
- [ ] Docker

---

#  Produtos monitorados

Atualmente:

- AMD Ryzen 7 5700X
- AMD Ryzen 7 5700X3D

Novos produtos poderão ser adicionados via configuração.

---

# Fluxo da aplicação

```text
application.properties
            │
            ▼

PriceTargetProvider
            │
            ▼

StoreScraper
            │
            ▼
           
NotificationChannel
```

---

## Status

Sprint 1 em andamento.

Fluxo completo funcionando utilizando componentes fake:

- Fake PriceTargetProvider
- Fake StoreScraper
- Console Notification

Próxima etapa:

Implementação dos primeiros scrapers reais.


# Exemplo de execução

```text
INFO  Iniciando busca de preços...

INFO  Procurando preço para Ryzen 7 5700X

INFO  Pesquisando produto Ryzen 7 5700X

========================================

Produto : Ryzen 7 5700X

Preço   : R$ 989,90

Loja    : Amazon

========================================

INFO  Busca de preços finalizada.
```


#  Autor

Leandro Gomides

Projeto desenvolvido para estudos de arquitetura, integração com lojas online e monitoramento inteligente de preços.
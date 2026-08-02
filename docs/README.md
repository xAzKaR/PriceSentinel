# 🛡 PriceSentinel

> Never miss the right price.

PriceSentinel é uma aplicação desenvolvida em **Java 21 + Spring Boot** para monitoramento inteligente de preços em lojas online.

O projeto permite cadastrar produtos e preços-alvo, consultar diversas lojas automaticamente e notificar o usuário quando uma oferta atender aos critérios definidos.

O principal objetivo deste projeto é servir como laboratório para estudo de:

- Arquitetura Hexagonal
- Domain Driven Design (DDD)
- Clean Architecture
- Integrações HTTP
- Scraping com Jsoup
- Testes Unitários
- Boas práticas de desenvolvimento Java

---

# Funcionalidades

- Monitoramento automático de preços
- Pesquisa de produtos em múltiplas lojas
- Comparação com preço-alvo
- Persistência utilizando H2
- API REST
- Agendamento automático (Scheduler)
- Arquitetura desacoplada para inclusão de novas lojas
- Alta cobertura de testes unitários

---

# Arquitetura

O projeto segue os princípios de:

- Hexagonal Architecture (Ports & Adapters)
- Domain Driven Design (DDD)
- SOLID
- Clean Code

Estrutura do projeto:

```text
application
│
├── dto
├── mapper
├── service
├── normalization
└── usecase

domain
│
├── enums
├── exception
├── model
├── port
└── valueobject

infrastructure
│
├── config
├── constants
├── controller
├── http
├── notification
├── persistence
│   ├── entity
│   ├── mapper
│   └── repository
├── provider
├── scheduler
└── scraper
```

---

# Tecnologias

- Java 21
- Spring Boot
- Spring MVC
- Spring Data JPA
- Spring Scheduler
- H2 Database
- Maven
- Lombok
- Jsoup
- JUnit 5
- Mockito

---

# Roadmap
## Sprint 1 — Base do projeto

- [x] Estrutura inicial
- [x] Arquitetura Hexagonal
- [x] Domain Models
- [x] Value Objects
- [x] Use Cases
- [x] Ports
- [x] Scheduler
- [x] Configuração via Properties
- [x] Persistência H2
- [x] API REST
- [x] Cobertura de testes unitários

---

## Sprint 2 — Scrapers

- [x] Amazon
- [ ] KaBuM
- [ ] Pichau
- [ ] Terabyte
- [ ] Mercado Livre
- [ ] Glacon

---

## Sprint 3 — Notificações

- [ ] Discord
- [ ] Telegram
- [ ] E-mail
- [ ] Push Notification

---

## Sprint 4 — Histórico

- [ ] Histórico de preços
- [ ] Dashboard Web
- [ ] Docker
- [ ] Testcontainers
- [ ] GitHub Actions

---

## Sprint 5 — Monetização

- [ ] Amazon Associates
- [ ] Amazon Product Advertising API
- [ ] Geração automática de links de afiliado
- [ ] Cache de links
- [ ] Estatísticas de cliques

# Fluxo da aplicação

```text
                 Scheduler
                     │
                     ▼
            PriceSearchUseCase
                     │
                     ▼
            PriceSearchService
                     │
         ┌───────────┴───────────┐
         ▼                       ▼
 PriceTargetProvider     StoreScrapers
         │                       │
         └───────────┬───────────┘
                     ▼
             NotificationChannel
```

---

# Pesquisa de produtos

A API disponibiliza um endpoint para pesquisa de produtos.

```http
GET /api/products/search?query=Ryzen 7 5700X
```

Exemplo de resposta:

```json
[
  {
    "name": "AMD Ryzen 7 5700X",
    "price": "R$ 999,90",
    "store": "AMAZON",
    "url": "https://amazon.com.br/..."
  }
]
```

---

# Banco de dados

Atualmente o projeto utiliza **H2 Database** em modo arquivo.

```text
jdbc:h2:file:./database/pricesentinel
```

Os produtos monitorados são persistidos automaticamente.

---

# Testes

O projeto possui cobertura de testes unitários para:

- Value Objects
- Services
- Use Cases
- Controllers
- Mappers
- Providers
- Scrapers
- Scheduler

---

# Próximos objetivos

- Comparação inteligente de produtos iguais entre lojas
- Histórico de preços
- Gráfico de evolução
- Notificações em tempo real
- Dashboard Web
- Containerização
- Deploy gratuito

---

# Autor

**Leandro Gomides**

Projeto desenvolvido para estudo de Arquitetura Hexagonal, DDD, Clean Code, integrações HTTP, Web Scraping e monitoramento inteligente de preços.
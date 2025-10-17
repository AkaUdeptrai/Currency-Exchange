# Currency Exchange Client-Server Application

A simple Client-Server application for real-time currency conversion using Java Socket and Docker.

## Features
- Server listens for client requests via TCP socket
- Fetches real-time exchange rates from an external API
- Client sends currency conversion requests
- Fully containerized using Docker Compose

## Project Structure
```
currency-exchange-server/
    ├── Server/
    │   ├── CurrencyServer.java
    │   │── Dockerfile
    │   └── ExchangeRateAPI.java
    └── docker-compose.yml

currency-exchange-client/
    ├── client/
    │   ├── CurrencyClient.java
    │   └── Dockerfile
    ├── .env
    └── docker-compose.yml

```

## How to Run

### Using Docker Compose (Recommended)
```bash
cd currency-exchange-server
docker compose up -d


cd currency-exchange-client
docker compose run --rm currency-client
```


## Usage
When the client starts, enter commands in the following format:
```
FROM_CURRENCY TO_CURRENCY AMOUNT
```

Examples:
```
USD VND 100
EUR USD 50
JPY VND 1000
GBP THB 200
```

Type `exit` to disconnect from the server.

## Common Currency Codes
- **USD**: United States Dollar
- **EUR**: Euro
- **GBP**: British Pound
- **VND**: Vietnamese Dong
- **JPY**: Japanese Yen
- **CNY**: Chinese Yuan
- **KRW**: South Korean Won
- **THB**: Thai Baht
- **SGD**: Singapore Dollar

## Exchange Rate API
- ExchangeRate-API (Free tier): https://www.exchangerate-api.com/

# Load Tester

A lightweight Java-based HTTP load testing CLI for generating concurrent
traffic against an HTTP endpoint and collecting performance metrics.

## Features

- Fixed concurrent users
- Configurable test duration
- HTTP GET and POST requests
- Concurrent request execution
- Custom HTTP headers
- Authentication header support
- Request body support
- Request body file support
- Configurable request timeout
- Throughput measurement
- Average latency
- Minimum and maximum latency
- P50, P95 and P99 latency
- HTTP status code distribution
- Connection error reporting
- Timeout reporting
- DNS error reporting
- Client and server error reporting
- CLI argument validation
- Executable JAR distribution

## Requirements

- Java 21 or later

Check your Java installation:

```bash
java -version
```

## Download

The latest release provides a ready-to-run executable JAR.
**[Download Load Tester v1.1.0](https://github.com/19Shreyansh/Load_Tester/releases/download/v1.1.0/load_tester-1.1.0.jar)**

You do not need Maven or the source code to run the released JAR.

## Quick Start
After downloading `load_tester-1.1.0.jar`, run a load test against an HTTP endpoint:

## Windows PowerShell
```powershell
java -jar load_tester-1.1.0.jar --url "https://example.com" --method GET --users 10 --duration 30
```

## Linux/macOS
```bash
java -jar load_tester-1.1.0.jar \
  --url "https://example.com" \
  --method GET \
  --users 10 \
  --duration 30
```

## POST Request
For POST requests, provide the request body using `--body` or `--body-file`.
```bash
java -jar load_tester-1.1.0.jar \
--url "https://example.com/api/users" \
--method POST \
--header "Content-Type: application/json" \
--body-file request.json \
--users 10 \
--duration 30
```
Example `request.json:`

```
{   
    "name": "Load Tester",
    "email": "test@example.com"
}
```

## Authtication Headers
Authentication and other custom HTTP headers can be supplied using `--header`.
```bash
java -jar load_tester-1.1.0.jar \
  --url "https://example.com/api/users" \
  --method GET \
  --header "Authorization: Bearer YOUR_TOKEN" \
  --users 10 \
  --duration 30
```
Multiple headers can be supplied by repeating `--header`:
```bash
java -jar load_tester-1.1.0.jar \
  --url "https://example.com/api/users" \
  --method GET \
  --header "Authorization: Bearer YOUR_TOKEN" \
  --header "X-API-Key: YOUR_API_KEY" \
  --users 10 \
  --duration 30
  ```

## Request Body
For a smaller request bodies,use `--body`:
```bash
java -jar load_tester-1.1.0.jar \
  --url "https://example.com/api/users" \
  --method POST \ 
  --header "Content-Type: application/json" \ 
  --body '{"name":"Load Tester"}' \ 
  --users 10 \ 
  --duration 30
```
For larger or more complex bodies, use `--body-file`:
```bash
java -jar load_tester-1.1.0.jar \
  --url "https://example.com/api/users" \
  --method POST \
  --header "Content-Type: application/json" \
  --body-file request.json \
  --users 10 \
  --duration 30
  ```

## Request Timeout

The default request timeout is 30 seconds.

It can be changed using `--timeout`:
```bash
java -jar load_tester-1.1.0.jar \
  --url "https://example.com" \
  --method GET \
  --users 10 \
  --duration 30 \
  --timeout 20
  ```

## CLI Options
- --url <url>             Target URL  
- --method <GET|POST>     HTTP request method
- --users <number>        Number of concurrent users
- --duration <seconds>    Test duration
- --timeout <seconds>     Request timeout (default: 30)
- --body <body>           Request body for POST
- --body-file <file>      Read POST body from file
- --header <name:value>   HTTP request header (repeatable)
- --version               Show version
- --help, -h              Show this help message
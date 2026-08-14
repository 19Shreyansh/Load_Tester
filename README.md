# Load Tester

A lightweight Java-based HTTP load testing CLI for generating concurrent
traffic against an HTTP endpoint and collecting performance metrics.

## Features

- Fixed concurrent users
- Configurable test duration
- HTTP GET requests
- Concurrent request execution
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
Download:
`load_tester-1.0.0.jar`

You do not need Maven or the source code to run the released JAR.

Download the release JAR and run:
```bash
java -jar load_tester-1.0.0.jar \
  --url "https://example.com" \
  --users 10 \
  --duration 30

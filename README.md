
# Cron Expression Parser

A Java command-line application that parses standard five-field cron expressions and expands each field into its individual values.

## Requirements

- Java 17
- Maven 3.8 or later

## Build the Project

```bash
mvn clean package
```

## Run the Application

```bash
java -jar target/cron-expression-parser-1.0-SNAPSHOT.jar "*/15 0 1,15 * 1-5 /usr/bin/find"
```

## Example

Input:

```text
*/15 0 1,15 * 1-5 /usr/bin/find
```

Output:

```text
minute        0 15 30 45
hour          0
day of month  1 15
month         1 2 3 4 5 6 7 8 9 10 11 12
day of week   1 2 3 4 5
command       /usr/bin/find
```

## Supported Expressions

- `*` - All allowed values
- `*/n` - Step values
- `1,5,10` - Comma-separated values
- `1-5` - Ranges
- `1-10/2` - Ranges with steps
- Specific numeric values

## Field Ranges

| Field | Allowed values |
|---|---|
| Minute | 0-59 |
| Hour | 0-23 |
| Day of month | 1-31 |
| Month | 1-12 |
| Day of week | 0-7 |

## Assumptions and Limitations

- The input contains exactly five cron time fields followed by a command.
- Only numeric values are supported.
- Month and weekday names are not supported.
- Special cron strings such as `@yearly` are not supported.
- Step values must be positive.
- Range start must not exceed range end.
- Values outside the allowed field range are rejected.
- Day-of-week values 0 and 7 are both accepted as numeric values; no special normalization is performed.
- Timezone and execution scheduling are not handled. This application only parses expressions.

## Run Tests

```bash
mvn test
```

## Technologies

- Java 17
- Maven
- JUnit 5

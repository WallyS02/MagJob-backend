FROM eclipse-temurin:17.0.6_10-jre

LABEL org.opencontainers.image.title="magjob-backend"
LABEL org.opencontainers.image.authors="Michał (Deryl) Konieczny, Szymon (stanczjo) Małecki, Sebastian (WallyS) Kutny"
LABEL org.opencontainers.image.source="https://github.com/KeepIt-Up/MagJob"
LABEL org.opencontainers.image.url="https://github.com/KeepIt-Up/MagJob"
LABEL org.opencontainers.image.vendor="KeepIt-Up"
LABEL org.opencontainers.image.version="0.0.1"
LABEL org.opencontainers.image.description="MagJob backend"
#LABEL org.opencontainers.image.licenses="MIT"

LABEL build_version=""
LABEL maintainer=""

ENV VERSION="0.0.1"

ENV SERVER_PORT=8080

#ENV SPRING_DATASOURCE_URL=jdbc:h2:mem:magjob-backend
#ENV SPRING_DATASOURCE_DRIVERCLASSNAME=org.h2.Driver
#ENV SPRING_DATASOURCE_USERNAME=admin
#ENV SPRING_DATASOURCE_PASSWORD=password

#ENV SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.H2Dialect
#ENV SPRING_JPA_GENERATE_DDL=true
#ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update

#ENV SPRING_H2_CONSOLE_enabled=true

ENV SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/postgres
ENV SPRING_DATASOURCE_USERNAME=admin
ENV SPRING_DATASOURCE_PASSWORD=admin

ENV SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.PostgreSQLDialect
ENV SPRING_DATASOURCE_DRIVERCLASSNAME=org.postgresql.Driver
ENV SPRING_JPA_GENERATE_DDL=true
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update

EXPOSE 8080

COPY target/magjob-backend-${VERSION}.jar /opt/magjob-backend/magjob-backend.jar

CMD ["java", "-jar", "/opt/magjob-backend/magjob-backend.jar"]

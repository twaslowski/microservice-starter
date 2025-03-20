# microservice-starter

This is my personal starter kit when I create new microservices. It includes:

**Web Capabilities**

- Sample Controllers for GET, POST, PUT and DELETE requests
- Spring Security
- A Kubernetes Service
- Ingress configuration
- MockMVC testing

**Database Capabilities**

- Creation of a PostgreSQL database via the `bitnami/postgresql` chart
- JPA repositories
- Flyway

**Operational Capabilities**

- A Helm chart for deploying the core application
- Service-level Prometheus scraping annotations
- GitHub Actions workflows for validating PRs and deploying
- A local test setup

## Running

This application comes with two ways of being run locally, as well as two profiles for deployment,
dev and production.

### Running locally with docker-compose

### Running locally with minikube

### Deploying to a Cluster

## Testing
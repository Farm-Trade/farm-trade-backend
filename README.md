### CI description
- docker-publish: Create docker image after push into `develp` image location `ghcr.io/farm-trade/farm-trade-backend`
- maven-publish: Create package after push into `develop` package location `https://maven.pkg.github.com/Farm-Trade/farm-trade-backend`
- pull-request-check: Run tests, create docker image after creating pull request into `develp` image location `ghcr.io/farm-trade/farm-trade-backend:${sha}`
### App starting from test side
Execute `./scripts/docker-login.sh` enter token and username then run `docker-compose up -d`
### Swagger
<domain>/swagger-ui.html

### CI description
- docker-publish: Create docker image after push into `develp` image location `ghcr.io/farm-trade/farm-trade-backend`
- maven-publish: Create package after push into `develop` package location `https://maven.pkg.github.com/Farm-Trade/farm-trade-backend`
- pull-request-check: Run tests, create docker image after creating pull request into `develp` image location `ghcr.io/farm-trade/farm-trade-backend:${branch-name}`
### App starting
Use `docker login -u ${username} -p ${password/token} ghcr.io` then run `docker-compose up -d`

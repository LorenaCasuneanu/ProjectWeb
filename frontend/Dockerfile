### STAGE 1: Build ###
FROM node:14-alpine AS node
WORKDIR /usr/src/app
COPY . .
RUN npm install
COPY . .
RUN npm install @auth0/auth0-angular
RUN npm run ng build -- --prod --output-path=docs
### STAGE 2: Run ###
FROM nginx:1.17.1-alpine
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=node /usr/src/app/docs /usr/share/nginx/html

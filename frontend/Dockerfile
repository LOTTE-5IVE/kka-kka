FROM node:18.10.0-alpine

WORKDIR /usr/src/app

COPY package.json ./
COPY package-lock.json ./
# COPY .env ./

RUN npm i

COPY . .

EXPOSE 3000

CMD [ "npm","run","dev" ]

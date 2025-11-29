FROM clojure:temurin-25-lein-alpine

WORKDIR /transactor

COPY . .

ADD https://datomic-pro-downloads.s3.amazonaws.com/1.0.7469/datomic-pro-1.0.7469.zip .

RUN apk add unzip && unzip datomic-pro-1.0.7469.zip

WORKDIR /transactor/datomic-pro-1.0.7469

EXPOSE 4334 4335

ENTRYPOINT ["bin/transactor", "/transactor/transactor-config.properties"]

build:
	lein uberjar

start: build
	java -jar target/uberjar/clotho.jar

start/dev:
	lein run
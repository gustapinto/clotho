build:
	lein uberjar

start: build
	java -jar target/uberjar/knit.jar

start/dev:
	lein run
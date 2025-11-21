build:
	lein uberjar

run/jar: build
	java -jar target/uberjar/clotho.jar

run:
	lein run

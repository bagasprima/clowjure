VERSION=0.1.0
APP_NAME=clow
JAR_FILE=target/uberjar/$(APP_NAME)-$(VERSION).jar
JAR_STANDALONE_FILE=target/uberjar/$(APP_NAME)-$(VERSION)-standalone.jar

.PHONY: build run

build:
	lein uberjar

run: build
	java -jar $(JAR_STANDALONE_FILE)

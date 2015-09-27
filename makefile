default:
	javac src/actors/*.java src/world/*.java src/game/*.java src/utils/*.java -d bin
clean:
	rm bin/actors/*.class bin/world/*.class bin/game/*.class bin/utils/*.class
jar: default
	@jar cmf manifest.txt robot.jar res -C bin game -C bin world -C bin utils -C bin actors && java -jar robot.jar

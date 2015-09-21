default:
	javac src/actors/*.java src/world/*.java src/game/*.java  src/utils/*.java -d bin && java -classpath bin game.Game
clean:
	rm bin/actors/*.class bin/world/*.class bin/game/*.class bin/utils/*.class
jar:
	jar cvfm robot.jar src/manifest.txt bin/actors/*.class bin/world/*.class bin/game/*.class bin/utils/*.class

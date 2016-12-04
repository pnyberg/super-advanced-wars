run: compile war

compile:
	javac ./buildings/*.java
	javac ./handlers/*.java
	javac ./heroes/*.java
	javac ./menus/*.java
	javac ./point/*.java
	javac ./units/*.java
	javac *.java

war:
	java SuperAdvanceWars

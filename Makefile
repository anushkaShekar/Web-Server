JFLAGS = -g
JC = javac
TARGET = server

all:
	$(JC) $(JFLAGS) *.java

run:
	java $(TARGET).Server -document_root "/home/moazzeni/webserver_files/files" -port 8888

clean:
	rm -f *.class

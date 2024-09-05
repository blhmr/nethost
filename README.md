# Nethost

Send your files in a secure way

## Preview

This terminal software is written in Java 8, the classes are in `bin/` folder and it depends on JeroMQ in `lib/` folder.
To compile them into a `.jar` file run this:
```bash
jar cfm nethost.jar MANIFEST.MF -C bin/ .
```
It will produce a portable `nethost.jar` file.
To run it, make sure you have Java 8 installed, then run:
```bash
# To receive a file
java -jar nethost.jar receive

# To send a file
java -jar nethost.jar send
```

## Disclaimer
- I haven't used Maven or any build tool because the project is too small.
- This is my first Java project, so keep your expectations low, I am still learning.

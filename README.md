# Gps-Tracker
Gps

**This is my first full-stack project.**

I has made it within a university course. The main purpose was to learn both how to develop projects like this and new technologies.

**The used technologies is Java + Spring Boot for backend, Android (Java) for frontend and MySql for database management.**

## Server
The server-side was implemented in Intellij IDE.
There is developed methods for register and login.
The application offer different functionalities such as: saving a new position on the database or operation on the existing position in the database like: delete, update, get. 
Also, the app will provide all position to logged user in a certain interval time.
It will be running in a servlet container: Tomcat.

## Client
The client-side was implemented in Android Studio IDE. 
The user can logging in with an existing account or registering with a new account in the app. 
It will record automatically the current user position (longitude & latitude) and it sent to the database (periodically - for example once every 3 minutes). Once this function is activated, there will be show one button that can stop that.
The app has one button for record position manually.

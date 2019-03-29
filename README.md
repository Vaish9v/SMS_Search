# SMS_Search

### Problem Definition
An android application will scan SMS text box and return all the SMS with pattern. The application will also provide total time consumed. A graphical chart will be shown to user to inform frequency of SMS text exchanged.Pattern will be accepted as input.

### Application Description
The application will search the SMS according to the input given by the user and will display the matched result. The user will decide the parameter for search like name, keyword, after, before or in between dates. According to the selected parameter the app will find all the SMS which satisfy the user query. Also the app will display time taken for search. The application will also represent graphically using pie chart, the number of SMS sent and recieved.

### Technology stack

- Java JDK+ JRE Environment Version 1.8 for backend development
- XML for UI development
- MPAndroid Chart for graphical representation of pie chart

### Java Classes

- MainActivity.java : Contains code for splash screen which runs untill SMS are not fetched.
- TabFragment1.java : Includes search dailog, SMS display, response time calculations.
- TabFragment2.java : Includes graphical representation of SMS frequency using pie chart.
- KMP.java          : Includes KMP search algorithm for searching.

Note : All the custom shape files are present in drawable folder in res.

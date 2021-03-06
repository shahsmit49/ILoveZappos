## ILoveZappos

This **Kotlin native android** application is built with some creative and interactive open source libraries on top of the android framework.
The app provides a platform to visualize trends in the price of ***bitcoin***, latest bids and asks, etc.
* APK Link - https://drive.google.com/open?id=16wjNGCQ6MM-aNKjwaBiaGOMfunmIQ8uR

# Detailed description of the features:

* Views displaying order book information of bids and asks
  Information retrieved using the publically available bitstamp api : https://www.bitstamp.net/api/v2/transactions/btcusd/ and 
  displayed in a **Live-data table** using *Recycler-views*.
  
  <img src = "img%20(6).jpeg" width="300" height="400"/>
  
* Contains a graph of the recent transaction history of bitcoins
  Information retrieved using the publically available bitstamp api : https://www.bitstamp.net/api/v2/order_book/btcusd/ and displayed 
  using a **line graph** showing the price history over time.
  
  <img src = "img%20(4).jpeg" width="300" height="400"/>

* Takes price input from the user and store it using **Shared-preferences** in the local storage. 

  <img src = "img%20(5).jpeg" width="300" height="400"/>

* The ability to create a price alert notification when the current price is below a user selected value 
  Information retrieved using the publically available bitstamp api : https://www.bitstamp.net/api/v2/ticker_hour/btcusd/ and is hit 
  every hour in the background using **WorkManager** android api. if the current bitcoin price has fallen below a specified user input, 
  then send notification to user. When clicked on notification opens the application.
  
  <img src = "img%20(1).jpeg" width="300" height="400"/>

  
# Open source libraries used: 
  
  * Retrofit to handle REST requests: https://square.github.io/retrofit/
  * mpAndroidChart to create the graph: https://github.com/PhilJay/MPAndroidChart
  
# Layouts:
  
  * Recycler views
  * SwipeRefreshLayout
  
# Android libraries and tools:
  
  * Work Manager - new job management system in Jetpack, incorporates the features of *Firebase Job Dispatcher (FJD)* and 
    Android’s JobScheduler to provide a consistent job scheduling service back to api level 14 while leveraging JobScheduler on newer 
    devices.
  * Shared preferences
  
  

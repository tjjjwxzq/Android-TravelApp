# SGFun - An Android Travel App
50.001 Project 1 - Build an Android Travel App!
----

## The Contributors 
1. Christabella Irwanto 
2. Tan Jun Qi 
3. Shaun Chiang 
4. Samuel Lim 
5. Lin Junsheng 
6. Hazel Lau 

## App description

![Image of App Icon](https://github.com/tjjjwxzq/Android-TravelApp/blob/master/TravelApp_tjjjwxzq/app/src/main/res/drawable/launcher_icon_highres.png)

SGFun is an Android Travel App for Singapore that is able to do daily itinerary planning, locate popular tourist attractions such as Resorts World Sentosa, and manage your budget to keep track of your expenses - all in one app! The app also features a simple and clean UI that is pleasing to the eye, along with convenient features such as autocomplete and robust spell checking. Accurate GPS relying on Maps API allows for quick map results on either map or satellite view, and itinerary planning helps optimise your trips in both time and money. Enjoy our App! :)

## App Details
1) Overview <br />

![Image of opening screen](https://github.com/tjjjwxzq/Android-TravelApp/blob/master/screenshots/resized/photo127526564546062305.jpg)
![Image of nav drawer](https://github.com/tjjjwxzq/Android-TravelApp/blob/master/screenshots/resized/photo127526564546062301.jpg)

The opening screen of our app is the Tourist Attraction Locator screen, and tapping the top left corner opens the side drawer, allowing access to the other features of our app, the Itinerary Planner and Budget Manager.

2) Tourist Attraction Locator <br />

![Image of autocomplete](https://github.com/tjjjwxzq/Android-TravelApp/blob/master/screenshots/resized/photo127526564546062293.jpg)
![Image of map view](https://github.com/tjjjwxzq/Android-TravelApp/blob/master/screenshots/resized/photo127526564546062300.jpg) 
![Image of satellite view](https://github.com/tjjjwxzq/Android-TravelApp/blob/master/screenshots/resized/photo127526564546062306.jpg)
![Image of itinerary creation](https://github.com/tjjjwxzq/Android-TravelApp/blob/master/screenshots/resized/photo127526564546062292.jpg) 


By using the searchbar, the user can easily find the tourist attractions they want with AutoComplete and Robust Spell Checking implemented. The map also shows the address and postal code of the location, and can move around the map to look for landmarks or nearby train stations. By clicking the "+" Icon, the user can then add different locations to their Itinerary, and name their Itinerary according to their preferences (e.g. "Tomorrow's Itinerary"). Multiple Itineraries are supported, so so the user can plan their trips few days in advance.

3) Itinerary Planner <br />

![Image of itinerary list](https://github.com/tjjjwxzq/Android-TravelApp/blob/master/screenshots/resized/photo127526564546062298.jpg) 
![Image of itinerary planner](https://github.com/tjjjwxzq/Android-TravelApp/blob/master/screenshots/resized/photo127526564546062295.jpg)
![Image of planned itinerary](https://github.com/tjjjwxzq/Android-TravelApp/blob/master/screenshots/resized/photo127526564546062290.jpg)
![Image of drawn polyline](https://github.com/tjjjwxzq/Android-TravelApp/blob/master/screenshots/resized/photo127526564546062308.jpg)

With that, we come to the Itinerary Planner, where the user can select an Itinerary and specify a budget. The App will then calculate the fastest and cheapest way based on the budget specified, and draw it on the map. This includes deciding the order of seeing the attractions, and starts and ends at your original position (for example, your hotel). Our optimisation algorithm is fast and effective, and will return a quick result even if you wanted to visit 100 attractions at once (we wouldn't recommend doing that though)!

4) Budget Manager <br />

![Image of budget manager](https://github.com/tjjjwxzq/Android-TravelApp/blob/master/screenshots/resized/photo127526564546062307.jpg)
![Image of adding moneys](https://github.com/tjjjwxzq/Android-TravelApp/blob/master/screenshots/resized/photo127526564546062303.jpg)
![Image of editing stuff](https://github.com/tjjjwxzq/Android-TravelApp/blob/master/screenshots/resized/photo127526564546062302.jpg)

Lastly, we have a budget manager that allows the user to keep track of their expenses from day-to-day. The user can add their budget for the day, and by adding their daily expenses, can observe at a glance the balance left for the day. These expenses can also be named, allowing the user to identify which daily expense (for example, transportation) is too much, and make prudent adjustments to their routine.

## In-depth: Features
1)Itinerary Planning <br />
1.1 Exhaustive Search <br />
The itinerary planning involved a recursive algorithm that generates all possible permutations of the given list of attractions. For every candidate order of length n, it finds the one that takes the least time by ranking the time average over all three modes of transport. 

1.2 Fast Approximate Solver <br />
Each step along the path, the solver will choose the destination which minimizes cost*time average over all three modes of transport.

1.3 Choosing modes of transport along each edge of the path <br />
Lastly, the mode of transport chosen will be first determined through the average cost per edge, then through the cost of taking a taxi for all edges. While the total cost exceeds the budget, find the shortest edge (in terms of average cost*time), and check if walking time is less than 15min. If yes, change to walking, else, change to bus, and check whether budget is exceeded. If still exceeded, find the next shortest edge, and repeat until one is within budget.

2) Tourist Attraction Locator <br />
2.1 Robust Spell Checker <br />
Our Robust Spell Checker makes use of FREJ (Fuzzy Regular Expressions for Java), an external Java Library that allows us to quickly make Fuzzy Regular Expressions for the different tourist attractions. These operate on the lines of edit distance and regular expression, and create a match when the input is "close enough" (for example, sentozza matches "Sentosa", but sentozzzzszaaa does not create a match). Using this library makes possible expansion of the number of tourist attractions much easier and quicker, for future versions of the App.

2.2 AutoComplete <br />
The Autocomplete function makes use of Google API to predict and list possible tourist locations given an input string. The resulting input from the user is then passed to the maps Fragment.

2.3 Maps Fragment <br />
The Maps Fragment then centers on the passed input. For the itinerary, the app takes in the lowest and highest latitude and longitude of the itinerary's locations; then frames the camera to fit these latitudes and longitudes. Our Maps fragment uses Maps API, and toggles between satellite view and normal view with a toggle button. The itinerary planner, after implementing the algorithm, passes the route and the map fragment is updated using update(). Coloured polylines are drawn using polylineoptions, representing different modes of travel. 


3) Budget Manager <br />
3.1 Acquiring Costs from Itinerary Planner <br />
The budget manager first acquires the transport cost from each itinerary and takes that into account. 

3.2 Editable Inputs <br /> 
The budget that the user has, as well as each item in the expenditure list can be edited if required. The balance that the user has left, as well as the total expenditure will change accordingly. 

4) More detail <br />
More detail can be found in our source code with docstrings. 

## Further Implementation
Further Implementation can be done to include all the tourist attractions in Singapore; include more features; and possibly expand to other countries. These changes can be reasonably easy to add, given the modular nature of our App. 

## Acknowledgements
As this is a group project for SUTD's (Singapore University of Technology and Design) ISTD Module 50.001, we'd like to thank our teachers (Profs Ngai-Man Cheung, Andrew Yoong, Ng Geok See) for their assistance and teaching. :) 

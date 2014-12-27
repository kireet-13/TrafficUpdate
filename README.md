TrafficUpdate
=============
<b>Strategy and To Do List for CODS 2015 Research Challenge</b>


<b>Objective:-</b> Design an application which can give real insights from the fast flowing streaming data. we need to figure out following things:-<br/>
i)  We need to check whether Facebook or twitter data is providing some relevant info or not.Actually This is the objective part of CODS challenge.<br/>
ii) Which areas are facing severe traffic problems.<br/>
iii) What are the most pressing traffic related problems citizens in different cities are complaining about (e.g. wrong_parking, heavy_vehicles, auto_refusal etc)<br/>
iv) How things have changed after additional traffic management people have been introduced across cities last month and so on.<br/>

<b>Available Resources for Data Collection:- </b>

1)Facebook pages of traffic police : link to those pages are:-<br/>
i)Bangalore - https://www.facebook.com/BangaloreTrafficPolice<br/>
ii)Chennai - https://www.facebook.com/chennaitrafficpolice<br/>
iii)Kolkata - https://www.facebook.com/KolkataTrafficPolice<br/>
iv)NewDelhi- https://www.facebook.com/pages/Delhi-TrafficPolice/117817371573308 <br/>


2)Twitter pages and keyword search in twitter.<br/>
3)Traffline. (http://www.traffline.com/)<br/>
4)Timetable of Trains:- API is available for that.( http://api.erail.in/stations/?key=##)<br/>
5)From DTC buses and traffic around ISBT.<br/>
6)From Delhi metro ridership data if available.<br/>
7)Traffic Information from various FM channels if there is any way to get it.<br/>
<b>Note:- additional resources can be added.</b><br/>



<b>TO DO list :-</b>

1)Get data from all the publicly available resources like twitter, facebook.
Make a list of what features are available and list them.
What other information can be extracted from this list?<br/>
2). Start with mapping.
Get arrival and departure time of trains at major stations.
Use NLP to get Location from whatever data we have.<br/>

<b>Keywords & Hashtags Used in Twitter for Delhi:-</b>

i)Delhi traffic<br/> 
ii)https://twitter.com/TrafflineDEL(TrafflineDEL)<br/> 
iii)dtptraffic<br/>
iv)TrafficDEL<br/>
v)DelhiTrafficPol<br/>
vi)avoid traffic delhi<br/>
vii)Breakdown delhi traffic<br/>
viii)#trafficblues<br/>
ix)#traffline<br/>
x)#StreetSmartWithTraffline<br/>
xi)#delhi #TRAFFICALERT<br/>
xii)#delhi #traffic<br/>
xiii)#delhi traffic<br/>

<b>Keywords & Hashtags Used in Twitter for Mumbai:-</b>
i)Mumbai traffic<br/>
ii)@TrafflineMUM<br/>
iii)TrafficMum<br/>
iv)MumbaiTrafficPol<br/>
v)avoid traffic Mumbai<br/>
vi)Breakdown Mumbai traffic<br/>
vii)@smart_mumbaikar<br/>
viii)@TrafficBOM<br/>
ix)#StreetSmartWithTraffline mumbai<ix>
x)#mumbai #TRAFFICALERT<br/> 
xi)#mumbai #TRAFFIC<br/>

<b>New Additions:-</b>

i) As whatsApp and Google hangout are not our Options anymore, we are using YO app for getting Coordinates of user. Working of Yo app is simple, with only a single touch only a YO is received and with a double touch we will get Location of user as well.<br/>
We have already configured Yo app for our purpose, Now We can get Users location whenever he sends a Yo for index we have created called TRAFFICUPDATE.<br/>
Now the question is what should we return to the user ?<br/>
One thing that I have in mind is that whenever a single tab we can interpret it as “User is asking for traffic information so we can return user a link that contains all the locations he should avoid”.
and whenever user double touches then we will get the location and can map it to show traffic jam.<br/>

ii) For mumbai and bangalore google transit feed data is available and we have already used that data to map bus routes also we have collected around 700-800 points which are at a distance of 0.75-1.25km . As soon as we get the server access we can run our scripts to collect traffic information between these points. But we are still facing some problems to map bus routes of delhi as google transit feed data is not available and google geocode api is not giving desired results.  So now we need to identify those places for which GEOCODE api is not working and will have to map them manually.<br/>

iii)  We have train data and frequencies of trains at all major stations of new delhi. We need to figure out what we can do with that data.<br/>

iv) We should put all our code on github. I will create a new repository in github and add everyone as contributor so that all of us can access the code and can put their work as well.<br/>

v) Sudeep bhaiya has already proposed an algorithm We can spent some time discussing  that 
as well, To me this approach looks promising but to apply that approach we need data of at least one week.<br/>


<b>Proposed Methodology:-</b>

i)Extract 10 days of data from the publicly available sources which list places where a traffic jam has occurred. <br/>
ii)From the extracted data perform a clustering process to get the places and convert them into lat, long. Mark these places on the map and locate nearby bus stations. Also apply the process to sub-clusters so found till you can no longer get new/distinct places.<br/>
iii)For all permitted directions for each identified bus station, locate more bus stops with help of bing api where congestion is detected upto a radius of say 10Km.<br/>
iv)For all places where congestion is detected, recursively apply the approach from step 3.<br/>

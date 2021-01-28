Conversion notes:

* Docs to Markdown version 1.0β29
* **Thu Jan 28 2021 07:21:38 GMT-0800** (PST)
* Source doc: FAQ (Title: Mobile app for BLE and WiFi mapping and testing for indoor tracking)
----->


Project scope:

To develop a mobile app and associated backend to keep Wifi/BLE data (e.g. MAC addresses) and location information tag to the WiFi/BLE data.



1. Develop function on mobile app that can collect Wifi/BLE data
2. Develop function on mobile app that can test the collected Wifi/BLE data by find the locations of the phone
3. A backend database to store the data collected in (a) and to retrieve the data collected when there testing is needed to be done on another mobile device.
4. Accuracy of tracking result within 5m

End result:



1. Mobile app that can enter Mapping mode and Testing mode
2. In Mapping mode, user will start at a location on the areas to be mapped. A floor plan of the area to be mapped shall be uploaded first to the backend server, and download to the mobile app. 
3. To start the mapping, the user will enable scanning for Wifi/BLE at the starting point and then indicate the actual location the user is doing the mapping on the floor plan.
4. Once the app has stored all the data (on the phone), the user will move in a straight line to another location on the floor plan (e.g. move in a straight line and stop after 5m). The user will then indicate the actual location on the floor plan and click on a button to store the WiFi/BLE data and the actual location. This process is repeated many times until the entire floor has been mapped.
5. Once the mapping is done, the user can then upload the data to the backend for storage.
6. In testing mode, the user will just move around at the location, the mobile device will constantly scan the surrounding wifi/BLE and show the location triangulated from the past data stored earlier.

**Q1: **It writes that “Android mobile app that can map and deliver accuracy of detection within 5m for building with 2 floors and area size of at least 10,000 sq ft”, where should we find this kind of building as there are no 2-floor buildings inside SUTD as far as I know?

A1: Look for any multi-storey buildings, and for this project, need to only use 2 of the floors (top/bottom of each other) as the test area. Also, the floors have to have public WiFis.

**Q2: **Need the app work for any building so long as the user provides the floor plan and other necessary information (BLEsignal, WAL maybe) using the app (so that there needs to be a setting page inside the app) ,or the app is used only for the dedicated building?

A2: The Android app needs to be developed as part of this project. Once we do the first WiFi/BLE mapping, we’ll be able to mark the mapped data to the location. As the WiFi/BLE information is unique (not all the time), then the app, while in testing mode, will be able to know which location/floors that it is on. There shall be least 2 operational modes: the BLE/WiFi mapping model, and the test mode.

**Q3:** I would like to ask about the information on the wireless APs (for example: 802.11-2016 FTM, signal strength and coverage, number of APs) and any restrictions on Android phone model (bluetooth devices). And is it only restricted to only BLE and WiFi mapping (are we graded if we implement things that go beyond these)

A3: In later versions of Android, the OS will throttle the scanning of WiFi. Only in version 8 and before that there is no throttling (need to check this).

For WiFi scanning,  need to only use those “fixed” APs, and not hotspots (enabled on phones).

**Follow up: **Hi Prof/ Mr. Client: Thanks for your prompt reply! But you have not answered the last question: is it only restricted to BLE and WiFi mapping? (e.g. RFID)

A3: Yes, only BLE and WiFi.

**Q4: **Would there be a budget allocated should we wish to buy the corresponding WAP (or wifi/bluetooth antenna)?

A4: Have to find location with Wifi APs installed. For BLE, we can provide the beacons.

**Q5:** If we are using any multi-storey buildings, how are we going to fix the floor plan? Are we going to use a fixed specific floor plan first? Or would this be implemented in a more “general” approach?

A5: Need to have the floor plan before doing BLE/Wifi mapping. The floor needs to be specific to the location (not generic).

**Q6: **Can you provide us with several useful links? (like a summary of recent efficient algorithms of indoor tracking/ Several fundamental papers etc) 

[https://www.researchgate.net/publication/237046557_WiFi_Positioning_A_Survey](https://www.researchgate.net/publication/237046557_WiFi_Positioning_A_Survey)

[https://www.researchgate.net/publication/232652924_WIFI-based_indoor_positioning_system](https://www.researchgate.net/publication/232652924_WIFI-based_indoor_positioning_system)

[https://ieeexplore.ieee.org/document/8692423](https://ieeexplore.ieee.org/document/8692423)

[https://arxiv.org/pdf/1709.01015.pdf](https://arxiv.org/pdf/1709.01015.pdf)

[https://www.hindawi.com/journals/misy/2016/2083094/](https://www.hindawi.com/journals/misy/2016/2083094/)

[https://www.researchgate.net/publication/316613991_Indoor_Positioning_System_using_Bluetooth_Low_Energy](https://www.researchgate.net/publication/316613991_Indoor_Positioning_System_using_Bluetooth_Low_Energy)

[https://link.springer.com/chapter/10.1007/978-3-319-31165-4_18](https://link.springer.com/chapter/10.1007/978-3-319-31165-4_18)

**Q7:** Could I ask if we will know the wifi AP locations on the floor plan beforehand and pre-configure the app parameters, or would we be required to create an interface for localisation?

A7: Need to find out if the location has Wifi network. Use the phone wifi scanning to ascertain if the location wifi. If there are many wifi (e.g. more than 5) then that will be a good location to do the trial.

**Q8: **Could we understand more about the specific use case of this app? Like what is the context it will be used in, what is its purpose, what forms of indoor tracking (e.g. humans, wireless/IoT equipment). If there is no specific purpose, would we be allowed to rescope it to our own use case/scenarios?

A8: For asset tracking and people tracking.  For asset tracking, we’ll fix a tracker on the asset, and when the asset stopped moving, then the tracker will send out the nearby Wifi APs (min. 2) and this is able to get the location.  For people tracking, we can embed the tracking in the phone using API.

**Q9** Will we be faced with a crowded or empty indoor setting? 

A9: Not sure how is this relevant to this project. Can you explain?

**Follow up: **I’m not the one who asked the question but I am thinking that a crowded setting where there is a lot of WIFI might introduce interference in the signals which affect the accuracy

**Q10 **What resources are provided by the client in this project? Eg. API? Database? 

A10: The team is supposed to build the app and define the algorithm to achieve the goal.

There will also be backend website to store all the data collected from the app.

**Q11: **Following up on Q7, the usage of asset tracking and people tracking is very broad. Would it be possible for us to define how this app will be used by the user and for what specific scenario? E.g. firefighting, supply chain logistics, COVID-19

A11: Our aim is to create a product that can be used for all the applications.

**Q12:** Could I ask if the wifi APs are only used for triangulation? Or how else would this information be used by the app?

A12: Pls check the reference papers links provided above.

**Q13:** Is it realistic that a location would have more than 10 wifi APs, wouldn’t the building area have to be very large? 

A13: Yes. Tracking with Wifi/BLE is only useful for large areas. 10 Wifi APs is only a reference, as long as enough Wifi AP to get accuracy of within 5 m.

**Q14:** Following Q7, what kind of data is needed to be stored on the server? Like the travel history of each user so the administrator can view all the history data? Does it mean we need to have an account or special ID for each user, and the server can receive data from multiple users at the same time? 

A14: Only the Wifi/BLE identifier and the locations, not the actual travelling data. The purpose of this project is as stated in the above scope.

**Q15**: How do we verify the accuracy of the WiFi/BLE? Is there some ground truth for reference?

A15: By measuring on site. For his project, we’ll define few locations (depending on floor size) to measure, and then do measurement for these 10 locations to determine the reported location vs the actual locations.

Q16: May i know what’s the difference between mapping and testing mode?

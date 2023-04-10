# Description
Just a small repository illustrating various aspects of mobile app including Code-Architecture, Unit-testing, RoomDB etc

Coding Challenge -
● The code follows Clean architecture practices
● The main libs used are as - Retrofit, ROOM, Coroutines, RxJava, Mockk, Flows , Paging
V3, Navigation etc
● Test cases have been written using mockk
● Support both Portrait and landscape mode
● Two search option -
● The outer search on home page is only for manufacture search option and uses room
db (With initial thoughts to support offline mode in app , later declined the idea with
deadline constraints. )
● The one more search option for searching models .
● The search is performed locally thereby reducing network calls
● The car images are added locally and hence only two images are used across app


Designs -
HomePage : contains all manufacturer list supported with pagination and bottomNavigation

<img width="293" alt="image" src="https://user-images.githubusercontent.com/50236871/157446042-7cf90237-eaa9-4097-8c2e-11f85df40951.png">


User can click on anyItem to navigate to the next Page which is Model Detail Page. which
shows all the models of the manufacturer user clicked before .

<img width="263" alt="image" src="https://user-images.githubusercontent.com/50236871/157446123-8c7d5adf-28a6-4e16-9163-4b08ffdf05fd.png">


User can search the desired model here.

<img width="303" alt="image" src="https://user-images.githubusercontent.com/50236871/157446207-1c44171d-4b60-4f3c-9955-d26f443358e7.png">


The searchPage on home uses room DB for searching and only searches for manufacturers

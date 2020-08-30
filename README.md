# Square employee directory interview challenge

Android project developed by Tyler Turnbull as part of the Square hiring process

## Build Tools & Versions Used
Created with Android studio 4.0 and tested on emulator running api 29

## Your Focus Areas
I divided my time and focus pretty evenly among the areas of the project.

I first focused on creating a concise api client and testing various scenarios with it.

Then built up the UI. The first time sync I experienced was getting different view types and sticky headers
to work with the fast adapter recycler view library. After playing around with it for awhile I reverted
to using the Adapter Delegates library which I have more experience in and between that and finding a great
resource on sticky headers I was able to get past that hurdle.

Lastly, I circled back and reviewed the requirements a final time and noticed that image caching
seemed to be a major concern. I knew that the Picasso library handled image caching but I wasnt sure
at what point it would hit the endpoint again and re-download the data. In my current implementation
here I force Picasso to check the offline cache first before hitting any endpoints. There may be a better
way to handle this though.

## Copied-in code or copied-in dependencies
Most of the code here was written as part of this project, however I did pull in some base classes that
I have setup from a side project. For instance the BaseFragment was something I previously created
to reduce some of the boilerplate when using ViewBinding with Fragments.

One thing that I did use from a post that I found was the StickyHeaderItemDecoration.

## Tablet / phone focus
Phone

## How long you spent on the project
I spent about 8-9 hours over two days. If I had skipped adding sticky headers and letter dividers
to the RecyclerView I could have shaves 1 - 2 hours off. I also spent a considerable amount of time
researching how to control image cache with Picasso.

## Anything else you want us to know.
If this was truly a production development I probably would hold off on using Hilt for dependency injection
and stick with Dagger 2 or try out Koin. With that being said Hilt was much more enjoyable getting going
than Dagger :)

In the event that you find this work to be unsatisfactory I would greatly appreciate any critique,
evaluation, and / or advice that you could provide. Thank you!
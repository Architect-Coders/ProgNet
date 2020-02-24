# ProgNet

ProgNet is a progressive rock mobile application based in Discogs public database, allows user to discover and see the details of new albums. It saves the albums recently heard along with the location where they have been heard for future social network implementations.


## Settings

To run it the user needs to include the apiKeys.properties file in the root directory, with this structure

```
discogsApiKey="XXXXXXXXXXXXX"
discogsApiSecret="XXXXXXXXXXX"

```

## Considerations

* This project uses "Arrow" library to manage the responses of the different data sources in a functional way.

* This project uses functions instead of classes to create the use cases, managing the decouplement with Higher Order Functions that acts like Factory Methods building use cases. In the project have been used types of the functions to ensure interface abstrations without using them, and have been assigned them to typealiases to allow better readability.

* The respositories have a layer of abstraction to provide dependency inversion respect of the use cases, like the data sources, that allows the internal layers become agnostic about the external ones.

* The discover first screen is a random provider of prog rock albums. In this case we only save the data to allow it to be displayed in offline mode in the detail view, but there is no cache response in the first screen because of its random approach, but all data will be stored to be displayed in different detail views.

* The application handles the different organizations of API response data with merges to maintain domain consistency.
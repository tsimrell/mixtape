#Mixtape Appliction
This application is for managing the state of Mixtapes. 
## How to Build
This is a Java Maven project, so the standard way of building applies. 
To manually build this on a terminal, make sure you have Java and Maven installed and they are working.
Once they are, run this command: `mvn package` . This will output a JAR file called `mixtape.jar` in the `.../target/`
directory. 

Also, importing into an IDE such as IntelliJ works just as well. For IntelliJ, simply import as a Maven project and then
use the built in Maven tool bar to build the project there, too. 

## How to Run
An example of the command to run this application is shown here:

`java -jar mixtape.jar /home/user/Desktop/test_mixtape.json /home/user/Desktop/change.json`

`java -jar mixtape.jar` is the command for telling Java to run the JAR file as a JAR. 
## A Note on Extras
There is a directory in the repo that contains the Mixtape base file, labeled `mixtape.json`. This is the same as what
was given in the initial coding exercise site. It also contains the Mixtape change file, labeled `change.json`. The
last thing is `mixtape.jar`, which is a compiled version of the code base. It was built on Ubuntu 18.04 LTS and was
tested on there, but there is no reason why any system with Java installed can't run it (Write once, run anywhere). 
### Arguments
This application accepts 3 arguments, but only the first two are required.
#### Argument 0
Argument 0 is the file path to the Mixtape base JSON file. This file is the base Mixtape file that is gets read in and 
updated. The file itself is not changed by this process. This can be relative if the Mixtape base JSON is near the JAR
file or can be an absolute path.

In the above example, argument 0 is `/home/user/Desktop/test_mixtape.json`  
#### Argument 1
Argument 1 is the file path to the Mixtape change JSON file. This file contains all of the changes you can make to the
Mixtape base output file. The file itself is not changed by this process. This can be relative if the Mixtape base JSON 
is near the JAR file or can be an absolute path.

In the above example, argument 1 is `/home/user/Desktop/change.json`
#### Argument 2 (Optional)
Argument 2 is the name/path to the output file. If this argument is omitted, then the output file will be `output.json` 
and will exist next to the JAR file.

## Change File Overview
````
{
  "new_playlist": [
    {
      "user_id": "2",
      "song_ids": [
        "8",
        "32"
      ]
    }
  ],
  "remove_playlist": ["1"],
  "update_playlist": [
  	{
  		"playlist_id" : "2",
  		"song_ids" : ["32"]
  	}
  ]
}
````

Above is an example Change file. It is in JSON and attempts to be as similar to the base Mixtape JSON as possible. Below
is an explanation on each JSON object and what it does.

Do note that any of the objects can be omitted if an action does not need to be taken. 
### new_playlist
This JSON object allows the user to add a new playlist. The business rules for this action are:
* `user_id` maps to a valid user. 
* `song_ids' contains all valid songs. 

If either of these are broken, the application will end with an error stating that the business rules have been broken.
Do note the lack of a playlist ID here. The application will generate an ID and this will be included in the output JSON
file. This ID will always be the next highest number in the list of playlist IDs. 

### remove_playlist
This JSON object allows the user to remove a playlist. The business rules for this action are:
* The given IDs in the array are valid playlist IDs.
If successful, the playlist that corresponds to the given playlist ID will be removed in the output JSON file. 

### update_playlist
This JSON object allows the user to update by adding an already existing song to an already existing playlist. The
business rules for this action are:
* `playlist_id` must be an ID to a valid playlist. 
* `song_ids` must all be IDs to valid songs. 

## Ideas for Scaling This Application
The first idea is to replace the Mixtape Base JSON with some sort of persistent datastore, such as an Relational DB or
a NoSQL DB. This would alleviate having to read in the Mixtape Base file everytime this application is ran. While there
will be a cost of maintaining a DB, if file sizes get so large that it starts to become an issue, a separate persistence
other than a flat file would really speed things up as there is no more file parsing and we would be able to query for
data instead of having to build maps to aid in random access look ups. This would also allow for the application to
be slimmed down as file IO won't be necessary, allow said application to be containerized and can scale as needed 
(perhaps lambdas for different update tasks?). 

If we are required to stick to using flat files for the Mixtape base JSON and the change file, then we could potentially
split up the change file to have a file for each action. This way, we can look into multi-threading the reading in of 
all of the different change actions in their own file, but this will require some care as to not have any race 
conditions such as a removal of a playlist happening before an update. Another option would be to use a more flatten 
file format, such as CSVs that allows for more line by line reading of data in as JSON has a whole structure that needs
to be maintained and parsed where as CSVs are far more straight forward to do data work on (at the cost of human 
readability).

Overall, getting away from the flat file IO and moving data into a DB would allow for easier parallelization with
multiple instances of this application able to hit the same DB containing what was originally in the mixtape base JSON,
which should aid in the scaling of this application.  
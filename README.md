Author Name: **Dylan Melotik**

email: **dmelotik@wisc.edu**

### Note(s): 
Make sure you clone the data repo in order to use the application.

## How to run the application:
1. Go into cmd / terminal
2. Go to your_path/covid19_US_Trends
3. `jar -cfm executable.jar manifest.txt`
4. `java --module-path "\path\to\javafx-sdk-11\lib" --add-modules javafx.controls,javafx.fxml -jar executable.jar`

## What I learned:
1. Creating a GUI through JavaFX 11
2. Connecting a GUI to data/funcitons
3. Importance of planning modular coding to allow for easier testing
4. How to store and access large amounts of data (through *.csv and other text files)

## What I would do next:
1. Add more styling
2. Make the code more modular and add testing
3. Add a button to update the /data repo from the GUI
4. Add more validation and error handling

## How to put the data on your machine:
1. Open Git's command line
2. Go into your_path/covid19_US_Trends/application
3. `mkdir data`
4. `cd data`
5. `git init`
6. `git remote add origin https://github.com/CSSEGISandData/COVID-19`
7. `git config core.sparseCheckout true # enable this`
8. `echo "csse_covid_19_data/csse_covid_19_daily_reports_us" > .git/info/sparse-checkout`
9. `git pull origin master`

## How to update the data:
1. Go into covid19_US_Trends/application/data
2. `git pull origin master`

## Bugs:
1. Not every input validation is accounted for > To fix this the code needs to be more modular
2. Will not work properly, unless the data repo is downloaded

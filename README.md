## TravelKeeper

Keep your travel experience saved and ready to use. 

You can easily access your entire location history where you've been.

### Prerequisites

* Oracle jdk 1.8
* maven > 3
* ElasticSearch 5.5

### Configuration

#### ElasticSearch 

Edit /config/elasticsearch.yml and set cluster.name: travelkeeper-cluster-dev

Run bin/elasticsearch to start elasticsearch

### Build and run

You need to have an instance of ElasticSearch 5.5 running before you can run the app.

Go to the project root folder and type:

    $ mvn install                       # build
    $ mvn install -DskipTests           # build and skip all tests
    $ mvn integration-test              # build and execute alltests
    $ mvn test                          # build and execute unit tests 
    
### Run

    $ mvn spring-boot:run

### Check out the service.

    $ curl localhost:8090/travelkeeper


## TravelKeeper

Keep your travel experience saved and ready to use. 

You can easily access your entire location history where you've been.

### Prerequisites

    * JDK 1.8
    * Maven > 3
    * ElasticSearch 5.5

### Configuration

#### ElasticSearch 

Edit /config/elasticsearch.yml and set cluster.name: travelkeeper-cluster-dev

Run bin/elasticsearch to start elasticsearch

### Build and run

You need to have an instance of ElasticSearch 5.5 running before you can run the app.
Open the application-dev.yml and set the cluster-name

Go to the project root folder and type:

    $ mvn clean compile install
    
### Run

    $ mvn spring-boot:run

### Check out the service.

    $ curl localhost:8090/travelkeeper


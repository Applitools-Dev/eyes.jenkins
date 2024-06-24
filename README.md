## Applitools Eyes Plugin for Jenkins
----------

### Usage 

#### Adding the Applitools Eyes plugin to your Jenkins CI:     
To install built package to your Jenkins, use menu Jenkins => Manage Jenkins => Manage Plugins. On the 'Advanced' tab download the applitools-eye.jar
For more details see the [guide](https://www.jenkins.io/doc/book/managing/plugins/)      

#### Adding Applitools to your project
The Applitools support is available on project basis. To enable Applitools support for a project, check the 'Applitools support' on the **build environment** section on the projects' configuration page. [More details are available here](https://plugins.jenkins.io/applitools-eyes/) 

#### Explicitly setting the BATCH ID / Name 
The plugin exports a set of environment variables which are used by the Applitools SDK when a test is run, 
and later by the plugin itself to present the Applitools results in the build status page.
  
These environment variables can overridden during the build.
For example, to override the APPLITOOLS_BATCH_ID environment variable, place a value to the file ./.applitools/BATCH_ID (in the build root folder). 
If the file ./.applitools/BATCH_ID exists, the module will read it and export its value as APPLITOOLS_BATCH_ID environment variable. 
Also, it will be stored as a build artifact, which will later be will be used to display the Applitools test results.

The same goes for any other Applitools environment variable.   

#### Building the plugin from source

To build the version, execute

    mvn clean; mvn package
    
The built module appears in the file://./target/applitools-eyes.jar    

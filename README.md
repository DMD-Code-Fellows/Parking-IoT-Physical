[![DMD](https://lh3.googleusercontent.com/TpRCbZnpkEiu4zKz0E664Avvi1alAWtR_gVhMUodeMi5WGhMq8DyVNtM5w25Uv11mm9G8tSthFy3DDDnxDpzb_JKevcCpIC7VH3SG4dZV2DHDLb8xAdyG23GJ9E7ihJuXVGXo-1U3nvuaLPJE6HLjLKpNjM7YSRE84QGKczQF2AvHE9EK4HjDKksFw4V8vb3LYzL9JlMLNaehpWkYb-IJ34N_ZG27gETCXF5FyHns7Ya4PtbOJQoq4iizSHTkzsYNp3SOW__UddtFujXlUw9gAfQ5XuK6UWrDhq7r3HZZt9SIVwdNXLCU2fpCR7qTfMosgK8cpvPmRb4FNOw4iCMwRVfnKo658qnAguwqq6Ryj7Pv-sgk5W7Hy17n7QoJGZUc0MVCZEnzjggjcIvv9UW7AUvRfdOD5Mxe4FnhYkdlrxGe8rUxF9lWQY6_4KB2xAcElIjWwVZLaoa5zFUTQs_JnfSMGMEVKeLsDv8KV3fpIDfjiYygbbmBLcXXFgT9XT8MCIdDFYr_ymmKPn8rsLNsc3czcP5Xjpf4ey6_W477-MsCS1vBOv9ORxDtNVp9kYBIhp7o7bYavXXQDsFs7Dv4mwSxvvIPkrlZnKP95i7LUzoWTESeO6A-DecAmfqNuapwQc3C4Ih3JhORgppsz6CvjeZ=s739-no)](https://http://parking.my-dog-spot.com/)
# Parking-IoT-Physical
Our team name: DMD-Code-Fellows

:star: Star us on GitHub!

Code base and resource repository use for raspberry pi interaction with [Parking-Iot-Server](https://github.com/DMD-Code-Fellows/Parking-IoT-Server)

Documentation: [Javadoc](https://dmd-code-fellows.github.io/Parking-IoT-Server/)

* [System requirements](#system-requirements)
* [Features](#features)
* [Environment Setup](#)
* [Device Setup](#device-setup)

## System requirements
* Raspberry Pi Device
    * jumper cables, breadboard, resistors
* Raspbian OS
* Library (Wiring Pi, Pi4J)


## Features

* Clone project from Github
* Follow this RaspberryPi configuration tutorial
* Run `./gradlew build` in console to compile code
* Run `./gradlew bootrun` to run application


## Environment Setup

###### Current Phase:
In Development

###### For Contribution Only Purposes:
- Only applicable to current members of the organization, git clone the project repository into a local directory.
- All work must be done on a unique branch that indicates the contributor and the feature.
- All merges from local branches can only be into the test branch. Approval from organization member is required prior to merging to master for publication


## Device Setup:

### Prerequsites

###### Java Runtime (JRE/JDK)

If you are using a recent build of Raspian, then an Oracle Java runtime environment is pre-installed. Nothing more to do.

To check if you have Java installed run:


    java -version


If you are using an older Raspian build that does not already include a Java runtime environment, then you need to follow these steps to setup the environment:
* Install and boot the Raspberry Pi using Rasbian
* run:

    ```
    apt-get update
    apt-get install oracle-java8-installer`
    ```
###### WiringPi Native Library

The library Pi4j is dependent on the use of an updated version of the library WiringPi. Even though it is native to  Raspian, the most current dependency is using a deprecated version of WiringPi.

To install WiringPi in your local RaspberryPi follow these instructions:

* download: [Wiring Pi 2.46](https://git.drogon.net/?p=wiringPi;a=snapshot;h=8d188fa0e00bb8c6ff6eddd07bf92857e9bd533a;sf=tgz)
* note the unique identifier number and letters after wiringPi
    * for example download file name: wiringPi-8d188fcf20.tar.gz
    * unique identifier number: 8d188fcf20
* then run this code:
    ```
    $ cd
    $ tar xfz wiringPi-98bcb20.tar.gz
    $ cd wiringPi-98bcb20
    $ ./build
    ```
* testing wiringPi's Connection
    * ```
      $ gpio -v
      $ gpio readall
      ```
    * A version result should display

### Schematics

### RaspberryPi Setup

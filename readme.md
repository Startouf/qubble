# Project Qubble

## Poster

You can have a look at [the poster (French)](https://drive.google.com/file/d/0B8EhxTyDP6M-RUN2QWJneGdVQVE/view?usp=sharing) we used for the presentation of our project

## Summary - What is Qubble ?

Creating an innovative music instrument from scratch, and easy to play with.

The final prototype is a big 1m² table which acts like a physical sequencer (as opposed to a software tool to generate new musics). The user can place objects on the table, which will contribute to playing certain notes or samples at a given time.

The actual mechanics involved inscribing QR-Codes under the objects, and detecting them with a camera placed under the table (the surface of the table is semi-transparent). Also, a projector and some reflection techniques were used to project an image on the table, so as to display eyecandy animations and give the user more feedback. Both the camera and the projector are connected to a computer, and various options to configure the table can be set using a GUI on the computer.

The main program was coded in Java, and split in different modules : Image Detection & Recognition, Audio Synthesis, UI Design, OpenGL, Movement estimation.
Some attention was also brought to defining a valid Business Model, and the overall integration implied using synchronisation techniques.

![Screenshot of Qubble durin the innovation Day](screenshot_qubble_innovation_day.jpg?raw=true "Screenshot of Qubble during the Innovation Day")

## Setup

Only the source code is available on this github. In order to reduce the repository size, multimedia object, libraries and other binaries have been removed from the git repository.

If you would like to run the project, we can give you some links to archives with the data and the libraries used for the project, please contact us.

Below short explanations, see "About the showcase setup" for more information

### Fake mode

It is possible to run the project without a camera and a projector. A "fake camera" GUI will let you simulate the detection of Qubjects and the opengl output will just open on your main display

### Real mode

If you want to run the project with a real camera, you might have to change some lines of code in the camera class that implements CameraInterface. Typically, depending whether you are running Windows/Linux, you will have to comment/uncomment the specific line of code, and make sure opencv grabs the good "device number" corresponding to the camera.

The code auto-detects a secondary display if available and opens the opengl output there. We assumed HD quality for the output, if you want to change the resolution there should be some hard-coded values in the code at several places... (check in Calibration classes)

## Usage

### Compile and run

The project was developped using Eclipse. The files .project and .classpath have been commited so you don't have to worry about rebuilding everything as it should. 

There are several main() that you can run, TL;DR : You can use the main in `calibration.Run`.

*  Main in `calibration.Run`. That main is supposed to automatically recalibrate the display (however automatic adjustment was not implemented and calibration data was actually hard-coded for the showcases with manual tuning). 
*  (...)

### After it compiles and executes

Click "Nouveau Projet". It should open the OpenGL window. Now press Play.

Avoid hitting the stop button. It is most likely broken, and it safer to just exit the program (note : clicking exit on the main GUI *should* also close the OpenGL Output)

It is possible to click "vue individuelle" or "vue tableur" to access new tabs where you can configure the Qubjects

Enjoy some cool music !

## About the showcase setup

Normally, the prototype consists of a semi-transparent table with a Full HD projector + a somewhat HD camera. That is, the system under which you run the project is expected to have a second monitor connected, as well as a camera.
Also, you would need some special QR-like codes to put on the table, calibrate the camera and the image recognition, which is quite a pain in the ass, and we actually had to do it manually.
Well, actually it would be enough to just film some surface on which the camera could see some of our QR-Codes. 
Anyway if you are really interested in recreating the full experience of this project, please contact us directly.

It shouldn't be a problem for the second monitor, as we use some Java utility class to find the second output (which should be ok cross-platform).
Note : the system will try to autodetect a second monitor. If it cannot find it, it will open the OpenGL output in the main display.

However for the camera, the project uses OpenCV, which is not cross-platform friendly. The program has been tested on Windows and Linux/Debian system and seemed to run fine (for the demo we used some rubbish camera which was hardly detected under linux, but hopefully Tarik Graba was able to debug it nicely)
Note howevr, that the program has be configured to use a FakeCamera by default, which will open an additional UI where you can decide to simulate the presence of Qubjects (and some will be simulated by default) 

Also, some threads are quite CPU intensive (image recognition + audio synthesis), so don't expect to run it with a bad computer

## More info about the project

### The context of the project

This project was started in the context of Télécom ParisTech PACT (Projet d'Apprentissage Collaboratif Thématique), a 6-month collaborative self-learning school project during which we built a project from scratch.

And from scratch, we really mean from nothing, not even the idea. This 6-month duration included the time needed to build a team, suggest ideas, ask experts about the technical feasability, designing, implementing, prototyping, etc. 

The school projects was divided into 4 examination milestones (called PAN), during which we had to progress through the aforementionned steps. Our project was selected as the most innovative one, and we were motivated enough to bring it to the next level by participating to Telecom ParisTech's Innovation Day, during which the project was selected as the first-year best student project, and rewarded a generous 2000$.

[<img src="https://www.facebook.com/photo.php?fbid=485297998282598&set=exp.485300278282370.unitary&type=1&theater" />](https://www.facebook.com/photo.php?fbid=485297998282598&set=exp.485300278282370.unitary&type=1&theater)

We thank Télécom ParisTech for giving us such an opportunity

### Task repartition

The main project was split into different subtasks

*  Image Detection and Recognition - Eric Masseran & Muriel Nahmani
*  Audio Synthesis - Vincent Couteaux & Alexandre Arnault
*  UI Design - Cyril Duchon-Doris & Karl Bertoli 
*  OpenGL animations - Cyril Duchon-Doris
*  Movement estimation (not implemented) - Pierre Lorin de Reure & Eric Masseran
*  Business Model - Karl Bertoli & Alexandre Arnault
*  Project Manager & Integration - Cyril Duchon-Doris

### Innovation Day Taskforce

After the school project was graded, we were offered the opportunity to present it during Télécom ParisTech Innovation Day. It wouldn't have been possible without extra work, and the following subset of team members agreed to keep working on it :

Cyril Duchon-Doris, Eric Masseran, Vincent Couteaux, Karl Bertoli

GG Guys :) !


## Special thanks

We would like to give special thanks to the following people who have given us invaluable help

*  Tarki Graba, our project mentor at Telecom ParisTech
*  Télécom ParisTech experts who have shouldered us for the different modules
  *  Bertrand David - Audio expert & PACT Organisation
  *  Jean Le Feuvre - Expert in OpenGL
  *  Sebastien Gardoll - IT & GUI expert
  *  Rémi Sharrock - IT Expert
  *  Jean Claude Dufourd - Audio expert
  *  Michel Roux - Expert in Image Processing
  *  Marco Cagnazzo - Expert in Movement estimation
  *  Rémi Maniak - Expert in Business Models
*  Thomas Houy - Organiser of Télécom ParisTech Innovation Day
*  Guénolé Lallement, who helped us reuse the table for the demos, and who lent us the mirrors


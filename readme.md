# Project Qubble

## Summary - What is Qubble ?

Creating an innovative music instrument from scratch, and easy to play with.

The final prototype is a big 1m² table which acts like a physical sequencer (as opposed to a software tool to generate new musics). The user can place objects on the table, which will contribute to playing certain notes or samples at a given time.

The actual mechanics involved inscribing QR-Codes under the objects, and detecting them with a camera placed under the table (the surface of the table is semi-transparent). Also, a projector and some reflection techniques were used to project an image on the table, so as to display eyecandy animations and give the user more feedback. Both the camera and the projector are connected to a computer, and various options to configure the table can be set using a GUI on the computer.

The main program was coded in Java, and split in different modules : Image Detection & Recognition, Audio Synthesis, UI Design, OpenGL, Movement estimation.
Some attention was also brought to defining a valid Business Model, and the overall integration implied using synchronisation techniques.

![Screenshot of Qubble durin the innovation Day](screenshot_qubble_innovation_day.jpg?raw=true "Screenshot of Qubble during the Innovation Day"))

## Setup

Only the main source code is available on this github. In order to reduce the repository size, multimedia object, libraries and other binaries have been removed from the git repository.

The libraries can be downloaded [TODO : link](#)

The data can be downloaded [TODO : link](#)


## Usage

### Compile and run

...will most likely fail (or then we are gonna be jealous)

The project was developped using Eclipse. The files .project and .classpath have been commited so you don't have to worry about rebuilding everything as it should. 

The main()s you want to run is the one in the class `calibration.Run`

### Wy is it not that simple ?

Normally, the prototype consists of a semi-transparent table with a Full HD projector + a somewhat HD camera. That is, the system under which you run the project is expected to have a second monitor connected, as well as a camera.
Also, you would need some special QR-like codes to put on the table, calibrate the camera and the image recognition, which is quite a pain in the ass, and we actually had to do it manually.
Well, actually it would be enough to just film some surface on which the camera could see some of our QR-Codes. 
Anyway if you are really interested in recreating the full experience of this project, please contact us directly.

It shouldn't be a problem for the second monitor, as we use some Java utility class to find the second output (which should be ok cross-platform).
Note : the system will try to autodetect a second monitor. If it cannot find it, it will open the OpenGL output in the main display.

However for the camera, the project uses OpenCV, which is not cross-platform friendly. The program has been tested on Windows and Linux/Debian system and seemed to run fine (for the demo we used some rubbish camera which was hardly detected under linux, but hopefully Tarik Graba was able to debug it nicely)
Note howevr, that the program has be configured to use a FakeCamera by default, which will open an additional UI where you can decide to simulate the presence of Qubjects (and some will be simulated by default) 

Also, some threads are quite CPU intensive (image recognition + audio synthesis), so don't expect to run it with a bad computer

### After it compiles and executes

Click "Nouveau Projet". It should open the OpenGL window. Now press Play.

Avoid hitting the stop button. It is most likely broken, and it safer to just exit the program (note : clicking exit on the main GUI *should* also close the OpenGL Output)

It is possible to click "vue individuelle" or "vue tableur" to access new tabs where you can configure the Qubjects

Enjoy some cool music !

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


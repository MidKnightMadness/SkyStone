# Welcome to the Robotics Programming Club!
 - If you want, you can click the `install plugins` button in the yellow bar above (if in android studio) to install the markdown plugin.
 - To make this easier to read, you can click View > Active Editor > Soft Wrap to wrap the text to the editor.


## Getting Started
 - You've already done most of it.
 - Right now, you should be looking at this file in Android Studio, you should be signed into GitHub in Android Studio, and on the branch `club`. If so, success! If not, let someone know so they can help you fix it. If you're on your own, go look at https://github.com/MidKnightMadness/Software-Install for instructions. Hopefully that helps.

## Making your first OpMode
 - FTC has made this nifty little repository that gives us a lot of the code to run the robot. All we have to do is create classes that extend OpMode or LinearOpMode (detail will be given soon), and these will be run by the robot controller and selected by the driver station phones.
 - To get started, make sure your project view is set to `Project Files`, not `Android`. `Android` shows many pseudo-files that don't really exist and will confuse you when you start.
 - There should be two bold folders, `FTCRobotController`, and `TeamCode`. There is a **lot** of opening folders many layers deep and it is easy to get lost. Please try to keep whatever you're not using minimized in the file tree view so you know where you are working. For now, open `FTCRobotController > src > main > java > org > firstinspires > ftc > robotcontroller > external > samples`. This is the samples folder which has sample code on how to perform many actions in an OpMode including servos, Dc Motors, and the different OpModes themselves.

 - Sadly, the simplest of these samples is still complicated for your first look at an OpMode. To help you get used to this style of code, I've created a different sample for you to take a look at. Now that you know where the samples are (they are a great source for reference), go find `TeamCode > src > main > java > org > firstinspires > ftc > teamcode`. This is the folder where all of the team's code goes that will be run by the robot controller. I have created a sample OpMode there named SampleOpMode.

 - We always want to make a copy of these samples since all of our code should be within the `TeamCode` folder and to keep the samples there for future reference, so right-click on `SampleOpMode` for now and select `Refactor > Copy...`. For the class name, leave it as-is, but the destination package should be org.firstinspires.ftc.teamcode.gregoryling, replacing my name with your name or another unique all-lowercase letters name that no one else is using. Yes, the name will show up red. That's ok so long as just your name is red. The rest of the package string should be black.
 - Finish the dialog and a copy shoud appear in the editor. If there's an error, check your spelling.
 - If you look in the file tree, you'll notice it made a new folder in teamcode with your name and placed a copy of the sample OpMode in there.

## Running the OpMode

## Dissecting the OpMode
 - There's a lot in here.
# koleada-engine
A general platform for me to learn game design using Processing and through Java.



Student Number: C20381946
Student Name  : Matthew Tweedy
---
# How it works:
The draw function is split into two sections. These are the if statement that checks if there was a beat, and the else where most of the objects are drawn. The if (wasBeat) section is mainly used for certain variables that I want to increment to affect the way some of my objects are drawn. For example, I have the number of circles in the background increasing on every beat, up until it reaches 9 and resets to 1. I also have the multiplier of how much these circles react to the beat decrementing depending on how many circles there are currently. Inside the else I am doing most of the drawing. I have a grid of spheres that grow in size based on the volume of the beat, which also have mapped colours in 3 ranges, red, green, and blue that cycle each on beat. Underneath, there is the circles in the background that change colours matching with the spheres and change to an RGB gradient after a certain beat. Finally there are 9 cubes with inner cubes that are translated on the z-axis based on the sin() of an angle that is constantly being increased by 0.02 radians. The inner cubes match the colour cycling of the other objects, while the outer stay white until the prelude to the final beat drop, where they are given random RGB values. By the end, there’s a lot of colour.

# What I am most proud of in the assignment:
In this assignment I am most proud of my dancing cubes. They have probably taken the most effort and time configuring due to the set of effects I have them cycling through depending on the section of the song. This was achieved by printing the current frame of the song at each beat, taking the frame value at the correct beat for the drop or change in song and then adjusting the stroke/translate accordingly. The smaller cubes inside are also cycling through red, green, and blue based on the modulo result of how many center circles are currently being drawn. All these problems were fun to tackle individually and see it slowly progress towards my final vision.

-----

Student Number: C20366426
Student Name: Ben O'Brien
---
For my visual portion of the assignment, I wanted to try creating a wave of colour which surrounded a particular shape, in this case, the shape being a sphere. I wanted to create a visual which would change based on a certain beat that would change the initial ring into a wave. The size of the sphere and the surrounding rings and waves ultimately changed based on the beat of the song.

In order to detect the beat, I used a variable called currentFrame.
This variable syncs the frame to each beat and therefore, was able to change the visual depending on the beat I wanted the visual to change at.

The visual also has mouse movements depending on where the cursor currently is. To do this, I used mouseX and mouseY in the camera function.

# Part I am most proud of
In my personal opinion, I think the part I am most proud of is creating the visual of the wave. From initially creating a cube moving in a circular motion, I created a wave of cubes in a nested for loop which created the wave visual. I initially created it from trial and error, to try and create a visual in which I was pleased with.

# How it works
At the start of my portion is right when the beat drops, the visual detects the beat of the song and syncs the sphere and inner ring together. When the 2nd beat drops for my portion, I used the variable currentFrame to dictate when the visual changes to the next visual. The next visual is the wave of colour that splashes along the screen, still containing the ring aspect, as if it was a vinyl disk at first, which splashes into a wave. I created the wave by recycling some code which I previously used for one visual but altered it to create the wave. In the code, particularly for the draw function, there is a "wasBeat" if state which checks if a certain thing is on the beat. In the "wasBeat" if statement, I used it to measure the frame of the beats. I used this to determine when the main drop of the beat happens. To adjust the size of the sphere, rings and the wave, I created a variable for each visual. Then I used the lerp function and multiplied the volume of each beat by the objects, which as a result, changes the size depending on the beat.

Student Number: C20353533
Student Name  : Kamila Krukowska
---
# How it works:
In order to draw the visuals, I wanted to create, I implemented if statements to separate and display the visuals on the current frames. In the first section I have a cube that’s made up of several cubes that change color depending on what frame it is on. The cube spun around by rotating it on the X and Y axis. The cubes also changed in size based on the sin() of a frame Count which was divided 20.0. The second section involves a background of squares that get bigger on the beat using the lerp function giving it an almost Flashing effect. In front of the background there are several circles of different sizes that spin around, after some time these circles change color and spin faster than before. The third section consists of a background of squares and circles. The squares would get bigger with the beat. The circles would also change color and get bigger depending on the frame. In the last section a triangle was displayed along with a grid and more outlined triangles. The outlined triangles rotate, while the gird flashed and changes from white to black. The colors from the main triangle and the outlined one's switch. 
# What I am most proud of in the assignment:
In this assignment I am most my section 4, i was originally having slight issues with placment of the verious triangles and how i was going to rotate them. i meant to spin them in their position, however after a while of working that i figured out a diffrent way to rotate them which i liked more. All in all i found this visual pleasing which is why im proud of it.

-----

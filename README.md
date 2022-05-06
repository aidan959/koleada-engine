# koleada-engine
A general platform for me to learn game design using Processing and through Java.

Student Number = C20366426
Student Name = Ben O'Brien

For my visual portion of the assignment, I wanted to try creating a wave of colour which surrounded a particular shape, in this case, the shape being a sphere. I wanted to create a visual which would change based on a certain beat that would change the initial ring into a wave. The size of the sphere and the surrounding rings and waves ultimately changed based on the beat of the song.

In order to detect the beat, I used a variable called currentFrame.
This variable syncs the frame to each beat and therefore, was able to change the visual depending on the beat I wanted the visual to change at.

The visual also has mouse movements depending on where the cursor currently is. To do this, I used mouseX and mouseY in the camera function.

# Part I am most of
In my personal opinion, I think the part  I am most proud of is creating the visual of the wave. From initially creating a cube moving in a circular motion, I created a wave of cubes in a nested for loop which created the wave visual. I initially created it from trial and error, to try and create a visual in which I was pleased with.

# How it works
At the start of my portion is right when the beat drops, the visual detects the beat of the song and syncs the sphere and inner ring together. When the 2nd beat drops for my portion, I used the variable currentFrame to dictate when the visual changes to the next visual. The next visual is the wave of colour that splashes along the screen, still containing the ring aspect, as if it was a vinyl disk at first, which splashes into a wave. I created the wave by recycling some code which I previously used for one visual but altered it to create the wave. In the code, particularly for the draw function, there is a "wasBeat" if state which checks if a certain thing is on the beat. In the "wasBeat" if statement, I used it to measure the frame of the beats. I used this to determine when the main drop of the beat happens. To adjust the size of the sphere, rings and the wave, I created a variable for each visual. Then I used the lerp function and multiplied the volume of each beat by the objects, which as a result, changes the size depending on the beat.

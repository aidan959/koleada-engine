# koleada-engine
A general platform for me to learn game design using Processing and through Java.



## Student Number: C20381946
## Student Name  : Matthew Tweedy
# How it works:
The draw function is split into two sections. These are the if statement that checks if there was a beat, and the else where most of the objects are drawn. The if (wasBeat) section is mainly used for certain variables that I want to increment to affect the way some of my objects are drawn. For example, I have the number of circles in the background increasing on every beat, up until it reaches 9 and resets to 1. I also have the multiplier of how much these circles react to the beat decrementing depending on how many circles there are currently. Inside the else I am doing most of the drawing. I have a grid of spheres that grow in size based on the volume of the beat, which also have mapped colours in 3 ranges, red, green, and blue that cycle each on beat. Underneath, there is the circles in the background that change colours matching with the spheres and change to an RGB gradient after a certain beat. Finally there are 9 cubes with inner cubes that are translated on the z-axis based on the sin() of an angle that is constantly being increased by 0.02 radians. The inner cubes match the colour cycling of the other objects, while the outer stay white until the prelude to the final beat drop, where they are given random RGB values. By the end, there’s a lot of colour.

# What I am most proud of in the assignment:
In this assignment I am most proud of my dancing cubes. They have probably taken the most effort and time configuring due to the set of effects I have them cycling through depending on the section of the song. This was achieved by printing the current frame of the song at each beat, taking the frame value at the correct beat for the drop or change in song and then adjusting the stroke/translate accordingly. The smaller cubes inside are also cycling through red, green, and blue based on the modulo result of how many center circles are currently being drawn. All these problems were fun to tackle individually and see it slowly progress towards my final vision.

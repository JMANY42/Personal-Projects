This is my version of a Snake AI. I made this as demo to get incoming freshmen to join the Computer Science Club at a special event right after winter break my junior year.
Run RunSnakeAI.java to watch the AI go

This was my first project using Java graphics and I had no idea what to do, so, I decided to copy some code off the internet for the snake game and study it to learn it. While I was wrapping my head around the code, I found some bugs in it that I fixed and then started on the AI.

The AI works by essentially running a maze solver from the head to the apple when it eats the previous apple. In the event that no path is found, it will try to keep itself alive and search for the apple every frame until it either finds a path or dies (usually dies).

What I learned: Java graphics and how Java Timers work. 

Problems: The AI isn't very good, it dies kinda quickly
This is my Chess AI, my biggest project to date. I am very proud of this. I made this in the spring of my junior year because I wanted a big challenge and because its cool to say that I've coded a chess AI. 
Run Test.java to run the game


The base game as well as the AI was coded from scratch in Java. I looked up as few tutorials and lessons as I could while making this, opting to make a sloppier and slower game and AI so that I could come up with my own techniques and learn more than I would just by copying code from online somewhere. I did look up the basic structure of the minimax algorithm and when it was super slow, due to many other factors, I optimized it with alpha beta pruning. 

My version of Chess is object oriented where every piece is its own object defined by my Piece class

There are three versions of the AI: RandomAI, AIv1, and AIv2.

Random AI just returns a random move in its movelist
AIv1 looks if it can capture a piece and then takes it, or makes a random move

AIv2 is the actual AI
It implements the minimax algorithm with alpha beta pruning

What I learned: Everthing.
I learned so much by attempting this project. I expanded my knowledge in every field of programming that I had learned up to this point, as well as learning many new topics. I learned more about java graphics and handling mouse clicks, code structuring, basic chess programming techniques, and the minimax algorithm with alpha beta pruning.

Problems:
It is very slow. Running at a depth of more than 4 takes a very long time. 
In order to speed this up, I could have implemented memoization where it stores every position explored and then looks it up later. 
Additionally, as an attempt to improve performance, I figured that maybe using classes to define every piece was causing a bottleneck so I attempted to change every reference to the Piece class with an integer with specific bits in different places to represent different categories of pieces. 
My biggest mistake was not saving a backup copy of the original code. Making this change did not improve performance whatsoever and made it 100x harder to read. This is my only regret in the entire project.

Overall, I am very proud of this even though it's sloppy and slow.
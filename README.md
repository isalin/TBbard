# TBbard

TBbard is a java application for playing back MIDI files in FFXIV using the Bard performance system. 

# Instructions

Notes can be extracted by dragging and dropping a MIDI file onto the application. The user will then be prompted to select an instrument/channel to extract.

Alternatively, it's also possible to manually enter or edit the "sheets".

For the automated music playback feature to work, after pressing play:
* Tab back into FFXIV.
* Hover the cursor over the "C(-1)" note in the Performance menu (basically just scroll to the top and hover over the top left action).

## UI description

* **WaitMultiplier** is a value that will be multiplied with every wait command. This can make some of the faster songs sound a lot better. 

* **Min FPS** is the minimum fps value that you're experiencing at the current in-game location. FFXIV's UI input is limited by your FPS and a delay is needed inbetween clicks. **Note:** *If you "uncap" your fps to a value higher than your monitor supports, you can still use the higher value.*

* **Start delay** is the delay (in milliseconds) between pressing Play and the application beginning playback. This is to make sure you have time to tab back into FFXIV and move the mouse to the appropriate spot.

## Syntax


* The application will understand of the following notes: C(-1), C, C(+1), C#(-1), C#, C#(+1), D(-1), D, D(+1), Eb(-1), Eb, Eb(+1), E(-1), E, E(+1), F(-1), F, F(+1), F#(-1), F#, F#(+1), G(-1), G, G(+1), G#(-1), G#, G#(+1), A(-1), A, A(+1), Bb(-1), Bb, Bb(+1), B(-1), B, B(+1).

* There is no need to write the parenthesis. "C-1" will work in place of "C(-1)".

* Space and line break are both interpreted as a division between two notes. 

* Typos and malformed notes are simply ignored.

* The character "w" followed by numbers, is interpreted as a "wait" command. The numbers is the time to wait in *milliseconds*. For an example, "w3500" would mean waiting for 3500 milliseconds (3.5 seconds).

# Limitations

* Bards in FFXIV are limited to 3 octaves, whereas MIDI files are capable of playing back 12 octaves. The application will attempt to convert this in a reasonable way. Unfortunately, some songs simply won't sound recognizable.

* FFXIV limits the input rate of notes music notes, and instead queues up quickly input notes. This means that fast paced songs sometimes lose their rhythm. This can be partially fixed by increasing the "WaitMultiplier". Many songs will still sound recognizable then played slowly.

* Due to performance considerations, he note C(+2) isn't available at the moment.

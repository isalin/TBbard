**Note:** If nothing happens in FFXIV on playback, you might need to run the application as administrator (right-click the .exe version and "Run as administrator"). Drag and drop does not work when run as admin.

# TBbard

TBbard is a java application for playing back MIDI files in FFXIV using the Bard performance system. 

[Example Usage](https://i.imgur.com/69lORVy.gifv)


# Instructions

Notes can be extracted by dragging and dropping a MIDI file onto the application or by clicking "Open" and navigating to the intended file. You can then select the instrument to play back.

Alternatively, it's also possible to manually enter or edit the "sheets".

For the automated music playback feature to work:
1. In FFXIV, make sure the perform interface is open and that your character is ready to play.
2. Drag and drop the file to play, or navigate to the file by using the "Open" dialog (or enter your own notes!).
3. Click Play.
4. Tab back to FFXIV, and make sure it's the currently selected window.
5. Playback should start after the Start Delay.

## Exe or jar

The .exe version is just a wrapper for the .jar version. It's mainly intended as a convenient way to quickly run the application as admin. You still need to have java installed!

## UI description

* **WaitMultiplier** is a value that will be multiplied with every wait command. This can make some of the faster songs sound a lot better. 

* **Min FPS** is the minimum fps value that you're experiencing at the current in-game location. FFXIV's UI input is limited by your FPS and a delay is needed in-between clicks. **Note:** *If you "uncap" your fps to a value higher than your monitor supports, you can still use the higher value.*

* **Start delay** is the delay (in milliseconds) between pressing Play and the application beginning playback. This is to make sure you have time to tab back into FFXIV and move the mouse to the appropriate spot.

* **Octave Target** FFXIV's perform system is limited to three octaves, whereas midi can have up to ~10 octaves. TBbard will attempt to convert the notes to sound reasonable, but for some songs you might want to adjust the target octaves (most of the time you'll probably want to leave this on the default).

* **Loop** will cause the playback to loop indefinitely until it's manually stopped. There's a 1 second delay in-between every loop to let the playback buffer catch back up for fast paced songs.

* **Instrument** refers to the selected MIDI instrument to play.

* **Open** lets you manually browse for a file.

* **Use full keyboard layout** means that TBbard will try to use the layout specified in the screenshot (check out the "Full keyboard layout" section below, or click the [?] in the application for more info). Greatly reduces latency, since it doesn't have to keep pressing and holding ctrl/shift.

* **Hold long notes** if selected, it'll hold every note prefixed with "h" or "hold" until another note is to be played or it gets a "release" command. If unchecked it'll just ignore the hold part, and play as it has previously with rapid taps for each note.

## Full keyboard layout
If you want to use the full keyboard layout, make sure your keys are bound like this:
![Full keyboard layout](https://i.imgur.com/bGUNHwL.png)

**Q: Why is the layout so weird?**

A: Because there are so many international layouts, and this way they should all be supported (hopefully). 


## Syntax


* The application will understand of the following notes: C(-1), C, C(+1), C#(-1), C#, C#(+1), D(-1), D, D(+1), Eb(-1), Eb, Eb(+1), E(-1), E, E(+1), F(-1), F, F(+1), F#(-1), F#, F#(+1), G(-1), G, G(+1), G#(-1), G#, G#(+1), A(-1), A, A(+1), Bb(-1), Bb, Bb(+1), B(-1), B, B(+1).

* There is no need to write the parenthesis. "C-1" will work in place of "C(-1)".

* Space and line break are both interpreted as a division between two notes. 

* Typos and malformed notes are simply ignored.

* The character "w" followed by numbers, is interpreted as a "wait" command. The numbers is the time to wait in *milliseconds*. For an example, "w3500" would mean waiting for 3500 milliseconds (3.5 seconds).

# Limitations

* Bards in FFXIV are limited to 3 octaves, whereas MIDI files are capable of playing back ~10 octaves. The application will attempt to convert this in a reasonable way. Unfortunately, some songs simply won't sound recognizable.

* If you're running the application with User Account Control active, you might have to run TBbard as administrator. To do this conveniently, use the .exe version, right-click it and select "Run as administrator".

* The drag and drop system doesn't work if the application is run as administrator.

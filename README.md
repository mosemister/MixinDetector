# MixinDetector

Designed to detect all mods and plugins that use Sponge Mixins as well as the Mixin file that the file contains.

You can then filter the results by name, date or even crash log. But also by clicking on a mod/plugin it will take you to its support page (if one is found) for you to search for incompatibilities and hopefully a fix, if not hopefully you can then report the issue for fixing

## Download
You are able to download it from out release's tab on the right (or click [here](https://github.com/mosemister/MixinDetector/releases))

### What version do I download?
You will find four or more assets to download. The bottom two named ``Source Code`` can be ignored. With the others being called ``MixinDetector.?``. Please download the version for your computer 

- ``.exe`` for Windows
- ``.app`` for Mac

If your computer's version is not listed, then download the ``.jar`` edition as this works on all computers that have a version of [Java](https://www.oracle.com/java/technologies/downloads/) 8+ installed (If you are running a Minecraft server then it is likely you already have Java 8+ installed on your computer)

## How to use Mixin Detector

Simply open the program, select your mods folder and get a smaller list of mods that contain Mixins. That simple. 

There are filters that can be applied to filter down the smaller list even further allowing you to maybe get a result of 1 mod

## What are Mixins

Mixins are a form of technology used to add code directly into the targets codebase. 

This allows plugins and mods to access parts of Minecraft that wouldn't normally be possible.

### Why do Mixin's break my server

This is a tricky one to answer. 

1) Sometimes it's down to the developer whereby they have added code to something that works in most situations, however doesn't in all (also known as a bug).
2) Sometimes it's due to another mod using something similar to Mixins however the two mods don't talk to one and another (also known as compatibility issue)
3) Sometimes it's due to the mod running on something it was never designed to, such as if the mod was made for Minecraft 1.19.1 however your running 1.19.2, it may try accessing some of Minecraft's code that isn't there

### After I have found the mod, how can I fix the issue?

This one is simple. Take a look at the mod developer's support page. 

- It maybe that they are already aware of the issue and have a update to the mod. 
- It maybe that they are already aware of the issue and have a config option that needs to be changed
- It maybe that they are already aware of the issue and are working on a fix
- It maybe that they are not already aware of the issue and need someone like yourself to report the issue

Most mods will have a ``issues``, ``faq`` or even ``mod incompatibility`` page('s) where info like the above would be, simply check those pages out. If you can't find it then report the issue to them. Most developers want the best for the mod they work on so are likely to help 


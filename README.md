Scab
====

Scab is a 2d multiplayer shooter with RPG and platforming elements

Usage
=====

* Ensure an up to date Java JRE is installed, at the time of writing JRE 8u45
find here: http://www.oracle.com/technetwork/java/javase/downloads/index.html
* Run the game using scripts in the ``scripts`` directory, e.g. runScab.bat in
windows

Development
===========

Requirements
------------
GDXWorld is installed and active on the eclipse path. Git clone from
``https://github.com/narfman0/GDXWorld.git``
protoc 2.6.1 is installed and on your PATH

Running
-------
Several eclipse runscripts are in the ``data/eclipse`` directory. This includes
scab runscripts as well as A.I. runscripts.

Plugins
-------
Scab and GDXWorld use JSPF for plugin development. To extend functionality
in scab, read JSPF documentation here ``https://code.google.com/p/jspf/``.
Several plugin types exist, including:

* IConsoleCommand - define and extend console commands (press ``.`` to bring up
console during gameplay)
* IGunListener - when gunshot occurs
* ILevelCompletedListener - upon level completion, fail or success
* Quest manifestations, handlers, triggers - different for each, but upon
execution these are invoked
* IRagdollPlugin - ragdoll creation plugins

Behavior trees
--------------

JBT ``https://sourceforge.net/projects/jbt/`` is leaned on heavily for A.I. The
configuration files are in ``data/jbt``, and code may be regenerated by running
the following commands or using an eclipse runscript.

For regenerating the nodes:

    java -cp .:lib/* jbt.tools.btlibrarygenerator.ActionsAndConditionsGenerator -c configurationFile.xml -r .;

For regenerating the library itself:

    java -cp .:lib/* jbt.tools.btlibrarygenerator.BTLibraryGenerator -c btLibraryConfiguration.xml

enemyAggressive will run at the player when seen, unless player isn't seen for 
some time (5 seconds default) at which point it will return to its previous 
objective (path). If it has no objective, it will lie stationary until player
is seen, then run at him. If it has a path, it will travel along path until
player is seen at which point it will run at him.

enemySentry with no path will stay where he is at all times, while enemySentry
with a path will patrol it if and only if the player hasn't been seen for some
time (5 seconds default).
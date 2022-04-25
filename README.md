# WormsGame
** WORMS GAME README FILE **

-------------------------
-------------------------

** Instructions for Installing the Game**
-------------------------------------------------------
** Must Have JRE "jdk-17.0.2" Installed On PC  To Run Bat Files **
--------------------------------------------------------------------
	
	- Create folder called worms on desktop
	
	- Extract WormsGame.zip to new worms file on desktop



** Instructions for Running the Game Client **
-------------------------------------------------------

	- Open the worms folder on the desktop and navigate to the bin folder (worms\WormsGame\bin).

	- Double click the wormsClient.bat file to open up the initial login screen.



** SERVER INSTRUCTIONS BEYOND THIS POINT **
-------------------------------------------
** Instructions for Using the sql file (SERVER SIDE ONLY) **
------------------------------------------------------------

	** Make sure to have the latest version of Xampp controller installed on your PC **
	
	- Open the worms folder on the desktop and navigate to the bin folder (worms\WormsGame\bin).
	
	- Copy the file named wormsgame.sql
	
	- Navigate to you temp folder on the C: drive and paste the file in there

	- Open a command prompt window

	- Change directory to xampp (C:\xampp)

	- Execute mysql_start in cmd to start server

	- Open second command prompt window

	- Navigate to C:\xampp\mysql\bin

	- Log onto mysql as root by doing the following:

		mysql -h localhost -u root

	- Select mysql as your database:
		
		use mysql;

	- Create a new user:

		grant all privileges on * to wormsadmin@localhost identified by 'wormsadmin' with grant option;
		commit;
	
	- Create the work area:  

		create database worms;

	- Grant all privileges to yourself:

		grant all on worms.* to wormsadmin identified by 'wormsadmin';

	- Log out:
		exit;

	- On second command prompt if you are not still on the C:\xampp\mysql\bin director navigate there
	
	- Insert tables and dummy data to worms database:

		mysql -h localhost -u wormsadmin -p worms > C:\temp\wormsgame.sql

	- worms database should now have all necessary table information for ServerGUI to work



 ** Instructions for Running the Game Server **
----------------------------------------------------------	
	- Open the worms folder on the desktop and navigate to the bin folder (worms\WormsGame\bin).

	- Double click the wormsServer.bat file to open up the initial login screen.


 ** Game Controls **
----------------------------------------------------------	

	- Move left and right: Left & Right Arrow Keys
	- Jump: Up Arrow Key
	- Change Weapon: Down Arrow Key
	- Shoot Weapon: Hold Down Left Mouse Button
	- Power Up Weapon: Hold Down for a Longer Period

	Weapons:
	- Bullets - Shoot straight horizontally
	- Grendade - Bounce several times before exploding
	- Missiles - Shoot in the direction of the cursor and fall over time

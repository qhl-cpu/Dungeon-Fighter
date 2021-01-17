# Dungeon fighter

## This is an Idle game.

**About this project** :
- In this game, you will be able to enter dungeons, fight monsters, level up your character 
and challenge stronger enemies.
- This game is welcome to play for any players who are interested in **Idle game** with medieval background.
- This project interests me a lot because I have always wanted to create my own game, and **Idle game** would 
  be a good start regarding its programming difficulty and the course materials we have learned so far.


## User Stories
- As a user, I want to be able to enter a dungeon.
- As a user, I want to be able to fight with different enemies.
- As a user, I want to be able to level up my character as defeating more enemies.
- As a user, I want to be able to select and gain different buffs after defeating enemies.
- As a user, I want to be able to get a random item to my inventory after defeating enemies.
- As a user, I want to be able to view my character's current status.
- As a user, I want to be able to view my character's inventory.
- As a user, I want to be able to use different items with different effects.
- As a user, I want to be able to have options to revive my character once life goes below 0.
- As a user, I want to be able to view game's background.
- As a user, I want to be able to save my game progress(character and inventory) to a file.
- As a user, I want to be able to load my game progress(character and inventory) from the file.

## Phase 4: Task 2
- I implemented a bi-directional association between item and inventory, so that each inventory has a list of
items and each item correspond to one inventory. Both of the classes have methods that reassign the other variable 
whenever itself is modified (ie.reassign inventory/item, use/delete item)

## Phase 4: Task 3
- If I had more time on the project I would refactor my GUI frames. Since many of my frames have common behaviours
I should have created an abstract class for them. 
- If I had more time on the project I would consider add some more functions to my individual items and monsters
and override some method for variate functions.
- If I had more time on the project I would add some more exception handling and make my methods more robust.
- If I had more time on the project I would implement an observer design parttern for my character/ inventory
and InventoryFrame/CharacterFrame, then my ui package won't depend on other packages anymore
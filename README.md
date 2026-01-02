# Software Construction Personal Project - Inventory Manager : CPSC 210
I hope to build an inventory managing system that takes the name, quantity, and location (e.g., different building names) of a material, and adds it to an inventory. Other features include updating quantity and indicators for when a material has low quantity levels. 

I was inspired by my workplace to build it, as they needed a way to aggregate different materials, their variable quantities, and the changing locations. I'm interested to see if I can build a solution that tackles their tracking issues to make staff's lives easier. Hopefully small businesses/non-profits with relatively small inventory can use it to better track their items, making the order process effecient and effective. 

## User stories

* As a user, I want to be able to add multiple materials to my inventory
* As a user, I want to be able to remove materials from my inventory
* As a user, I want to be able to list all the materials in my inventory
* As a user, I want to be able to list all the materials that are below a minimum level in quantity. 
* As a user, I want to be able to have the option to save my inventory to file when quitting the application
* As a user, I want to be able to have the option to load my inventeory from file when starting the application 

# Instructions for End User
* You can add a material to Y by clicking the 'add new material' button. 3 input panels will show up consecutively; enter the item name, quantity (integer), and location. After, click the done button to add new material to inventory. 
* You can view entire inventory (list of materials) by cliking the 'Show inventory' button. 
* You can generate the first required action by clicking the 'remove material' button
    * Enter name of item, then click the 'done button' to remove from inventory
* You can generate the second  required action 'Show low level items' by cliking the button
* You can location my visual component by clicking the 'save to file' or 'load inventory' file buttons
* You can load and save application state to file functions by the button. Can verify the state of the file by using the show inventory button. 

## Phase 4: Task 2

Tue Aug 05 23:27:38 PDT 2025
Displayed empty inventory
Tue Aug 05 23:27:41 PDT 2025
Added new material Pochacco
Tue Aug 05 23:27:41 PDT 2025
Added new material Blueberries
Tue Aug 05 23:27:41 PDT 2025
Added new material Kuromi
Tue Aug 05 23:27:41 PDT 2025
Added new material Cheese
Tue Aug 05 23:27:41 PDT 2025
Added new material Guitar
Tue Aug 05 23:27:58 PDT 2025
Added new material Taylor Swift
Tue Aug 05 23:27:59 PDT 2025
Displayed inventory
Tue Aug 05 23:28:14 PDT 2025
Removed Kuromi
Tue Aug 05 23:28:15 PDT 2025
Displayed reordering list

## Phase 4 : Task 3
Looking at the design in the UML class diagram, it's relatively clean. With more time, I would refactor InventoryApp to be more structured and divided. Currently, it's comprised of many different functions and private classes, which can make the code hard to read and understand (i.e., not as intuitive to break down). Additionally, this could make testing and debugging more difficult when they appear on the program. One way to refactor the code would be to make the buttons (which are currently private classes) be a part of a larger abstract class. This could both reduce duplication in the code and make the code more intuitive conceptually. 

Moreover, I would refactor my code as I've personally found it difficult and unnecessarily time-consuming when finding methods in InventoryApp. By refactoring, the UML class diagram can represent the functionality of the program in a structured, sequential order. 

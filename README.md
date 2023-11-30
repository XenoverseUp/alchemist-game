# KU Alchemist Game
The final project of COMP302 course written in Java, using Swing.

## Instructions
- To clone the repository: <code>git clone https://github.com/comp-302-group-1/alchemist-game</code>
- To checkout to a branch: <code>git checkout {branch-name}</code>
- To open a new branch: <code>git branch {branch-name}</code>
- Branch only on **development** branch and merge back to it.
- Open pull request to merge your branch and never merge before code review.
- **Never** directly commit to the main branch as we will merge development into it only on deployment.

## How to push your code?
After cloning the repo first time, make sure your branch is development (<code>git checkout development</code>). On development branch, open a new branch based on your code (<code>git branch {branch-task-name}</code>) and checkout to it (<code>git checkout {branch-task-name}</code>). Make your development, commit and push your code using following commands:
1. git add .
2. git commit -m "commit-message"
3. git push -u origin branch-task-name

For commit messages use conventions indicated in [this](https://gist.github.com/qoomon/5dfcdf8eec66a051ecd85625518cfd13) document. Such as _fix-potion-brewing-area_. 


## Details of The Game
**_Potions(name, point value, unique recipes)_**
- Romictmce (10, ginger, zinc, porcini)
- Poigi-Joicie (7, sage, portobello)
- Bodion (13, dill, quartz, oyster)
- Plovee (2, cilantro, fluorite) 
- Poticon (6, sage, copper) 
- Pacice (9,  ginger)
- Roin (4, rosemary, magnesium, portobello)
- Rungton (15, cilantro, porcini)
- Potion (5, sage, oyster)
- Potie (11, dill, hedgehog)


## _Ingredients(name, identifier unique, color, value)_
		 	 	 							
### Herb 
- h_1:ginger, yellow, 3
- h_2: rosemary, green, 5
- h_3: cilantro, green, 9
- h_4: dill, green, 11
- h_5: sage, lilac, 13

### Mineral
- mi_1:copper, grey, 5
- mi_2:magnesium, white, 9
- mi_3:fluorite, white, 10
- mi_4:zinc, grey, 12
- mi_5:quartz, purple, 3

### Mushroom 
- mu_1:portobello, brown, 20 
- mu_2:porcini, yellow, 10
- mu_3:oyster, white, 9
- mu_4:hedgehog, brown, 2


### _Publication Cards(specific requirements, point value)_

- ARTFAIC: Requirement: player has more than 2 minerals in its inventory
- ARTFACC: Requirement: Player has value > 50
- MFCTIIC: Requirement: player has at least 1 herb in its inventory
- MCTICND: Requirement: player’s herb values sum > 7
- DEDRICD: Requirement: Player has made 3 mushrooms in its inventory
- MERIDUD: Requirement: player owns at least 1 mineral in its inventory





## _Artifact Cards(effect-type, ability)_

- Elixir of Insight: one-time effect; allows a player to view the top three cards of the ingredient deck and rearrange them in any order

- Philosopher’s Compass: one-time effect; Once per round, the player can swap the position of two alchemy markers on the Deduction Board. (one-time or once per round???)

- Request Potion: one-time effect; Player chooses getting one of the potions that another player has and adding their own inventory.

- Terminate the Player: one-time; player skips that round and does nothing.




## _Alchemy Markers_

- Player holds the position of its Alchemy Marker


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


## Ingredients(name, identifier unique, color, value, …)
		 	 	 							
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


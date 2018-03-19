# Bombecraft 2.0

## Table of content
 1. [Style Guide](#style-guide)
 2. [Features](#features)
 3. [Gui](#gui)
     1. [Components](#components)
         1. [Button](#button)
         2. [Checkbox](#Checkbox)
         3. [Option box](#option-box)
         4. [Select box](#select-box)
         5. [Multi select box](#multi-select-box)
         6. [Slider](#slider)
         7. [Panel](#slider)
         8. [Vertical scroll panel](#vertical-flow-layout)
         9. [Horizontal scroll panel](#horizontal-scroll-panel)
     2. [Layouts](#layouts)
         1. [Border layout](#border-layout)
         2. [Bounded layout](#bounded-layout)
         3. [Divided vertical layout](#divided-vertical-layout)
         4. [Vertical flow layout](#vertical-flow-layout)
         5. [Vertical layout](#vertical-layout)
 3. [Options](#options)
 3. [TODO](#todo)
## Style guide
 _TODO_
## Features
 - Particles
 - ViewManager (zoom, offset)
 - Map with chunks
 - Gui 2.0
 - Basic Client Server Messaging
 - State manager
 - Health bar
 - Explosions
 
## Implementation
### Managers
#### MainManager
 - Manage all managers
#### SceneManager
 - Manage bullets, bombs, particle emitters
#### ViewManager
 - Allow interaction with Game
 - Use offset and zoom
#### PostFxManager
 - Create and render all damages on maps
 - Render crates, death bodies, blood...
#### TaskManager
#### MapManager
 - Create, render and update map
#### BotManager
#### GuiManager
#### PlayerManager
 - Contains all player in game
 - all
## Gui
### Components
#### Button
#### Checkbox
#### Option box
#### Select box
#### Multi select box
 _TODO_
#### Slider
 _TODO_
#### Autocomplete multi select box
 _TODO_
#### Autocomplete select box
 _TODO_
#### Panel
#### Text input
 _TODO_
#### Vertical scroll panel
#### Horizontal scroll panel
 _TODO_
 
### Layouts
#### Border layout
#### Bounded layout
#### Divided vertical layout
#### Vertical flow layout
#### Vertical layout

### Options
 - Fullscreen - _TODO_

## TODO
 - Identify players by ID not by login
 - Allow toggle fullscreen
 - PostFX image divide into chunks
 - Fix line-block collision
 - Update item only if their distance is less than X chunks
 - Make chunks seamless 
### Demos
 - Missile demo - shot that follows the mouse
 - Map with enemies
 - Map with enemies + grid border collisionq

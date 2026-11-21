# Zona 2: Pueblo Abandonado y 4 Casas Explorables
fill 30 64 -24 80 64 24 minecraft:mud_bricks
fill 35 64 -18 75 64 18 minecraft:coarse_dirt
fill 44 64 -4 50 64 4 minecraft:mossy_cobblestone
fill 46 65 -1 48 66 1 minecraft:mossy_stone_bricks hollow
fill 47 64 0 47 65 0 minecraft:water
setblock 46 67 -1 minecraft:soul_lantern

# Casa 1: Don Aurelio (cofre + botón interactivo para leer Carta de Don Aurelio)
fill 34 65 -16 44 70 -8 minecraft:spruce_planks hollow
fill 34 65 -16 44 65 -8 minecraft:cobblestone
setblock 39 65 -8 minecraft:dark_oak_door[half=lower]
setblock 39 66 -8 minecraft:dark_oak_door[half=upper]
setblock 36 67 -8 minecraft:glass_pane
setblock 42 67 -8 minecraft:glass_pane
setblock 36 66 -14 minecraft:chest{Items:[{Slot:0b,id:"lallorona:carta_parroco",Count:1b},{Slot:1b,id:"minecraft:bread",Count:2b}]}
setblock 38 65 -14 minecraft:command_block{Command:"function lallorona:lore/book_2_carta_vecino"}
setblock 38 66 -14 minecraft:stone_button[face=floor]
setblock 42 66 -14 minecraft:candle[lit=true,candles=2]

# Casa 2: Velador (Llave de la Capilla)
fill 34 65 8 44 70 16 minecraft:dark_oak_planks hollow
setblock 39 65 8 minecraft:dark_oak_door[half=lower]
setblock 39 66 8 minecraft:dark_oak_door[half=upper]
setblock 42 66 14 minecraft:chest{Items:[{Slot:0b,id:"lallorona:llave_capilla",Count:1b}]}
setblock 36 66 14 minecraft:lantern

# Casas 3 y 4
fill 56 65 -16 68 71 -7 minecraft:cobblestone hollow
setblock 62 65 -7 minecraft:dark_oak_door[half=lower]
setblock 62 66 -7 minecraft:dark_oak_door[half=upper]
setblock 58 66 -14 minecraft:candle[lit=true,candles=3]

fill 56 65 7 68 71 16 minecraft:spruce_planks hollow
setblock 62 65 7 minecraft:dark_oak_door[half=lower]
setblock 62 66 7 minecraft:dark_oak_door[half=upper]
setblock 65 66 13 minecraft:cobweb

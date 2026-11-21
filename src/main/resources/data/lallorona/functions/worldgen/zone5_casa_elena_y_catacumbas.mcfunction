# Zona 5: Casa de Elena + Catacumbas Subterráneas con escalera abierta y 3 Sellos interactivos
fill 188 64 -36 225 64 0 minecraft:coarse_dirt
fill 194 65 -28 212 74 -12 minecraft:dark_oak_planks hollow
fill 194 69 -28 212 69 -12 minecraft:spruce_planks
setblock 194 65 -20 minecraft:dark_oak_door[half=lower]
setblock 194 66 -20 minecraft:dark_oak_door[half=upper]
setblock 208 66 -25 minecraft:chest{Items:[{Slot:0b,id:"lallorona:diario_elena",Count:1b}]}
setblock 206 65 -25 minecraft:command_block{Command:"function lallorona:lore/book_3_diario_elena"}
setblock 206 66 -25 minecraft:stone_button[face=floor]

# Catacumbas subterráneas (Y=55 a 62) con acceso abierto desde la casa
fill 196 55 -28 214 62 -12 minecraft:deepslate_tiles hollow
fill 208 62 -16 208 65 -16 minecraft:air
fill 208 56 -16 208 64 -16 minecraft:ladder[facing=west]
# 3 Sellos de culpa interactivos
setblock 199 56 -24 minecraft:amethyst_block
setblock 199 55 -23 minecraft:command_block{Command:"function lallorona:puzzles/catacomb_seal_break"}
setblock 199 56 -23 minecraft:stone_button[face=floor]
setblock 205 56 -24 minecraft:amethyst_block
setblock 205 55 -23 minecraft:command_block{Command:"function lallorona:puzzles/catacomb_seal_break"}
setblock 205 56 -23 minecraft:stone_button[face=floor]
setblock 211 56 -24 minecraft:amethyst_block
setblock 211 55 -23 minecraft:command_block{Command:"function lallorona:puzzles/catacomb_seal_break"}
setblock 211 56 -23 minecraft:stone_button[face=floor]
setblock 205 57 -20 minecraft:soul_lantern

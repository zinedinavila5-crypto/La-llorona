# Zona 3: Capilla con Acertijo de 3 Campanas con botones interactivos + Cementerio
fill 85 64 -65 130 64 -25 minecraft:podzol
fill 68 64 -45 92 64 -43 minecraft:gravel
fill 68 64 -45 70 64 0 minecraft:gravel

fill 92 65 -54 108 73 -38 minecraft:deepslate_bricks hollow
setblock 92 65 -46 minecraft:dark_oak_door[half=lower]
setblock 92 66 -46 minecraft:dark_oak_door[half=upper]
fill 104 65 -48 106 65 -44 minecraft:polished_deepslate
setblock 105 68 -48 minecraft:bell
setblock 105 68 -46 minecraft:bell
setblock 105 68 -44 minecraft:bell
# Botones interactivos del acertijo (Agua -> Noche -> Perdón)
setblock 105 64 -48 minecraft:command_block{Command:"function lallorona:puzzles/church_bell_step1"}
setblock 105 66 -48 minecraft:stone_button[face=floor]
setblock 105 64 -46 minecraft:command_block{Command:"function lallorona:puzzles/church_bell_step2"}
setblock 105 66 -46 minecraft:stone_button[face=floor]
setblock 105 64 -44 minecraft:command_block{Command:"function lallorona:puzzles/church_bell_step3"}
setblock 105 66 -44 minecraft:stone_button[face=floor]
setblock 104 66 -46 minecraft:candle[lit=true,candles=4]
fill 100 65 -52 100 66 -52 minecraft:iron_bars

fill 112 65 -60 126 66 -32 minecraft:iron_bars outline
setblock 115 65 -55 minecraft:mossy_cobblestone_wall
setblock 118 65 -55 minecraft:mossy_cobblestone_wall
setblock 121 65 -55 minecraft:mossy_cobblestone_wall
setblock 115 65 -45 minecraft:mossy_cobblestone_wall
setblock 121 65 -45 minecraft:mossy_cobblestone_wall
setblock 118 65 -40 minecraft:soul_lantern

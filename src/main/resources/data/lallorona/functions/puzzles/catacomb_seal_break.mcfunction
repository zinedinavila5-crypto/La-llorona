scoreboard players add #global ll_catacomb_seal 1
playsound minecraft:block.amethyst_cluster.break block @a ~ ~ ~ 1.2 0.7
execute if score #global ll_catacomb_seal matches 1 run tellraw @a [{"text":"[Sello de Culpa 1/3] ","color":"aqua"},{"text":"«No fue odio... fue desesperación en la crecida del río.»","color":"gray","italic":true}]
execute if score #global ll_catacomb_seal matches 2 run tellraw @a [{"text":"[Sello de Culpa 2/3] ","color":"aqua"},{"text":"«El pueblo cerró sus puertas mientras el agua se los llevaba.»","color":"gray","italic":true}]
execute if score #global ll_catacomb_seal matches 3 run tellraw @a [{"text":"[Sellos Rotos 3/3] ","color":"green","bold":true},{"text":"Recibes el Medallón de Plata y la Cinta de Cuna. Dirígete al Santuario del Río.","color":"white"}]
execute if score #global ll_catacomb_seal matches 3 run give @p lallorona:medallon_plata 1
execute if score #global ll_catacomb_seal matches 3 run give @p lallorona:reliquia_cuna 1

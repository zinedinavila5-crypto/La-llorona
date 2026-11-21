scoreboard players set #global ll_ritual_activo 0
scoreboard players set #global ll_ritual_timer 0

execute if score @s ll_cp matches 1 run tp @s 0 65 0
execute if score @s ll_cp matches 2 run tp @s 42 65 0
execute if score @s ll_cp matches 3 run tp @s 95 65 -45
execute if score @s ll_cp matches 4 run tp @s 140 65 8
execute if score @s ll_cp matches 5 run tp @s 196 65 -20
execute if score @s ll_cp matches 6 run tp @s 248 65 15

execute if score @s ll_cp matches 1..2 run tp @e[type=lallorona:la_llorona,limit=1] 36 65 -12
execute if score @s ll_cp matches 3 run tp @e[type=lallorona:la_llorona,limit=1] 112 65 -48
execute if score @s ll_cp matches 4 run tp @e[type=lallorona:la_llorona,limit=1] 165 65 16
execute if score @s ll_cp matches 5 run tp @e[type=lallorona:la_llorona,limit=1] 208 56 -24
execute if score @s ll_cp matches 6 run tp @e[type=lallorona:la_llorona,limit=1] 268 65 15

effect give @s minecraft:resistance 5 2 true
tellraw @s [{"text":"[Reintento] ","color":"aqua","bold":true},{"text":"Has regresado al último punto de control con tus objetos clave intactos.","color":"gray"}]

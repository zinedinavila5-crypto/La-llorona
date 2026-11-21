scoreboard players set @s ll_acto 2
scoreboard players set @s ll_cp 2
spawnpoint @s 42 65 0
title @s subtitle {"text":"Casas abandonadas y objetos en las calles","color":"gray","italic":true}
title @s title {"text":"ACTO II: VOCES ENTRE VENTANAS","color":"aqua"}
tellraw @s [{"text":"[Punto de Control Guardado] ","color":"green","bold":true},{"text":"Explora las viviendas de la plaza, recoge la Carta de Don Aurelio y la Llave de la Capilla, y sigue el camino de grava hacia el norte.","color":"white"}]
execute at @s run playsound lallorona:puerta_chirrido ambient @s ~8 ~1 ~-5 1.1 0.9
fill 38 66 -8 50 70 8 air replace minecraft:torch
data merge entity @e[type=lallorona:la_llorona,limit=1] {AggressionPhase:2}
advancement grant @s only lallorona:act2_pueblo

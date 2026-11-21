scoreboard players set @s ll_acto 4
scoreboard players set @s ll_cp 4
spawnpoint @s 140 65 8
title @s subtitle {"text":"No mires atrás cuando el agua suba","color":"dark_aqua","italic":true}
title @s title {"text":"ACTO IV: EL RÍO DE NIEBLA","color":"red"}
tellraw @s [{"text":"[Punto de Control Guardado] ","color":"green","bold":true},{"text":"¡La Llorona te persigue! Cruza el río y el pantano, recoge el Frasco con Agua del Río en el cofre de la orilla y llega a la Casa de Elena.","color":"white"}]
execute at @s run playsound lallorona:grito_sobrenatural hostile @s ~15 ~2 ~ 1.4 0.95
data merge entity @e[type=lallorona:la_llorona,limit=1] {AggressionPhase:3}
tp @e[type=lallorona:la_llorona,limit=1] 160 65 14
advancement grant @s only lallorona:act4_bosque

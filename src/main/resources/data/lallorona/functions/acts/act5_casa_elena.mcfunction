scoreboard players set @s ll_acto 5
scoreboard players set @s ll_cp 5
spawnpoint @s 196 65 -20
title @s subtitle {"text":"La verdad detrás de la maldición","color":"gray","italic":true}
title @s title {"text":"ACTO V: LA MEMORIA DE ELENA","color":"aqua"}
tellraw @s [{"text":"[Punto de Control Guardado] ","color":"green","bold":true},{"text":"Casa de Elena. Baja por la escalera trasera a las catacumbas subterráneas y activa los 3 Sellos de Amatista para recuperar el Medallón de Plata y la Cinta de Cuna.","color":"white"}]
execute at @s run playsound lallorona:pasos_madera ambient @s ~ ~4 ~ 1.3 0.9
data merge entity @e[type=lallorona:la_llorona,limit=1] {AggressionPhase:4}
tp @e[type=lallorona:la_llorona,limit=1] 206 56 -22
advancement grant @s only lallorona:act5_casa

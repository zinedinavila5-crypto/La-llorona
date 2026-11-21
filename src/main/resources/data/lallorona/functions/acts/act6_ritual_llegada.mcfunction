scoreboard players set @s ll_acto 6
scoreboard players set @s ll_cp 6
spawnpoint @s 248 65 15
title @s subtitle {"text":"Donde comenzó la maldición, debe terminar el lamento","color":"aqua","italic":true}
title @s title {"text":"ACTO VI: EL ÚLTIMO LAMENTO","color":"gold"}
tellraw @s [{"text":"[Punto de Control Guardado] ","color":"green","bold":true},{"text":"Santuario del Río. Pulsa el botón del altar central para iniciar el Ritual de Liberación y resiste 90 segundos sin atacarla.","color":"white"}]
data merge entity @e[type=lallorona:la_llorona,limit=1] {AggressionPhase:5}
tp @e[type=lallorona:la_llorona,limit=1] 268 65 15
advancement grant @s only lallorona:act6_santuario

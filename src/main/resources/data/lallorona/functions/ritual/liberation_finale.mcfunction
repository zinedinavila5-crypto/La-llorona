scoreboard players set #global ll_ritual_activo 2
data merge entity @e[type=lallorona:la_llorona,limit=1] {Liberated:1b,AggressionPhase:1}
stopsound @a * lallorona:musica_persecucion
weather clear
time set 23200
particle minecraft:end_rod 258 67 15 4.0 3.0 4.0 0.05 90 force
title @a times 25 120 40
title @a subtitle {"text":"«Al fin... puedo escucharlos en paz.»","color":"aqua","italic":true}
title @a title {"text":"EL ÚLTIMO LAMENTO","color":"gold"}
tellraw @a [{"text":"[Final — Liberación] ","color":"gold","bold":true},{"text":"Elena detiene su ataque y asciende envuelta en luz cálida. No la destruiste: le devolviste su nombre y rompiste la maldición.","color":"white"}]
advancement grant @a only lallorona:final_liberacion

scoreboard players set @s ll_acto 3
scoreboard players set @s ll_cp 3
spawnpoint @s 95 65 -45
title @s subtitle {"text":"El secreto enterrado bajo las campanas","color":"gray","italic":true}
title @s title {"text":"ACTO III: LA CAPILLA Y LAS TUMBAS","color":"aqua"}
tellraw @s [{"text":"[Punto de Control Guardado] ","color":"green","bold":true},{"text":"Capilla de San Mateo. Pulsa los botones del altar en el orden correcto: Agua (Izquierda) -> Noche (Centro) -> Perdón (Derecha) para obtener la Vela Bendita.","color":"white"}]
execute at @s run playsound lallorona:susurros_habitacion ambient @s ~ ~2 ~ 1.2 0.88
data merge entity @e[type=lallorona:la_llorona,limit=1] {AggressionPhase:2}
tp @e[type=lallorona:la_llorona,limit=1] 112 65 -48
advancement grant @s only lallorona:act3_capilla

scoreboard players set @a ll_acto 1
scoreboard players set @a ll_cp 1
scoreboard players set #global ll_ritual_activo 0
scoreboard players set #global ll_ritual_timer 0
scoreboard players set #global ll_bell_seq 0
scoreboard players set #global ll_catacomb_seal 0

tp @a 0 65 0
spawnpoint @a 0 65 0
gamemode adventure @a
clear @a
give @a lallorona:linterna_aceite 1
function lallorona:lore/book_1_llegada

title @a times 20 80 30
title @a subtitle {"text":"San Mateo del Río — 1924","color":"gray","italic":true}
title @a title {"text":"ACTO I: EL PUEBLO SILENCIOSO","color":"aqua"}
tellraw @a [{"text":"[Objetivo] ","color":"gold","bold":true},{"text":"Cruza el puente viejo sobre el río y entra a la plaza del pueblo abandonado.","color":"white"}]

execute at @a run playsound lallorona:lamento_lejano ambient @a ~25 ~2 ~10 1.2 0.92
advancement grant @a only lallorona:act1_llegada

kill @e[type=lallorona:la_llorona]
summon lallorona:la_llorona 36 65 -6 {AggressionPhase:1,CustomName:'"La Llorona"'}

execute if score #global ll_ritual_activo matches 1 run tellraw @a [{"text":"[Ritual] ","color":"gray"},{"text":"El ritual ya está en curso... ¡resiste!","color":"white"}]
execute unless score #global ll_ritual_activo matches 1 run scoreboard players set #global ll_ritual_activo 1
execute if score #global ll_ritual_timer matches 0 run title @a title {"text":"EL RITUAL HA COMENZADO","color":"aqua"}
execute if score #global ll_ritual_timer matches 0 run title @a subtitle {"text":"Sobrevive 90 segundos sin atacar a Elena","color":"gray"}
execute if score #global ll_ritual_timer matches 0 run data merge entity @e[type=lallorona:la_llorona,limit=1] {AggressionPhase:5}
execute if score #global ll_ritual_timer matches 0 at @a run playsound lallorona:grito_sobrenatural hostile @a ~ ~ ~ 1.8 0.9
execute if score #global ll_ritual_timer matches 0 at @a run playsound lallorona:musica_persecucion record @a ~ ~ ~ 1.4 1.0

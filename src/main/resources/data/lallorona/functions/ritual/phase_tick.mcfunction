scoreboard players add #global ll_ritual_timer 1
particle minecraft:soul_fire_flame 258 66.2 15 3.0 0.3 3.0 0.02 6 force
execute if score #global ll_ritual_timer matches 600 run tellraw @a [{"text":"[Ritual 33%] ","color":"aqua","bold":true},{"text":"La Vela Bendita ilumina la niebla... ¡Elena duda por un instante!","color":"white"}]
execute if score #global ll_ritual_timer matches 600 run playsound lallorona:lamento_lejano hostile @a 258 66 15 1.5 1.05
execute if score #global ll_ritual_timer matches 1200 run tellraw @a [{"text":"[Ritual 66%] ","color":"aqua","bold":true},{"text":"El Medallón de Plata resuena con los recuerdos de sus hijos. ¡Solo un poco más!","color":"white"}]
execute if score #global ll_ritual_timer matches 1200 run playsound lallorona:llanto_rio hostile @a 258 66 15 1.6 1.1
execute if score #global ll_ritual_timer matches 1800 run function lallorona:ritual/liberation_finale

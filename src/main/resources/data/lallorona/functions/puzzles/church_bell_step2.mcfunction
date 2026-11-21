execute if score #global ll_bell_seq matches 1 run scoreboard players set #global ll_bell_seq 2
execute if score #global ll_bell_seq matches 2 run playsound minecraft:block.bell.use block @a ~ ~ ~ 1.2 1.0
execute if score #global ll_bell_seq matches 2 run tellraw @a [{"text":"[Campana 2/3 — Noche] ","color":"aqua"},{"text":"Responde desde el campanario... falta la campana del Perdón.","color":"white"}]

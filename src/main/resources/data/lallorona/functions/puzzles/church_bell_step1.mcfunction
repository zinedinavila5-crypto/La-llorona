scoreboard players set #global ll_bell_seq 1
playsound minecraft:block.bell.use block @a ~ ~ ~ 1.2 0.8
tellraw @a [{"text":"[Campana 1/3 — Agua] ","color":"aqua"},{"text":"Resuena con claridad... ahora busca la campana de la Noche.","color":"white"}]

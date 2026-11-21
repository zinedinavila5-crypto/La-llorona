execute if score #global ll_bell_seq matches 2 run scoreboard players set #global ll_bell_seq 3
execute if score #global ll_bell_seq matches 3 run playsound minecraft:block.bell.resonate block @a ~ ~ ~ 1.5 1.15
execute if score #global ll_bell_seq matches 3 run setblock 100 65 -52 air
execute if score #global ll_bell_seq matches 3 run setblock 100 66 -52 air
execute if score #global ll_bell_seq matches 3 run give @p lallorona:vela_bendita 1
execute if score #global ll_bell_seq matches 3 run tellraw @a [{"text":"[Acertijo Resuelto] ","color":"green","bold":true},{"text":"La cripta se abre y obtienes la Vela Bendita del Altar.","color":"white"}]

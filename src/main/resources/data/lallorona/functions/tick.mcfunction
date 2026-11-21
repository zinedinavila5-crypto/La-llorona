scoreboard players add #global ll_ambient_cd 1
execute if score #global ll_ambient_cd matches 440.. run function lallorona:events/ambient_pulse
execute if score #global ll_ambient_cd matches 440.. run scoreboard players set #global ll_ambient_cd 0

execute as @a[x=32,y=-64,z=-18,dx=26,dy=128,dz=36] if score @s ll_acto matches 1 run function lallorona:acts/act2_pueblo
execute as @a[x=86,y=-64,z=-60,dx=28,dy=128,dz=32] if score @s ll_acto matches 2 run function lallorona:acts/act3_capilla
execute as @a[x=132,y=-64,z=-12,dx=38,dy=128,dz=50] if score @s ll_acto matches 3 run function lallorona:acts/act4_bosque_rio
execute as @a[x=188,y=-64,z=-36,dx=28,dy=128,dz=34] if score @s ll_acto matches 4 run function lallorona:acts/act5_casa_elena
execute as @a[x=242,y=-64,z=0,dx=30,dy=128,dz=30] if score @s ll_acto matches 5 run function lallorona:acts/act6_ritual_llegada

execute if score #global ll_ritual_activo matches 1 run function lallorona:ritual/phase_tick

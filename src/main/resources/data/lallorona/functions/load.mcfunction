scoreboard objectives add ll_acto dummy "Acto Actual"
scoreboard objectives add ll_cp dummy "Punto de Control"
scoreboard objectives add ll_ritual_timer dummy "Progreso Ritual"
scoreboard objectives add ll_ritual_activo dummy "Ritual Activo"
scoreboard objectives add ll_bell_seq dummy "Secuencia Campanas"
scoreboard objectives add ll_catacomb_seal dummy "Sellos Catacumbas"
scoreboard objectives add ll_ambient_cd dummy "Temporizador Ambiente"

gamerule doDaylightCycle false
gamerule doWeatherCycle false
gamerule doMobSpawning false
gamerule keepInventory false
gamerule commandBlockOutput false
time set 18000
weather rain

tellraw @a [{"text":"[La Llorona: El Último Lamento] ","color":"aqua","bold":true},{"text":"Cargado. En mundo Superplano ejecuta ","color":"gray"},{"text":"/function lallorona:worldgen/build_all","color":"yellow"},{"text":" y luego ","color":"gray"},{"text":"/function lallorona:acts/act1_start","color":"green"}]

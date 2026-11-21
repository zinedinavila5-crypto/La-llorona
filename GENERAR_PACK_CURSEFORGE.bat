@echo off
setlocal
echo ===============================================================
echo   CONSTRUCTOR AUTOMATICO - LA LLORONA: EL ULTIMO LAMENTO
echo   Genera el archivo UNICO LaLlorona_CurseForge_LISTO.zip
echo ===============================================================

where ffmpeg >nul 2>nul || (echo [ERROR] Instala FFmpeg primero: winget install Gyan.FFmpeg & pause & exit /b 1)
where gradle >nul 2>nul || (echo [ERROR] Instala Gradle 8.8 primero: winget install Gradle.Gradle & pause & exit /b 1)

echo [1/3] Convirtiendo 12 audios WAV a OGG espaciales (mono)...
if not exist "src\main\resources\assets\lallorona\sounds" mkdir "src\main\resources\assets\lallorona\sounds"
for %%f in (audio_masters_wav\*.wav) do (
    ffmpeg -y -loglevel error -i "%%f" -ac 1 -c:a libvorbis -q:a 5 "src\main\resources\assets\lallorona\sounds\%%~nf.ogg"
)

echo [2/3] Compilando LaLlorona.jar con Forge 1.20.1 y Java 17...
call gradle build --no-daemon
if errorlevel 1 (echo [ERROR] Fallo la compilacion de Gradle. Revisa que tengas Java 17 activo. & pause & exit /b 1)

echo [3/3] Empaquetando UN SOLO ZIP listo para CurseForge...
if exist dist_cf rmdir /s /q dist_cf
mkdir dist_cf\overrides\mods
copy /y build\libs\LaLlorona-*.jar dist_cf\overrides\mods\LaLlorona.jar >nul
copy /y curseforge_manifest.json dist_cf\manifest.json >nul

powershell -NoProfile -Command "Compress-Archive -Path 'dist_cf\manifest.json','dist_cf\overrides' -DestinationPath 'LaLlorona_CurseForge_LISTO.zip' -Force"

echo.
echo ===============================================================
echo   LISTO! Se creo el archivo unico: LaLlorona_CurseForge_LISTO.zip
echo   Importalo directo en CurseForge -> Import Profile, agrega GeckoLib y juega.
echo ===============================================================
pause

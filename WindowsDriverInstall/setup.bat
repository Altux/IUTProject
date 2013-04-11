echo "Mise en place du driver..."
unzip.vbs
driver\%PROCESSOR_ARCHITECTURE%\install-filter.exe install --inf=driver\VTPlayer.inf

echo "Mise en place du processus gérant les mouvements de la souris..."
MKDIR "%ProgramFiles%\VTPlayerMouse"
COPY vtpServer.exe "%ProgramFiles%\VTPlayerMouse\vtpServer.exe"
COPY lib_vtplayer.dll "%ProgramFiles%\VTPlayerMouse\lib_vtplayer.dll"
REG ADD HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows\CurrentVersion\Run /v VTPlayer /t REG_SZ /d "%ProgramFiles%\VTPlayerMouse\vtpServer.exe"
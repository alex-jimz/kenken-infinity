@ECHO OFF

IF "%1"=="" (
    echo Please provide the name of a Juego de Prueba or "help"
    echo Usage: ./juegoDePrueba.bat name
    exit /B 1
)
SET name=%1

IF "%name%" == "help" (
    echo Usage: ./juegoDePrueba.bat name
    echo List of Juegos de Prueba:
    echo 49UsersRanking
    echo 50UsersRanking
    echo 51UsersRanking
    echo ContinueGame
    echo Default
    echo Empty
    echo EndGameNoHints
    echo EndGameUsingHints
    echo EndGameWithDifferentTimes
    echo Just5Users
    echo RankingSorted
    echo ResetGame
) ELSE (
    Xcopy .\JuegosDePrueba\%name%\Game.json .\Data\Game.json /Y
    Xcopy .\JuegosDePrueba\%name%\Kenken.json .\Data\Kenken.json /Y
    Xcopy .\JuegosDePrueba\%name%\Ranking.json .\Data\Ranking.json /Y
    Xcopy .\JuegosDePrueba\%name%\User.json .\Data\User.json /Y

    call .\kenkenInfinity.bat
)

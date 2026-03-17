#!/bin/bash

# Function to print usage and help
print_help() {
    echo "Please provide the name of a Juego de Prueba or 'help'"
    echo "Usage: ./juegoDePrueba.sh name"
    exit 1
}

# Check if an argument is provided
if [ -z "$1" ]; then
    print_help
fi

name="$1"

if [ "$name" == "help" ]; then
    echo "Usage: ./juegoDePrueba.sh name"
    echo "List of Juegos de Prueba:"
    echo "49UsersRanking"
    echo "50UsersRanking"
    echo "51UsersRanking"
    echo "ContinueGame"
    echo "Default"
    echo "Empty"
    echo "EndGameNoHints"
    echo "EndGameUsingHints"
    echo "EndGameWithDifferentTimes"
    echo "Just5Users"
    echo "RankingSorted"
    echo "ResetGame"
else
    cp "./JuegosDePrueba/$name/Game.json" "./Data/Game.json"
    cp "./JuegosDePrueba/$name/Kenken.json" "./Data/Kenken.json"
    cp "./JuegosDePrueba/$name/Ranking.json" "./Data/Ranking.json"
    cp "./JuegosDePrueba/$name/User.json" "./Data/User.json"

    ./kenkenInfinity.sh
fi

import { useParams } from "@solidjs/router";
import { createEffect, createSignal } from "solid-js";

import RenderGame from "./RenderGame";
import FormGame from "./action-form/FormGame";

import './Game.css';

function Game() {

    const { gameId } = useParams();

    const [game, setGame] = createSignal({
        status: '',
        turn: 0,
        gameboard: Array.from({ length: 12 }, () => Array(18))
    });

    createEffect(() => {

        const fetchGame = async () => {
            const response = await fetch(`http://localhost:8080/game/${gameId}`);
            const json = await response.json();
            setGame(json);
        };

        fetchGame();
    }, [gameId]);

    return (
        <div class='game-container'>
            <h1 class='turn-heading'>Turn: {game().turn}</h1>

            <div class='content-wrapper'>
                <div class='table-wrapper'><RenderGame game={game()} /></div>
                <div class='form-wrapper'><FormGame game={game()} setGame={setGame} gameId={gameId} /></div>
            </div>
        </div>
    );
}

export default Game;
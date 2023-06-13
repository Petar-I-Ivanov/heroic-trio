import { useParams } from "@solidjs/router";
import { createEffect, createSignal } from "solid-js";

import RenderGame from "./RenderGame";
import FormGame from "./game-form/FormGame";

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
        <div style='display:flex; justify-content: center; align-items: center; height: 100vh;'>
            {/* <h1>Turn: {game().turn}</h1> */}
            <RenderGame game={game()} />
            <FormGame game={game()} setGame={setGame} gameId={gameId} />
        </div>
    );
}

export default Game;
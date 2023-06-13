import { useNavigate, useParams } from "@solidjs/router";
import { createEffect, createSignal } from "solid-js";

import './WelcomeResult.css';

function Result() {

    const { gameId } = useParams();
    const navigate = useNavigate();

    const [game, setGame] = createSignal({
        status: '',
        turn: 0
    });

    createEffect(() => {

        const fetchGame = async () => {
            const response = await fetch(`http://localhost:8080/game/${gameId}`);
            const json = await response.json();
            setGame(json);
        };

        fetchGame();
    }, [gameId])

    async function startNewGame() {

        const response = await fetch('http://localhost:8080/game', { method: 'POST' });
        const game = await response.json();
        navigate(`/game/${game.id}`);
    }

    function goBack() {
        navigate('/');
    }

    return (
        <div class='result-container'>
            <h1 class='result-heading'>You <span class='span-info'>{game().status}</span> the game in <span class='span-info'>{game().turn}</span> turns!</h1>

            <button class='start-button' onClick={goBack}>Go Back</button>
            <button class='start-button' onClick={startNewGame}>Start New Game</button>
        </div>
    );
}

export default Result;
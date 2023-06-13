import { useNavigate } from "@solidjs/router";
import './Welcome.css';

function Welcome() {

    const navigate = useNavigate();

    async function startNewGame() {

        const response = await fetch('http://localhost:8080/game', { method: 'POST' });
        const game = await response.json();
        navigate(`/game/${game.id}`);
    }

    return (
        <div class='welcome-container'>
            <h1 class='welcome-heading'>Welcome to Heroic Trio</h1>
            <button class='start-button' onClick={startNewGame}>Start New Game</button>
        </div>
    );
}

export default Welcome;
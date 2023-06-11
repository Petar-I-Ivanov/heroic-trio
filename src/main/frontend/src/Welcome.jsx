import { useNavigate } from "@solidjs/router";

function Welcome() {

    const navigate = useNavigate();

    async function startNewGame() {

        const response = await fetch('http://localhost:8080/game', { method: 'POST' });
        const game = await response.json();
        navigate(`/game/${game.id}`);
    }

    return (
        <div>
            <h1>Welcome to Heroic Trio</h1>
            <button onClick={startNewGame}>Start New Game</button>
        </div>
    );
}

export default Welcome;
import { useNavigate, useParams } from "@solidjs/router";
import { createEffect, createSignal } from "solid-js";
import RenderGame from "./RenderGame";

function Game() {

    const { gameId } = useParams();
    const navigate = useNavigate();

    const [heroPick, setHeroPick] = createSignal();
    const [direction, setDirection] = createSignal();
    const [game, setGame] = createSignal({
        status: '',
        turn: 0,
        gameboard: Array.from({ length: 18 }, () => Array(12))
    });

    createEffect(() => {

        const fetchGame = async () => {
            const response = await fetch(`http://localhost:8080/game/${gameId}`);
            const json = await response.json();
            setGame(json);
        };

        fetchGame();
    }, [gameId])

    async function makeAction(e) {

        e.preventDefault();

        if (!heroPick || !direction) {
            return;
        }

        const actionValue = e.target.action.value;
        console.log(actionValue)
        const response = await fetch(`http://localhost:8080/game/${gameId}`, {
            method: 'PUT',
            headers: { 'content-type': 'application/json' },
            body: JSON.stringify({ heroPick: heroPick(), direction: direction() })
        });

        const json = await response.json();
        setGame(json);

        setHeroPick();
        setDirection();
    }

    function onHeroPickChange(e) {
        setHeroPick(e.target.value);
        console.log(heroPick());
    }

    function onDirectionChange(e) {
        setDirection(e.target.value);
    }

    return (
        <div>
            <RenderGame game={game()} />

            <form class='game-form' onSubmit={makeAction}>

                {!heroPick() && <> <label for="heroPick">Pick Hero:</label> <input onChange={onHeroPickChange} type='text' id='heroPick' maxLength='1' autofocus /></>}
                {(heroPick() && !direction()) && <> <label for="direction">Direction:</label> <input onChange={onDirectionChange} type='text' id='direction' maxLength='1' autofocus /></>}
                <button type='submit'>Submit</button>
            </form>
        </div>
    );
}

export default Game;
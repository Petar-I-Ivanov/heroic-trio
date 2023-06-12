import { useNavigate, useParams } from "@solidjs/router";
import { createEffect, createSignal } from "solid-js";
import RenderGame from "./RenderGame";
import SetHeroPick from "./inputs/SetHeroPick";
import SetAction from "./inputs/SetAction";
import SetMovementScenario from "./inputs/scenarios/SetMovementScenario";
import SetGnomeAbilityScenario from "./inputs/scenarios/SetGnomeAbilityScenario";
import SetDwarfAbilityScenario from "./inputs/scenarios/SetDwarfAbilityScenario";
import SetWizardAbilityScenario from "./inputs/scenarios/SetWizardAbilityScenario";

function Game() {

    const { gameId } = useParams();
    const navigate = useNavigate();

    const [heroPick, setHeroPick] = createSignal();
    const [action, setAction] = createSignal();

    const [direction, setDirection] = createSignal();

    const [rowOne, setRowOne] = createSignal(-1);
    const [colOne, setColOne] = createSignal(-1);

    const [rowTwo, setRowTwo] = createSignal(-1);
    const [colTwo, setColTwo] = createSignal(-1);

    const [numberOfSquares, setNumberOfSquares] = createSignal(-1);
    const [sortType, setSortType] = createSignal();

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
    }, [gameId])

    async function makeAction(e) {

        e.preventDefault();

        const body = {
            heroPick: heroPick(),
            action: action(),
            direction: direction(),
            rowOne: rowOne(),
            colOne: colOne(),
            rowTwo: rowTwo(),
            colTwo: colTwo(),
            numberOfSquares: numberOfSquares(),
            sortType: sortType()
        }

        const response = await fetch(`http://localhost:8080/game/${gameId}`, {
            method: 'PUT',
            headers: { 'content-type': 'application/json' },
            body: JSON.stringify(body)
        });

        if (response.status === 200) {
        
            const json = await response.json();
            setGame(json);

            setHeroPick(); setAction(); setDirection(); setRowOne(-1);
            setColOne(-1); setRowTwo(-1); setColTwo(-1); setNumberOfSquares(-1);
            setSortType();
        }
    }

    return (
        <div>
            <RenderGame game={game()} />

            <form class='game-form' onSubmit={makeAction}>

                {!heroPick() && <SetHeroPick setHeroPick={setHeroPick} />}
                {!action() && <SetAction setAction={setAction} />}

                {isMovementScenario(action()) && <SetMovementScenario direction={direction} setDirection={setDirection} />}

                {isGnomeAbilityScenario(action(), heroPick()) && <SetGnomeAbilityScenario direction={direction}
                    setDirection={setDirection} numberOfSquares={numberOfSquares} setNumberOfSquares={setNumberOfSquares} />}

                {isDwarfAbilityScenario(action(), heroPick()) && <SetDwarfAbilityScenario rowOne={rowOne} setRowOne={setRowOne}
                    colOne={colOne} setColOne={setColOne} rowTwo={rowTwo} setRowTwo={setRowTwo} colTwo={colTwo} setColTwo={setColTwo} />}

                {isWizardAbilityScenario(action(), heroPick()) && <SetWizardAbilityScenario rowOne={rowOne} setRowOne={setRowOne}
                    colOne={colOne} setColOne={setColOne} rowTwo={rowTwo} setRowTwo={setRowTwo} colTwo={colTwo} setColTwo={setColTwo}
                    sortType={sortType} setSortType={setSortType} />}

                <button type='submit'>Submit</button>
            </form>
        </div>
    );
}

export default Game;

function isMovementScenario(action) {
    return action === 'movement';
}

function isGnomeAbilityScenario(action, heroPick) {
    return action === 'ability' && heroPick === '1';
}

function isDwarfAbilityScenario(action, heroPick) {
    return action === 'ability' && heroPick === '2';
}

function isWizardAbilityScenario(action, heroPick) {
    return action === 'ability' && heroPick === '3';
}
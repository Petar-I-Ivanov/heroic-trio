import { createSignal } from "solid-js";

import SetDwarfAbilityScenario from "./inputs/scenarios/SetDwarfAbilityScenario";
import SetGnomeAbilityScenario from "./inputs/scenarios/SetGnomeAbilityScenario";
import SetMovementScenario from "./inputs/scenarios/SetMovementScenario";
import SetWizardAbilityScenario from "./inputs/scenarios/SetWizardAbilityScenario";

import SetAction from "./inputs/SetAction";
import SetHeroPick from "./inputs/SetHeroPick";

function FormGame(props) {

    const [heroPick, setHeroPick] = createSignal();
    const [action, setAction] = createSignal();

    const [direction, setDirection] = createSignal();

    const [rowOne, setRowOne] = createSignal(-1);
    const [colOne, setColOne] = createSignal(-1);

    const [rowTwo, setRowTwo] = createSignal(-1);
    const [colTwo, setColTwo] = createSignal(-1);

    const [numberOfSquares, setNumberOfSquares] = createSignal(-1);
    const [sortType, setSortType] = createSignal();

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

        const response = await fetch(`http://localhost:8080/game/${props.gameId}`, {
            method: 'PUT',
            headers: { 'content-type': 'application/json' },
            body: JSON.stringify(body)
        });

        if (response.status === 200) {
            const json = await response.json();
            props.setGame(json);
        }

        setHeroPick(); setAction(); setDirection(); setRowOne(-1);
        setColOne(-1); setRowTwo(-1); setColTwo(-1); setNumberOfSquares(-1);
        setSortType();
    }

    return (
        <form style='flex: 1; margin-right: 10px;' onSubmit={makeAction}>

            {!heroPick() && <SetHeroPick game={props.game} setHeroPick={setHeroPick} />}
            {!action() && <SetAction setAction={setAction} />}

            {isMovementScenario(action()) && <SetMovementScenario direction={direction} setDirection={setDirection} />}

            {isGnomeAbilityScenario(action(), heroPick()) && <SetGnomeAbilityScenario direction={direction}
                setDirection={setDirection} numberOfSquares={numberOfSquares} setNumberOfSquares={setNumberOfSquares} />}

            {isDwarfAbilityScenario(action(), heroPick()) && <SetDwarfAbilityScenario rowOne={rowOne} setRowOne={setRowOne}
                colOne={colOne} setColOne={setColOne} rowTwo={rowTwo} setRowTwo={setRowTwo} colTwo={colTwo} setColTwo={setColTwo} />}

            {isWizardAbilityScenario(action(), heroPick()) && <SetWizardAbilityScenario rowOne={rowOne} setRowOne={setRowOne}
                colOne={colOne} setColOne={setColOne} rowTwo={rowTwo} setRowTwo={setRowTwo} colTwo={colTwo} setColTwo={setColTwo}
                sortType={sortType} setSortType={setSortType} />}

            <br/>
            <button type='submit'>Submit</button>
        </form>
    );
}

export default FormGame;


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
import { useNavigate } from "@solidjs/router";
import { createSignal } from "solid-js";

import SetDwarfAbilityScenario from "./inputs/scenarios/SetDwarfAbilityScenario";
import SetGnomeAbilityScenario from "./inputs/scenarios/SetGnomeAbilityScenario";
import SetMovementScenario from "./inputs/scenarios/SetMovementScenario";
import SetWizardAbilityScenario from "./inputs/scenarios/SetWizardAbilityScenario";

import SetAction from "./inputs/SetAction";
import SetHeroPick from "./inputs/SetHeroPick";

import './FormGame.css';

function FormGame(props) {

    const navigate = useNavigate();
    const [error, setError] = createSignal();

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
            heroPick: heroPick(), action: action(), direction: direction(),
            rowOne: rowOne(), colOne: colOne(), rowTwo: rowTwo(), colTwo: colTwo(),
            numberOfSquares: numberOfSquares(), sortType: sortType()
        }

        setHeroPick(); setAction(); setDirection(); setRowOne(-1); setColOne(-1);
        setRowTwo(-1); setColTwo(-1); setNumberOfSquares(-1); setSortType();

        const response = await fetch(`http://localhost:8080/game/${props.gameId}`, {
            method: 'PUT',
            headers: { 'content-type': 'application/json' },
            body: JSON.stringify(body)
        });

        if (response.status !== 200) {
            const text = await response.text();
            setError(text);
            return;
        }

        const json = await response.json();

        console.log(json.status);
        if (json.status === 'WON' || json.status === 'LOST') {
            navigate(`/result/${json.id}`);
        }

        props.setGame(json);
        setError();
    }

    function isMovementScenario() {
        return action() === 'movement' && heroPick();
    }
    
    function isGnomeAbilityScenario() {
        return action() === 'ability' && heroPick() === '1';
    }
    
    function isDwarfAbilityScenario() {
        return action() === 'ability' && heroPick() === '2';
    }
    
    function isWizardAbilityScenario() {
        return action() === 'ability' && heroPick() === '3';
    }

    function isMovementScenarioFilled() {
        return direction();
    }

    function isGnomeAbilityScenarioFilled() {
        return direction() && numberOfSquares() !== -1;
    }

    function isDwarfAbilityScenarioFilled() {
        return (rowOne() !== -1 && colOne() !== -1)
            && (rowTwo() !== -1 && colTwo() !== -1);
    }

    function isWizardAbilityScenarioFilled() {
        return (rowOne() !== -1 && colOne() !== -1)
            && (rowTwo() !== -1 && colTwo() !== -1)
            && sortType();
    }

    function isAnyScenarioAvailable() {
        return isMovementScenarioAndFilled() || isGnomeAbilityScenarioAndFilled()
            || isDwarfAbilityScenarioAndFilled() || isWizardAbilityScenarioAndFilled();
    }

    function isMovementScenarioAndFilled() {
        return isMovementScenario() && isMovementScenarioFilled();
    }

    function isGnomeAbilityScenarioAndFilled() {
        return isGnomeAbilityScenario() && isGnomeAbilityScenarioFilled();
    }

    function isDwarfAbilityScenarioAndFilled() {
        return isDwarfAbilityScenario() && isDwarfAbilityScenarioFilled();
    }

    function isWizardAbilityScenarioAndFilled() {
        return isWizardAbilityScenario() && isWizardAbilityScenarioFilled();
    }

    function isMovementScenarioAndNotFilled() {
        return isMovementScenario() && !isMovementScenarioFilled();
    }

    function isGnomeAbilityScenarioAndNotFilled() {
        return isGnomeAbilityScenario() && !isGnomeAbilityScenarioFilled();
    }

    function isDwarfAbilityScenarioAndNotFilled() {
        return isDwarfAbilityScenario() && !isDwarfAbilityScenarioFilled();
    }

    function isWizardAbilityScenarioAndNotFilled() {
        return isWizardAbilityScenario() && !isWizardAbilityScenarioFilled();
    }

    return (
        <form class='form' onSubmit={makeAction}>

            {!heroPick() && <SetHeroPick game={props.game} setHeroPick={setHeroPick} />}
            {!action() && <SetAction setAction={setAction} />}

            {isMovementScenarioAndNotFilled() && <SetMovementScenario setDirection={setDirection} />}

            {isGnomeAbilityScenarioAndNotFilled() && <SetGnomeAbilityScenario setDirection={setDirection} setNumberOfSquares={setNumberOfSquares} />}

            {isDwarfAbilityScenarioAndNotFilled() && <SetDwarfAbilityScenario setRowOne={setRowOne} setColOne={setColOne}
            setRowTwo={setRowTwo} setColTwo={setColTwo} />}

            {isWizardAbilityScenarioAndNotFilled() && <SetWizardAbilityScenario setRowOne={setRowOne} setColOne={setColOne}
            setRowTwo={setRowTwo} setColTwo={setColTwo} setSortType={setSortType} />}

            
            {error() && <p class='error-message'>{error()}</p>}
            {isAnyScenarioAvailable() && <button class='submit-button' type='submit'>Submit</button>}
        </form>
    );
}

export default FormGame;
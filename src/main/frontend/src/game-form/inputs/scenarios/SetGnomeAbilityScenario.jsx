import SetDirection from "../SetDirection";
import SetNumberOfSquares from "../SetNumberOfSquares";

function SetGnomeAbilityScenario(props) {

    return (
        <>
            {!props.direction() && <SetDirection setDirection={props.setDirection} />}
            {props.numberOfSquares() === -1 && <SetNumberOfSquares setNumberOfSquares={props.setNumberOfSquares} />}
        </>
    );
}

export default SetGnomeAbilityScenario;
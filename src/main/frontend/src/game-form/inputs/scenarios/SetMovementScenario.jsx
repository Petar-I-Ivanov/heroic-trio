import SetDirection from "../SetDirection";
import '../Input.css';

function SetMovementScenario(props) {

    return (
        <SetDirection setDirection={props.setDirection} />
    );
}

export default SetMovementScenario;
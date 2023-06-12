import SetDirection from "../SetDirection";

function SetMovementScenario(props) {

    return(
        <>
        {!props.direction() && <SetDirection setDirection={props.setDirection} />}
        </>
    );
}

export default SetMovementScenario;
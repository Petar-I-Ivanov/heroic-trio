import SetDirection from '../SetDirection';
import SetNumberOfSquares from '../SetNumberOfSquares';

import '../Input.css';

function SetGnomeAbilityScenario(props) {

    return (
        <>
            <SetDirection setDirection={props.setDirection} />
            <SetNumberOfSquares setNumberOfSquares={props.setNumberOfSquares} />
        </>
    );
}

export default SetGnomeAbilityScenario;
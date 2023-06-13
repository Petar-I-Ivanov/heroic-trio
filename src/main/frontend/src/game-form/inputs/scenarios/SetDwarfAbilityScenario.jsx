import SetCol from "../SetCol";
import SetRow from "../SetRow";

import '../Input.css';

function SetDwarfAbilityScenario(props) {

    return(
        <>
            <h3>Input first position to swap</h3>
            <SetRow setRow={props.setRowOne} />
            <SetCol setCol={props.setColOne} />

            <h3>Input second position to swap</h3>
            <SetRow setRow={props.setRowTwo} />
            <SetCol setCol={props.setColTwo} />
        </>
    );
}

export default SetDwarfAbilityScenario;
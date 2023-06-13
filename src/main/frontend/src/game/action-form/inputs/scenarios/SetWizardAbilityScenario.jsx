import SetCol from '../SetCol';
import SetRow from '../SetRow';
import SetSortType from '../SetSortType';

import '../Input.css';

function SetWizardAbilityScenario(props) {

    return(
        <>
            <h3>Input first position to swap</h3>
            <SetRow setRow={props.setRowOne} />
            <SetCol setCol={props.setColOne} />

            <h3>Input second position to swap</h3>
            <SetRow setRow={props.setRowTwo} />
            <SetCol setCol={props.setColTwo} />

            <SetSortType setSortType={props.setSortType} />
        </>
    );
}

export default SetWizardAbilityScenario;
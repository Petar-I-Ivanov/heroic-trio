import SetCol from "../SetCol";
import SetRow from "../SetRow";
import SetSortType from "../SetSortType";

function SetWizardAbilityScenario(props) {

    return(
        <>
            {props.rowOne() === -1 || props.colOne() === -1 && <h3>Input first position to swap</h3>}
            {props.rowOne() === -1 && <SetRow setRow={props.setRowOne} />}
            {props.colOne() === -1 && <SetCol setCol={props.setColOne} />}

            {props.rowTwo() === -1 || props.colTwo() === -1 && <h3>Input second position to swap</h3>}
            {props.rowTwo() === -1 && <SetRow setRow={props.setRowTwo} />}
            {props.colTwo() === -1 && <SetCol setCol={props.setColTwo} />}

            {!props.sortType() && <SetSortType setSortType={props.setSortType} />}
        </>
    );
}

export default SetWizardAbilityScenario;
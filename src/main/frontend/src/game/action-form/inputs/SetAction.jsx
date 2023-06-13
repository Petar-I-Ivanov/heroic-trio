import './Input.css';

function SetAction(props) {

    function onChange(event) {
        props.setAction(event.target.value);
    }

    return (
        <>
            <h3 class='heading'>Pick action:</h3>

            <input class='input-radio' type='radio' id='movement' name='action' value="movement" onChange={onChange} />
            <label class='labeling' for='movement'>Movement</label>

            <input class='input-radio' type='radio' id='ability' name='action' value="ability" onChange={onChange} />
            <label class='labeling' for='ability'>Ability</label>
        </>
    );
}

export default SetAction;
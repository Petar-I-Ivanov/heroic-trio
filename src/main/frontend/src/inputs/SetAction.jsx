function SetAction(props) {

    function onChange(event) {
        props.setAction(event.target.value);
    }

    return (
        <>
            <h3>Pick action:</h3>

            <input type='radio' id='movement' name='action' value="movement" onChange={onChange} />
            <label for='movement'>Movement</label>

            <input type='radio' id='ability' name='action' value="ability" onChange={onChange} />
            <label for='ability'>Ability</label>
        </>
    );
}

export default SetAction;
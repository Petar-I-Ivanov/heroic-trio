function SetDirection(props) {

    function onChange(event) {
        props.setDirection(event.target.value);
    }

    return (
        <>
            <h3 class='heading'>Pick Direction:</h3>

            <input type='radio' id='forward' name='direction' value="w" onChange={onChange} />
            <label for='forward'>Forward</label>

            <input type='radio' id='right' name='direction' value="d" onChange={onChange} />
            <label for='right'>Right</label>

            <input type='radio' id='backward' name='direction' value="s" onChange={onChange} />
            <label for='backward'>Backward</label>

            <input type='radio' id='left' name='direction' value="a" onChange={onChange} />
            <label for='left'>Left</label>
        </>
    );
}

export default SetDirection;
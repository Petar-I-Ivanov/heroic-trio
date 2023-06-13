function SetRow(props) {

    function onChange(event) {
        props.setRow(event.target.value);
    }

    return(
        <>
            <label for='row'>Input row:</label>
            <input id='row' type='number' min={0} max={12} onChange={onChange} />
        </>
    );
}

export default SetRow;
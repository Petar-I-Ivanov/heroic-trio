function SetCol(props) {

    function onChange(event) {
        props.setCol(event.target.value);
    }

    return(
        <>
            <label for='col'>Input column:</label>
            <input id='col' type='number' min={0} max={18} onChange={onChange} />
        </>
    );
}

export default SetCol;
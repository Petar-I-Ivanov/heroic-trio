function SetNumberOfSquares(props) {

    function onChange(event) {
        props.setNumberOfSquares(event.target.value);
    }

    return (
        <>
            <h3>Pick Number Of Squares:</h3>

            <input type='radio' id='twoSquares' name='numberOfSquares' value={2} onChange={onChange} />
            <label for='twoSquares'>Two squares</label>

            <input type='radio' id='threeSquares' name='numberOfSquares' value={3} onChange={onChange} />
            <label for='threeSquares'>Three squares</label>
        </>
    );
}

export default SetNumberOfSquares;
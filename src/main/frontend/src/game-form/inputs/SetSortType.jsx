function SetSortType(props) {

    function onChange(event) {
        props.setSortType(event.target.value);
    }

    return (
        <>
            <h3 class='heading'>Pick Sort Type:</h3>

            <input type='radio' id='ascending' name='sortType' value="ascending" onChange={onChange} />
            <label for='ascending'>Ascending</label>

            <input type='radio' id='descending' name='sortType' value="descending" onChange={onChange} />
            <label for='descending'>Descending</label>
        </>
    );
}

export default SetSortType;
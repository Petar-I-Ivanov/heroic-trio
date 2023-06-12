function SetHeroPick(props) {

    function onChange(event) {
        props.setHeroPick(event.target.value);
    }

    return (
        <>
            <h3>Pick hero:</h3>

            {!props.game.gnomeUsed && <>
            <input type='radio' id='gnome' name='heroPick' value="1" onChange={onChange} />
            <label for='gnome'>Gnome</label>
            </>}

            {!props.game.dwarfUsed && <>
            <input type='radio' id='dwarf' name='heroPick' value="2" onChange={onChange} />
            <label for='dwarf'>Dwarf</label>
            </>}

            {!props.game.wizardUsed && <>
            <input type='radio' id='wizard' name='heroPick' value="3" onChange={onChange} />
            <label for='wizard'>Wizard</label>
            </>}
        </>
    );
}

export default SetHeroPick;
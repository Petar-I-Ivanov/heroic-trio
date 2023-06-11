function RenderGame(props) {

    return (
        <table>
            <tbody>
                {props.game.gameboard.map((row, rowIndex) => (
                    <tr key={rowIndex}>
                        {row.map((col, colIndex) => (
                            <td key={`${rowIndex}-${colIndex}`}>
                                <p>{col}</p>
                            </td>
                        ))}
                    </tr>
                ))}
            </tbody>
        </table>
    );
}

export default RenderGame;
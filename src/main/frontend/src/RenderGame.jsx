import GImage from './assets/gnome.jpg';
import DImage from './assets/dwarf.jpg';
import WImage from './assets/wizard.jpg';

import EImage from './assets/empty.jpg';
import XImage from './assets/boss.jpg';

function RenderGame(props) {

    const images = { 'G': GImage, 'D': DImage, 'W': WImage, 'E': EImage, 'X': XImage };
    const titles = { 'G': 'Gnome', 'D': 'Dwarf', 'W': 'Wizard', 'E': 'Empty Corner', 'X': 'Boss' };

    return (
        <table style='flex: 1; margin-right: 10px;'>
            <tbody>
                {props.game.gameboard.map((row, rowIndex) => (
                    <tr key={rowIndex}>
                        {row.map((col, colIndex) => (
                            <td key={`${rowIndex}-${colIndex}`}>
                                {isNaN(col) && <img src={images[col]} style='width:25px; height:25px' title={titles[col]}/>}
                                {!isNaN(col) && <p style='width:25px; height:25px'>{col}</p>}
                            </td>
                        ))}
                    </tr>
                ))}
            </tbody>
        </table>
    );
}

export default RenderGame;
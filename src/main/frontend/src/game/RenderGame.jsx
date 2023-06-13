import GImage from '../assets/game-images/gnome.jpg'
import DImage from '../assets/game-images/dwarf.jpg';
import WImage from '../assets/game-images/wizard.jpg';

import EImage from '../assets/game-images/empty.jpg';
import XImage from '../assets/game-images/boss.jpg';

import './RenderGame.css';

function RenderGame(props) {

    const images = { 'G': GImage, 'D': DImage, 'W': WImage, 'E': EImage, 'X': XImage };
    const titles = { 'G': 'Gnome', 'D': 'Dwarf', 'W': 'Wizard', 'E': 'Empty Corner', 'X': 'Boss' };

    return (
        <table class='table'>
            <tbody>
                {props.game.gameboard.map((row, rowIndex) => (
                    <tr key={rowIndex}>
                        {row.map((col, colIndex) => (
                            <td key={`${rowIndex}-${colIndex}`}>
                                {isNaN(col) && <img src={images[col]} title={`${titles[col]}:  ${rowIndex}-${colIndex}`} />}
                                {!isNaN(col) && <p title={`${rowIndex}-${colIndex}`}>{col}</p>}
                            </td>
                        ))}
                    </tr>
                ))}
            </tbody>
        </table>
    );
}

export default RenderGame;
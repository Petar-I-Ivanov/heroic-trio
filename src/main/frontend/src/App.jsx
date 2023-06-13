import { Router, Routes, Route } from '@solidjs/router';
import Game from './game/Game';
import Result from './Result';
import Welcome from './Welcome';

function App() {

  return (
    <Router>
        <Routes>
            <Route path='/' component={Welcome} />
            <Route path='/game/:gameId' component={Game} />
            <Route path='/result/:gameId' component={Result} />
        </Routes>
    </Router>
);
}

export default App;

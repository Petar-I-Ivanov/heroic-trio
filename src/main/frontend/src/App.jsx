import { Router, Routes, Route } from '@solidjs/router';
import Game from './Game';
import Welcome from './Welcome';

function App() {

  return (
    <Router>
        <Routes>
            <Route path='/' component={Welcome} />
            <Route path='/game/:gameId' component={Game} />
        </Routes>
    </Router>
);
}

export default App;

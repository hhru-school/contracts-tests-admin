import logo from './logo.svg';
import './App.css';
import { Component } from 'react';

class App extends Component {
    state = {
        health: String
    }

    async componentDidMount() {
        const response = await fetch('/api/health');
        const body = await response.json();
        console.log(body.value)
        this.setState({health: body.value});
    }

    render() {
        return (
            <div className="App">
                <header className="App-header">
                    <img src={logo} className="App-logo" alt="logo"/>
                    <div className="App-intro">
                        <h2>Response backend(expected hello)</h2>
                        <div>{this.state.health}</div>
                    </div>
                </header>
            </div>
        );
    }
}

export default App;

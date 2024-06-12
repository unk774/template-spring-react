import {useState} from "react";
import Login from "./components/Login";
import Home from "./components/Home";
import {logout} from "./modules/api";


function App() {
    const [principal, setPrincipal] = useState()

    const onLoginSuccess = (principal) => setPrincipal(principal)
    const onLogout = () => {
        logout(principal.username)
        setPrincipal(undefined)
    }

    return (
        <div className={"app"}>
            {principal
                ? <Home onLogout={onLogout}/>
                : <Login onLoginSuccess={onLoginSuccess}/>}
        </div>
    )
}

export default App;

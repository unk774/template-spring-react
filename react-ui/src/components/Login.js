import "../styles/Login.css";
import {login, setAccessToken, setRefreshToken} from "../modules/api";
import {useEffect, useState} from "react";
import {api} from '../modules/api';

export default function Login(props) {

    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")

    useEffect(() => {
        tryLogin()
        // eslint-disable-next-line
    }, []);

    const handleSubmit = () => {
        login(username, password).then(tokens => {
            setUsername("")
            setPassword("")
            setAccessToken(tokens.accessToken)
            setRefreshToken(tokens.refreshToken)
            tryLogin()
        })
    }

    const tryLogin = () => {
        api.get('/auth/user').then(response => {
            props.onLoginSuccess(response.data)
        }).catch(err => {
            console.log(err)
        })
    }

    return (
        <div className="login">
            <div className="login__username">
                <span>username:</span>
                <input type="text" value={username} onChange={(event => {
                    setUsername(event.target.value)
                })}/>
            </div>
            <div className="login__password">
                <span>password:</span>
                <input type="password" name="password" value={password} onChange={(event => {
                    setPassword(event.target.value)
                })}/>
            </div>
            <button onClick={handleSubmit}>login</button>
        </div>
    );
}
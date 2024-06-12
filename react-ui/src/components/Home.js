import "../styles/Home.css"

export default function Home(props) {
    const {onLogout} = props;

    return (
        <div className={"home"}>
            login success<br/>
            welcome back!<br/>
            <button onClick={onLogout}>logout</button>
        </div>
    )
}
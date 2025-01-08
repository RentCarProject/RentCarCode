import React from "react";
import './Header.css';
import logo from '../Logo/CRUVIX.png';


const Header = ()=>{
    return (
        <header>
            <div className="header-container">
                <img src={logo}  className="logo" />
            </div>
        </header>
    );
};

export default Header
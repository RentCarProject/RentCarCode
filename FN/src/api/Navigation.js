import logo from '../components/assets/Cruvix.png';
import { Container, Nav, Navbar, NavDropdown} from "react-bootstrap";
import { useEffect, useState } from 'react';
import { fetchUser } from '../api/UserAPI';
import { useNavigate } from 'react-router-dom';


export default function Navi() {
    const [user, setUser] = useState({});
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem('accessToken');
        if (!token) {
            navigate('/home'); 
        } else {
           
            fetchUser()
                .then((response) => {
                    setUser(response);  
                    console.log(response)
                })
                .catch((error) => {
                    console.log(error);
                    
                });
        }
    }, [navigate]);

    const handleLogout = async () => {
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        localStorage.removeItem('tokenType');
        localStorage.clear();
        window.location.href = '/home';
    }


    return (
        <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
            <Container>
                <Navbar.Brand href="/home">
                    <img src={logo} width="55" height="55" alt="" />
                    Home
                </Navbar.Brand>

                <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                <Navbar.Collapse id="responsive-navbar-nav">
                    <Nav className="me-auto" alt="Nav Empty Space">

                    </Nav>
                    <Nav>
                        <Nav.Link href="/home">Home</Nav.Link>

                        <NavDropdown title="렌트" id="collasible-nav-dropdown">
                            <NavDropdown.Item href="/car">렌트차량</NavDropdown.Item>
                        
                        </NavDropdown>
                        <NavDropdown title="리뷰" id="collasible-nav-dropdown">
                            <NavDropdown.Item href="/review">리뷰</NavDropdown.Item>
                     
                        </NavDropdown>
                        {user.memberId ? (
                            <NavDropdown title={`${user.memberNm}님 환영합니다`} id="collasible-nav-dropdown">
                                <NavDropdown.Item href="/mypage">MyPage</NavDropdown.Item>
                                <NavDropdown.Item href="/" onClick={handleLogout}>로그아웃</NavDropdown.Item>
                            </NavDropdown>
                        ) : (
                            <NavDropdown title="Login/SignUp" id="collasible-nav-dropdown">
                                <NavDropdown.Item href="/signin">Login</NavDropdown.Item>
                                <NavDropdown.Item href="/signup">SignUp</NavDropdown.Item>
                            </NavDropdown>
                        )}
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}
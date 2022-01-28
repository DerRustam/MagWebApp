import React, {Component} from "react";
import {Container, Navbar, Nav} from "react-bootstrap";

import AccountCard from "../expand/AccountCard";
import SignModal from "../modal/SignModal";
import UserService, {UserStatus} from "../../services/user-service"
import {strings} from "../../locale/strings";
import {getUserName,  removeUserName} from "../../services/auth-service";

export default class NavigationMenu extends Component {
    constructor(props) {
        super(props);

        this.state = {
            isSigned: false,
            accountData: undefined
        }

        this.loginProceed = this.loginProceed.bind(this);
        this.accountRemoved = this.accountRemoved.bind(this);
    }

    componentDidMount() {
        let user = getUserName();
        console.log("User: "+user);
        if (user !== null){
            UserService.loadAccountData(user).then(
                result => {
                    if (result.status === UserStatus.OK){
                        this.setState({isSigned: true, accountData: result.accountData});
                    } else {
                        removeUserName();
                        this.setState({isSigned: false, accountData: undefined});
                    }
                }
            )
        }
    }

    loginProceed(loginData){
        this.setState({
            isSigned: true,
            accountData: loginData
        });
    }

    accountRemoved(){
        this.setState({
            isSigned: false,
            accountData: undefined
        });
    }

    render() {
        return(
          <Navbar bg="dark" variant="dark" expand="lg" sticky="top">
              <Container>
                  <Navbar.Brand href="/">Image Hosting</Navbar.Brand>
                  <Nav className="me-auto mx-3">
                      <Nav.Link href="/">{strings("L_HOME")}</Nav.Link>
                  </Nav>
                  <Nav className="me-2">
                      <Nav.Item>
                          {!this.state.isSigned && <SignModal loginProceed={this.loginProceed}/>}
                          {this.state.isSigned && <AccountCard accountData={this.state.accountData} logoutProceed={this.accountRemoved}/>}
                      </Nav.Item>
                  </Nav>
              </Container>
          </Navbar>
        );
    }
}
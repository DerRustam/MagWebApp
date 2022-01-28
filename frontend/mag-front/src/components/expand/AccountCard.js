import React, {Component} from "react";
import {Container, Button, Card, Overlay} from "react-bootstrap"
import {CgProfile} from "react-icons/cg"
import AuthService from "../../services/auth-service";

import {strings} from "../../locale/strings";


export default class AccountCard extends Component{
    viewNickname;
    isSuspended;
    lastName;
    contactEmail;
    constructor(props) {
        super(props);
        this.state = {
            showCard: false
        }
        this.handleLogout = this.handleLogout.bind(this);
        this.handleClickCard =this.handleClickCard.bind(this);
        this.target = React.createRef();
    }

    handleClickCard() {
        this.setState({showCard: !this.state.showCard});
    }

    handleLogout(){
        AuthService.logout().then(
            _ => {
                this.props.logoutProceed();
            }
        );
    }

    render() {
        return(
            <Container >
                <Button onClick={this.handleClickCard} style={styles.buttonShowCard} ref = {this.target}>
                    <CgProfile size = {20}/>
                </Button>
                <Overlay target = {this.target.current} show={this.state.showCard} placement="bottom">
                    {({placement, arrowProps, show: _show, popper, ...props}) => (
                        <div {...props} style={{...props.style}}>
                            <Card bg="dark" text="light" border="primary" className="mb-2" style={styles.cardAccount}>
                                <Card.Header>{strings("L_P_PROFILE")}</Card.Header>
                                {
                                    this.props.accountData.type === "USER" &&
                                    <Card.Body>
                                        <Card.Title>{strings("L_P_USER")}</Card.Title>
                                        <Card.Text>{this.props.accountData.viewNickname}</Card.Text>
                                        <Card.Text>{this.props.accountData.firstName + " " + this.props.accountData.lastName}</Card.Text>
                                            {this.props.accountData.isSuspended && <Card.Text> {strings("L_P_SUSPENDED")}</Card.Text>}
                                            {!this.props.accountData.isSuspended && <Card.Text> {strings("L_P_ACTIVE")}</Card.Text>}
                                        <Card.Link href ="/">
                                            <Button variant="outline-danger" type="submit" onClick={this.handleLogout}>
                                                {strings("L_P_LOGOUT")}
                                            </Button>
                                        </Card.Link>
                                    </Card.Body>
                                }
                                {
                                    this.props.accountData.type === "STAFF" &&
                                    <Card.Body>
                                        <Card.Title>{strings("L_P_STAFF")}</Card.Title>
                                        <Card.Text>{this.props.accountData.fullName}</Card.Text>
                                        <Card.Text>{this.props.accountData.contactEmail}</Card.Text>
                                        <Card.Text>
                                            {this.props.accountData.isSuspended &&strings("L_P_SUSPENDED")}
                                            {!this.props.accountData.isSuspended &&strings("L_P_ACTIVE")}
                                        </Card.Text>
                                        <Card.Link href ="/data_working">
                                            <Button variant="outline-info" type="submit">
                                                {strings("L_P_DB")}
                                            </Button>
                                        </Card.Link>
                                        <Card.Link href ="/">
                                            <Button variant="outline-danger" type="submit" onClick={this.handleLogout}>
                                                {strings("L_P_LOGOUT")}
                                            </Button>
                                        </Card.Link>
                                    </Card.Body>
                                }
                            </Card>
                        </div>
                    )}
                </Overlay>
            </Container>
        );
    }
}

const styles = {
    buttonShowCard :{width: '5rem'},
    cardAccount: {marginTop: '10px', width: '18rem'}
};
import React, {Component} from "react";
import {Alert, Tab, Col, Row, Nav} from "react-bootstrap";

import {validateField, passwordsIsEquals, uidIsEmail} from "../../utils/sign-validators";

import {SignUpForm} from "../forms/sign/SignUpForm";
import {SignInForm} from "../forms/sign/SignInForm";
import {strings} from "../../locale/strings";
import AuthService, {SignStatus} from "../../services/auth-service";

export default class SignContainer extends Component{
    constructor(props) {
        super(props);

        this.state = {
            errors: {
                uid_empty: true,
                em_empty: true,
                em_invalid: true,
                un_empty: true,
                un_invalid: true,
                pw_empty: true,
                pw_invalid: true,
                pwc_empty: true,
                pwc_invalid: true,
                fn_empty: true,
                fn_invalid: true
            },
            touches:{
                userId: false,
                email: false,
                username: false,
                password: false,
                passwordConf: false,
                firstName: false
            },
            userData:{
                userId:"",
                email: "",
                username: "",
                password: "",
                passwordConf: "",
                firstName: "",
                gender: "",
                remembrance: "off"
            },
            activeNavKey: "s-in",
            signError: undefined
        }
        this.handleChange = this.handleChange.bind(this);
        this.changeRemembrance = this.changeRemembrance.bind(this);
        this.setGender = this.setGender.bind(this);
        this.submitSignIn = this.submitSignIn.bind(this);
        this.submitSignUp = this.submitSignUp.bind(this);
        this.onChangeActiveTab = this.onChangeActiveTab.bind(this);
        this.loginSucceed = this.loginSucceed.bind(this);
    }

    handleChange(event) {
        const field = event.target.name;
        const userData = this.state.userData;
        const touches = this.state.touches;
        const errors = this.state.errors;
        touches[field] = true;
        userData[field] = event.target.value;
        const errMap = validateField(field, event.target.value);
        Object.entries(errMap).map(([key, value]) => {
            errors[key] = value;
        })
        this.setState({errors,touches,userData});
    }

    changeRemembrance(event){
        const userData = this.state.userData;
        userData.remembrance = event.target.value;
        this.setState({userData});
    }

    setGender(event){
        const userData = this.state.userData;
        userData.gender = event.target.value;
        this.setState({userData});
    }

    onChangeActiveTab(tabKey){
        this.setState({activeNavKey: tabKey, signError : undefined});
    }

    loginSucceed(accountData){
        this.props.loginSucceed(accountData);
    }

    submitSignIn(event){
        event.preventDefault();
        this.setState({signError : undefined});
        if (this.validateOnSignIn()) {
            AuthService.login(this.state.userData).then(
            //AuthService.ping().then(
                result =>{
                    if (result.status === SignStatus.OK){
                        console.log(result.accountData);
                        this.loginSucceed(result.accountData)
                    } else if (result.status === SignStatus.ERR_NF){
                        if (uidIsEmail(this.state.userData.userId)){
                            this.setState({signError : strings("MSG_E_SI_EM")})
                        } else {
                            this.setState({signError : strings("MSG_E_SI_UN")})
                        }
                    }
                }
            );
        }
    }

    submitSignUp(event){
        event.preventDefault();
        this.setState({signError : undefined});
        if (this.validateOnSignUp()){
            AuthService.register(this.state.userData).then(
                result =>{

                    if (result.status === SignStatus.OK){
                        const userData = this.state.userData;
                        userData.userId = userData.username;
                        this.setState({userData});
                        this.onChangeActiveTab("s-in");
                    } else if (result.status === SignStatus.ERR_DUP){
                        if (uidIsEmail(this.state.userData.userId)){
                            this.setState({signError : strings("MSG_E_SI_EM")})
                        } else {
                            this.setState({signError : strings("MSG_E_SI_UN")})
                        }
                    }
                }
            );
        }
    }

    validateOnSignIn(){
        let isValid = true;
        if (this.isFieldInvalid("userId", this.state.userData.userId)){
            isValid = false;
        }
        if (this.isFieldInvalid("password", this.state.userData.password)){
            isValid = false;
        }
        const touches = this.state.touches;
        touches.userId = true;
        touches.password = true;
        this.setState({touches})
        return isValid;
    }

    validateOnSignUp(){
        const errors = this.state.errors;
        const touches = this.state.touches;
        let isValid = true;
        if (this.isFieldInvalid("email", this.state.userData.email)){
            isValid = false;
        }
        if (this.isFieldInvalid("username", this.state.userData.username)){
            isValid = false;
        }
        if (this.isFieldInvalid("password", this.state.userData.password)){
            isValid = false;
        }
        if (this.isFieldInvalid("firstName", this.state.userData.firstName)){
            isValid = false;
        }
        if (this.isFieldInvalid("passwordConf", this.state.userData.passwordConf)){
            isValid = false;
        } else if (!passwordsIsEquals(this.state.userData.password, this.state.userData.passwordConf)){
            isValid = false;
            errors.pwc_invalid = true;
        } else {
            errors.pwc_invalid = false;
        }
        touches.email = true;
        touches.username = true;
        touches.password = true;
        touches.passwordConf = true;
        touches.firstName = true;
        this.setState({errors, touches})
        return isValid;
    }

    isFieldInvalid(fieldName, fieldValue){
        let errMap = validateField(fieldName, fieldValue);
        for (let [_, val] of Object.entries(errMap)){
            if (val === true){
                return true;
            }
        }
        return false;
    }

    render() {
        return (
            <Tab.Container id="sign-tabs" defaultActiveKey="s-in">
                <Col>
                    <Row>
                        <Nav variant="pills" activeKey={this.state.activeNavKey} onSelect={this.onChangeActiveTab} className="flex-row nav-fill w-100 border rounded rounded-4 mb-3 bg-light">
                            <Nav.Item>
                                <Nav.Link eventKey="s-in">{strings("L_LOGIN")}</Nav.Link>
                            </Nav.Item>
                            <Nav.Item>
                                <Nav.Link eventKey="s-up">{strings("L_SIGN_UP")}</Nav.Link>
                            </Nav.Item>
                        </Nav>
                    </Row>
                    <Tab.Container activeKey={this.state.activeNavKey}>
                        <Tab.Content>
                            <Tab.Pane eventKey="s-in">
                                <SignInForm onSubmit={this.submitSignIn} onChange={this.handleChange}
                                            onChangeRemembrance={this.changeRemembrance}
                                            userData={this.state.userData} touches={this.state.touches}
                                            errors={this.state.errors}/>
                            </Tab.Pane>
                            <Tab.Pane eventKey="s-up">
                                <SignUpForm onSubmit={this.submitSignUp} onSetGender={this.setGender} onChange={this.handleChange}
                                            userData={this.state.userData} touches={this.state.touches}
                                            errors={this.state.errors}/>
                            </Tab.Pane>
                        </Tab.Content>
                    </Tab.Container>

                    {this.state.signError && <Alert variant="danger" className="mt-3 mb-0">{this.state.signError}</Alert>}
                </Col>
            </Tab.Container>
        );
    }
}
import React, {Component} from "react"
import {Button, Modal, Container} from "react-bootstrap";

import {GrLogin} from "react-icons/gr"

import SignContainer from "../sign/SignContainer";

import {strings} from "../../locale/strings";


export default class SignModal extends Component{
    constructor(props) {
        super(props);
        this.state = {
            show: false
        }
        this.showModal = this.showModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
        this.loginSucceed = this.loginSucceed.bind(this);
    }

    showModal() {
        this.setState({show: true});
    }

    closeModal(){
        this.setState({show: false});
    }

    loginSucceed(loadedAccount){
        this.closeModal();
        this.props.loginProceed(loadedAccount);
    }

    render() {
        return(
            <Container>
                <Button onClick={this.showModal}>{strings("B_SIGN")}<GrLogin/></Button>
                <Modal size="lg" show={this.state.show} onHide={this.closeModal}>
                    <Modal.Header closeButton>
                        <Modal.Title>{strings("L_SIGN")}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <SignContainer loginSucceed={this.loginSucceed}/>
                    </Modal.Body>
                </Modal>
            </Container>
        );
    }

}
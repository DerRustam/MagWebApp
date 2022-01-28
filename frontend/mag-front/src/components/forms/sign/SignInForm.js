import React from 'react'
import {Form, Button} from "react-bootstrap"
import {strings} from "../../../locale/strings"

export const SignInForm = ({onSubmit, onChange,onChangeRemembrance, userData, touches, errors}) => {
    return(
        <Form noValidate onSubmit={onSubmit}>
            <Form.Group className="mb-3">
                <Form.Label>{strings('L_S_EM_UN')}</Form.Label>
                <Form.Control required name = "userId" type = "text" value = {userData.userId}
                              onChange = {onChange} maxLength = "254"
                              placeholder={strings('L_HOLDER_EM_UN')}
                              isInvalid={!!touches.userId && (errors.uid_empty || errors.em_invalid || errors.un_invalid)}
                />
                <Form.Control.Feedback type="invalid">
                    {
                        (errors.uid_empty && strings("L_E_EM_UN_ETY"))
                        || (errors.un_invalid && strings("L_E_UN_FMT"))
                        || (errors.em_invalid && strings("L_E_EM_FMT"))
                    }
                </Form.Control.Feedback>
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Label>{strings("L_S_PW")}</Form.Label>
                <Form.Control required name="password" type="password" value={userData.password}
                              onChange={onChange} maxLength="16"
                              placeholder={strings("L_HOLDER_PW")}
                              isInvalid={!!touches.password && (errors.pw_empty || errors.pw_invalid)}
                />
                <Form.Control.Feedback type="invalid">
                    {
                        (errors.pw_empty && strings("L_E_PW_ETY"))
                        || (errors.pw_invalid && strings("L_E_PW_FMT"))
                    }
                </Form.Control.Feedback>
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Check type="checkbox" id="isRemember" name="isRemember" className="form-check"
                            label="Remember me" onChange={onChangeRemembrance}/>
            </Form.Group>
            <Button type = "submit" className="w-25">{strings("B_SIGN_IN")}</Button>
        </Form>
    );
}



import React from "react";
import {Form, Button, Col, Row} from 'react-bootstrap';
import {strings} from "../../../locale/strings";

export const SignUpForm = ({onSubmit, onChange, onSetGender, userData, touches, errors}) => {
    return(
        <Form noValidate onSubmit={onSubmit}>
            <Form.Group className="mb-3">
                <Form.Label>{strings("L_S_EM")}</Form.Label>
                <Form.Control required name="email" type="text" value={userData.email}
                              onChange={onChange} maxLength="254"
                              placeholder={strings("L_HOLDER_EM")}
                              isInvalid={!!touches.email && (errors.em_empty || errors.em_invalid)}
                />
                <Form.Control.Feedback type="invalid">
                    {
                        (errors.em_empty && strings("L_E_EM_ETY"))
                        || (errors.em_invalid && strings("L_E_EM_FMT"))
                    }
                </Form.Control.Feedback>
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Label>{strings("L_S_UN")}</Form.Label>
                <Form.Control required name="username" type="text" value={userData.username}
                              onChange={onChange} maxLength="32"
                              placeholder={strings("L_HOLDER_UN")}
                              isInvalid={!!touches.username && (errors.un_empty || errors.un_invalid)}
                />
                <Form.Control.Feedback type="invalid">
                    {
                        (errors.un_empty && strings("L_E_UN_ETY"))
                        || (errors.un_invalid && strings("L_E_UN_FMT"))
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
                <Form.Label>{strings("L_S_PWC")}</Form.Label>
                <Form.Control required name = "passwordConf" type="password" value={userData.passwordConf}
                              onChange={onChange} maxLength="16"
                              placeholder={strings("L_HOLDER_PWC")}
                              isInvalid={!!touches.passwordConf && (errors.pwc_empty || errors.pwc_invalid)}
                />
                <Form.Control.Feedback type="invalid">
                    {
                        (errors.pwc_empty && strings("L_E_PWC_ETY"))
                        || (errors.pwc_invalid && strings("L_E_PWC_S"))
                    }
                </Form.Control.Feedback>
            </Form.Group>
            <Row className="mb-3">
                <Form.Group as = {Col} md = "6">
                    <Form.Label>{strings("L_S_FN")}</Form.Label>
                    <Form.Control required name="firstName" type= "text" value={userData.firstName}
                                  onChange={onChange} maxLength="30"
                                  placeholder={strings("L_HOLDER_FN")}
                                  isInvalid={!!touches.firstName && errors.fn_empty}
                    />
                    <Form.Control.Feedback type="invalid">
                        {
                            (errors.fn_empty && strings("L_E_FN_ETY"))
                            || (errors.fn_invalid && strings("L_E_FN_FMT"))
                        }
                    </Form.Control.Feedback>
                </Form.Group>
                <Form.Group as = {Col} md="2">
                    <Form.Label htmlFor="signGenderSelect">{strings("L_S_GENDER")}</Form.Label>
                    <Form.Select id="signGenderSelect" name="gender" value={userData.gender} onChange={onSetGender}>
                        <option value=" ">None</option>
                        <option value="M">Male</option>
                        <option value="F">Female</option>
                    </Form.Select>
                </Form.Group>
            </Row>
            <Button type="submit" variant="primary" className="w-25">{strings("B_SIGN_UP")}</Button>
        </Form>
    );
}

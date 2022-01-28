import React from "react";
import {Button, Row, Col} from "react-bootstrap";

import {IoMdAdd} from "react-icons/io";

import {strings} from "../../locale/strings";

export default function ToolPanel({handleOnAdd}){
    return (
        <Row className="bg-light px-1 py-1">
            <Col md={10}></Col>
            <Col className="container-fluid">
                <Button variant="primary" onClick={handleOnAdd}>
                    <IoMdAdd size={23}/>
                    <label className="mx-2 mb-1">
                        {strings("L_DB_ADD")}
                    </label>
                </Button>
            </Col>
        </Row>
    );
}
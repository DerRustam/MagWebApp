import React, {Component} from "react"

import {Container, Col, Row} from "react-bootstrap"
import {TableOperation, DefaultTableName} from "../meta/data-table-meta";

import TableSelector from "../components/data/TableSelector";
import ToolPanel from "../components/data/ToolPanel";
import TableView from "../components/data/TableView";

export default class DataPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            focusField: "",
            focusValue: "",
            selectedTable: DefaultTableName,
            currentOperation: TableOperation.None
        }
        this.selectTable = this.selectTable.bind(this);
        this.focusText = this.focusText.bind(this);
        this.selectOperation = this.selectOperation.bind(this);
        this.handleOnAdd = this.handleOnAdd.bind(this);
    }

    selectTable(table) {
        this.setState({selectedTable: table, currentOperation: TableOperation.None, focusField: "", focusValue: ""});
    }

    focusText(dataField, text){
        this.setState({focusedField: dataField, focusValue: text});
    }

    handleOnAdd(){

    }

    selectOperation(operation){
        this.setState({currentOperation: operation})
    }



    render() {
        return(
            <Container fluid={true} className = "bg-warning">
                <Row>
                    <Col md ={2} className = "bg-dark border border-primary">
                        <TableSelector selectedTabName = {this.state.selectedTable} selectTable={this.selectTable}/>
                    </Col>
                    <Col className = "bg-light border border-primary border-2 ">
                        <ToolPanel handleOnAdd={this.handleOnAdd}/>
                        <TableView selectedTable={this.state.selectedTable}/>
                    </Col>
                </Row>
            </Container>

        );
    }
}
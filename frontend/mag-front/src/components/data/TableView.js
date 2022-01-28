import React, {Component} from "react";
import {Button, ButtonGroup, Col, Container, Row} from "react-bootstrap";
import {FiRefreshCcw} from "react-icons/fi";
import {loadPage} from "../../services/data-service"
import {ClassificationTables} from "../../meta/data-table-meta";
import {strings} from "../../locale/strings";
import getTableWithNameAndData from "./table/TableManager";

const sizePerPageList = [10, 15, 30];

export default class TableView extends Component{
    constructor(props) {
        super(props);
        this.state = {
            viewTable: null,
            subTableNames: [],
            selectedSubTable: null,
            columns: [],
            data: [],
            pageNumber: 1,
            pageSize: sizePerPageList[0],
            pagesCount: 1,
            buttonNextDisabled: false,
            buttonPrevDisabled: true,
            showDescription: false,
            descriptionField: "",
            descriptionText: "",
            showOperationModal: false,
            selectedRow: undefined,
        }
        this.selectSize = this.selectSize.bind(this);
        this.selectFirstPage = this.selectFirstPage.bind(this);
        this.selectPreviousPage = this.selectPreviousPage.bind(this);
        this.selectPage = this.selectPage.bind(this);
        this.selectNextPage = this.selectNextPage.bind(this);
        this.selectLastPage = this.selectLastPage.bind(this);
        this.refreshTablePage = this.refreshTablePage.bind(this);
    }
    componentDidUpdate(prevProps, prevState, snapshot) {
        if (prevProps.selectedTable !== this.props.selectedTable){
            this.selectTable(this.props.selectedTable)
        }
    }

    componentDidMount() {
        this.selectTable(this.props.selectedTable);
    }

    selectTable(table) {
        let tableId = table;
        if (ClassificationTables.includes(table)){
            if (table === "Accounts" || table === "History"){
                const listSubTables = ["Staff", "Users"];
                const selectedSubTable = "Users";
                tableId = selectedSubTable+table;
                this.setState({subTableNames: listSubTables, selectedSubTable: selectedSubTable});
            }
        } else {
            this.setState({selectedSubTable: null});
        }
        const sorting = {};
        loadPage(tableId, this.state.pageNumber-1, this.state.pageSize, sorting).then(
            response => {
                console.log(response.data);
                const table = getTableWithNameAndData(tableId, response.data.content);
                this.setState({viewTable: table});
            }, error => {
                if (error.response){
                    console.log(error.response);
                } else {
                    console.log(error);
                }
            }
        )
    }

    selectSubTable(subTable){
        this.setState({selectedSubTable: subTable});
        console.log(subTable+this.props.selectedTable);
        loadPage(subTable+this.props.selectedTable, this.state.pageNumber-1, this.state.pageSize, {}).then(
            response => {
                console.log(response.data);
                const table = getTableWithNameAndData(subTable+this.props.selectedTable, response.data.content);
                this.setState({viewTable: table});
            }, error => {
                if (error.response){
                    console.log(error.response);
                } else {
                    console.log(error);
                }
            }
        )
    }

    refreshTablePage(){

    }


    selectSize(event){
        this.setState({pageSize: event.target.value});
    }

    selectFirstPage(){

    }

    selectPreviousPage(){

    }

    selectNextPage(){

    }

    selectLastPage(){

    }

    selectPage(event){

    }

    render() {
        return(
            <Container className="px-0 border border-secondary border-2">
                {this.state.selectedSubTable &&
                    <ButtonGroup>
                        {
                            this.state.subTableNames.map((name, i) =>{
                                return <Button className="mx-2" key={i} onClick={this.selectSubTable.bind(this, name)}>{name}</Button>
                            })
                        }
                    </ButtonGroup>
                }
                {this.state.viewTable}
                <Row>
                    <Col className = "mb-1" md={1}>
                        <select className="btn btn-secondary btn-block mt-1" onChange={this.selectSize}>
                            {
                                sizePerPageList.map((item) => {
                                    return <option key={item}>{item}</option>
                                })
                            }
                        </select>
                    </Col>
                    <Col md={{span: 5, offset: 2}}>
                        <Row>
                            <Col className="mt-1">
                                <Button onClick={this.selectFirstPage}
                                        disabled={this.state.buttonPrevDisabled}>{" <<< "}</Button>
                                <Button className="ms-2" onClick={this.selectPreviousPage}
                                        disabled={this.state.buttonPrevDisabled}>{"   <    "}</Button>
                            </Col>
                            <Col className="mt-1">
                                <select className="btn btn-secondary"
                                        onChange={this.selectPage} value={this.state.pageNumber}>
                                    {
                                        Array.from(Array(this.state.pagesCount).map((item) => {
                                            return <option key={item}>{item+1}</option>
                                        }))
                                    }
                                </select>
                                <label className="ms-2">{" / "+this.state.pagesCount}</label>
                            </Col>
                            <Col className="mt-1">
                                <Button className="me-2" onClick={this.selectNextPage}
                                        disabled={this.state.buttonNextDisabled}>{"  >  "}</Button>
                                <Button onClick={this.selectLastPage}
                                        disabled={this.state.buttonNextDisabled}>{" >>> "}</Button>
                            </Col>
                            <Col md={3}>
                                <Button onClick={this.refreshTablePage} className="btn-info btn-block mt-1">
                                    <FiRefreshCcw size={18}/>
                                </Button>
                            </Col>
                        </Row>
                    </Col>
                </Row>
            </Container>
        );
    }
}
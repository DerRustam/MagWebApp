import React, {Component} from "react"
import {Button, ButtonGroup} from "react-bootstrap";

import {TableNames} from "../../meta/data-table-meta";

export default class TableSelector extends Component {
    constructor(props) {
        super(props);
    }

    selectTable(tableName){
        this.props.selectTable(tableName);
    }

    render() {
        return(
            <ButtonGroup vertical className="w-100">
                {
                    TableNames.map((table, index) => {
                        return <Button className="my-1" key={index} variant = {this.props.selectedTabName === table ? "warning" : "secondary"}
                                       onClick={this.selectTable.bind(this, table)}>
                            {table}
                        </Button>
                    })
                }
            </ButtonGroup>
        );
    }
}
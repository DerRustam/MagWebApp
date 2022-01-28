import React from "react"
import {Table} from "react-bootstrap";
import {strings} from "../../../locale/strings";

export default function getSearchTagTable(dataList) {
    return(
        <Table striped bordered hover>
            <thead>
                <tr>
                    <th>Name Tag</th>
                    <th>{strings("L_DB_OPR")}</th>
                </tr>
            </thead>
            <tbody>
            {
                dataList.map((record, _) => {
                    return <tr id={record.id}>
                        <td>{record.nameTag}</td>
                        <td></td>
                    </tr>;
                })
            }
            </tbody>
        </Table>
    );
}